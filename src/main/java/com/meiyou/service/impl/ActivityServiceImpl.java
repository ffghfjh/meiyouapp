package com.meiyou.service.impl;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.Activity;
import com.meiyou.pojo.ActivityExample;
import com.meiyou.pojo.User;
import com.meiyou.service.ActivityService;
import com.meiyou.service.RootMessageService;
import com.meiyou.service.UserService;
import com.meiyou.utils.Constants;
import com.meiyou.utils.FileUploadUtil;
import com.meiyou.utils.Msg;
import com.meiyou.utils.RedisUtil;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import redis.clients.jedis.GeoRadiusResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author     ：huangzhaoyang
 * @date       ：Created in 2019/8/21 14:19
 * @description：附近动态服务层接口实现
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    UserService userService;

    @Autowired
    RootMessageService rootMessageService;

    /**
     * hzy
     * 用户发布动态
     * @param uid
     * @param latitude
     * @param longitude
     * @param content
     * @param files
     * @param request
     * @return
     */
    @Override
    public int postActivity(int uid, double latitude, double longitude, String content,  MultipartFile[] files
            ,HttpServletRequest request){
        Activity activity = new Activity();
        activity.setCreateTime(new Date());
        activity.setUpdateTime(new Date());
        activity.setPublishId(uid);
        //使用Hutool进行json操作
        JSONArray array = JSONUtil.createArray();
        for (MultipartFile file : files) {
            Msg msg = FileUploadUtil.uploadUtil(file, "activity", request);
            if (msg.getCode() == 100) {
                array.add(msg.getExtend().get("path"));
            }
        }
        if (array.size() == 0) {
            return 0;
        }
        activity.setImgsUrl(array.toString());//以json数组的形式存图片
        activity.setContent(content);
        activity.setReadNum(0);
        activity.setLikeNum(0);
        activity.setCommontNum(0);
        activity.setBoolClose(false);
        int i = activityMapper.insert(activity);
        //获得插入的id
        int aid = activity.getId();
        if (i == 1) {
            //添加地理位置和aid到Redis缓存中
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(latitude);
            coordinate.setLongitude(longitude);
            coordinate.setKey(Integer.toString(aid));
            Long reo = RedisUtil.addReo(coordinate, Constants.GEO_ACTIVITY);
            if (reo == null) {
                return 0;
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int removeActivity(int aid) {
        return 0;
    }

    @Override
    public int removeAllActivityByUid() {
        return 0;
    }

    @Override
    public int updateActivity(Activity activity) {
        return 0;
    }

    @Override
    public Activity getActivityByAid(int aid) {
        return null;
    }

    /**
     * hzy
     * 根据用户id获得所有动态
     * @param uid
     * @return ArrayList<HashMap<String, Object>>
     */
    @Override
    public ArrayList<HashMap<String, Object>>  listUserActivityByUid(int uid) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        //根据uid获取用户动态
        List<Activity> activities = listActivityByUid(uid);
        HashMap<String, Object> hashMapNo = new HashMap<>();
        User user = userService.getUserById(uid);
        boolean flag = (activities ==null && activities.size()==0);
        //用户或动态不存在的话，返回默认信息，防止空指针异常
        if (user == null || flag) {
            hashMapNo.put("header", "no picture");
            hashMapNo.put("nickname", "无昵称");
            hashMapNo.put("sex", 0);
            hashMapNo.put("birthday", "0");
            hashMapNo.put("content", "无动态内容");
            hashMapNo.put("imgsUrl", "no picture");
            hashMapNo.put("distance", "0.0km");
            hashMapNo.put("time", "时间不存在");
            hashMapNo.put("readNum", 0);
            hashMapNo.put("likeNum", 0);
            hashMapNo.put("commontNum", 0);
            hashMapNo.put("uid", 0);
            hashMapNo.put("aid", 0);
            list.add(hashMapNo);
            return list;
        }
        for (Activity activity : activities) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("header", user.getHeader());
            hashMap.put("nickname", user.getNickname());
            hashMap.put("sex", user.getSex());
            hashMap.put("birthday", user.getBirthday());
            hashMap.put("content", activity.getContent());
            hashMap.put("imgsUrl", activity.getImgsUrl());
            hashMap.put("distance", "0.00");
            //发布时间
            Date createTime = activity.getCreateTime();
            //当前时间
            Date nowTime = DateUtil.date();
            //时间差
            String formatBetween = DateUtil.formatBetween(createTime, nowTime, BetweenFormater.Level.SECOND) + "前";
            hashMap.put("time", formatBetween);
            hashMap.put("readNum", activity.getReadNum());
            hashMap.put("likeNum", activity.getLikeNum());
            hashMap.put("commontNum", activity.getCommontNum());
            hashMap.put("uid", uid);
            hashMap.put("aid", activity.getId());
            list.add(hashMap);
        }
        return list;
    }

    /**
     * hzy
     * 通过用户id查询ta的所有动态
     * @param uid
     * @return
     * 待办事项：需要加redis缓存
     */
    @Override
    public List<Activity> listActivityByUid(int uid) {
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria criteria = example.createCriteria();
        criteria.andPublishIdEqualTo(uid);
        return activityMapper.selectByExample(example);
    }

    @Override
    public List<Activity> listActivity() {
        List<Activity> activities = activityMapper.listActivityByExample(null);
        if (activities == null || activities.size()==0) {
            List<Activity> activities1 = new ArrayList<>();
            Activity activity = new Activity();
            activity.setContent("找不到任何动态信息");
            return activities1;
        }
        return activities;
    }

    /**
     * hzy
     * 通过用户id和经纬度获取附近所有动态
     * @param uid
     * @param latitude
     * @param longitude
     * @return
     */
    @Override
    public Msg listNeighborActivity(int uid, double latitude, double longitude) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        //范围半径
        String range = rootMessageService.getMessageByName("range");
        double radius = Double.parseDouble(range);
        //从Redis获取附近的所有用户的动态
        Coordinate coordinate = new Coordinate();
        coordinate.setKey(Integer.toString(uid));
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        List<GeoRadiusResponse> responseList = RedisUtil.geoQueryActivity(coordinate, radius);
        if (responseList == null && responseList.size() ==0) {
            return Msg.fail();
        }
        for (GeoRadiusResponse response : responseList) {
            String memberByString = response.getMemberByString();
            //距离我多远
            Double dis = response.getDistance();
            String distance = "0.00";
            if (dis != null) {
                distance = Double.toString(dis);
            }
            int nbhAid = 0;
            if (memberByString != null) {
                nbhAid = Integer.parseInt(memberByString);
            }
            Activity activity = activityMapper.selectByPrimaryKey(nbhAid);
            //如果activity不存在
            if (activity == null) {
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("header", "no picture");
                hashMap.put("nickname", "无昵称");
                hashMap.put("sex", 0);
                hashMap.put("birthday", "0");
                hashMap.put("content", "无附近动态内容");
                hashMap.put("imgsUrl", "no picture");
                hashMap.put("distance", "0.00");
                hashMap.put("time", "时间不存在");
                hashMap.put("readNum", 0);
                hashMap.put("likeNum", 0);
                hashMap.put("commontNum", 0);
                hashMap.put("uid", 0);
                hashMap.put("aid", nbhAid);
                list.add(hashMap);
            }
            //如果activity存在
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            //获得该动态的用户信息
            User user = userService.getUserById(activity.getPublishId());
            hashMap.put("header", user.getHeader());
            hashMap.put("nickname", user.getNickname());
            hashMap.put("sex", user.getSex());
            hashMap.put("birthday", user.getBirthday());
            hashMap.put("content", activity.getContent());
            hashMap.put("imgsUrl", activity.getImgsUrl());
            hashMap.put("distance", distance);
            Date createTime = activity.getCreateTime();
            Date nowTime = DateUtil.date();
            //时间差
            String formatBetween = DateUtil.formatBetween(createTime, nowTime, BetweenFormater.Level.SECOND) + "前";
            hashMap.put("time", formatBetween);
            hashMap.put("readNum", activity.getReadNum());
            hashMap.put("likeNum", activity.getLikeNum());
            hashMap.put("commontNum", activity.getCommontNum());
            hashMap.put("uid", activity.getPublishId());
            hashMap.put("aid", activity.getId());
            list.add(hashMap);
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("activityList", list);
        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMsg("查找动态成功");
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("a", "hh");
        map1.put("b", "sdf");
        msg.setExtend(map);
        return msg;
    }

}
