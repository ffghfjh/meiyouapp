package com.meiyou.service.impl;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.mapper.UserMapper;
import com.meiyou.model.Coordinate;
import com.meiyou.pojo.Activity;
import com.meiyou.pojo.ActivityExample;
import com.meiyou.pojo.User;
import com.meiyou.service.ActivityLikeService;
import com.meiyou.service.ActivityService;
import com.meiyou.service.RootMessageService;
import com.meiyou.service.UserService;
import com.meiyou.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.GeoRadiusResponse;

import javax.servlet.http.HttpServletRequest;
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
    UserMapper userMapper;

    @Autowired
    RootMessageService rootMessageService;

    @Autowired
    ActivityLikeService activityLikeService;

    @Override
    public Msg remove(String uid, String token, int aid) {
        Msg msg = new Msg();
        int persionId = Integer.parseInt(uid);
        if (RedisUtil.authToken(uid, token)) {
            //判断动态是否是动态主人
            Activity activity = activityMapper.selectByPrimaryKey(aid);
            User user = userMapper.selectByPrimaryKey(persionId);
            boolean boolHost = ((activity != null) && (user != null) && (activity.getPublishId() == user.getId()));
            if (boolHost) {
                int i = activityMapper.deleteByPrimaryKey(aid);
                if (i == 1) {
                    msg.setCode(100);
                    msg.setMsg("删除动态成功！");
                    return msg;
                }
                msg.setCode(200);
                msg.setMsg("删除动态失败");
                return msg;
            }
            msg.setCode(200);
            msg.setMsg("动态不存在或用户不存在或删除的是非动态主人");
            return msg;
        }
        msg.setCode(200);
        msg.setMsg("鉴权失败，非用户本人");
        return msg;
    }

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
    public int postActivity(int uid, double latitude, double longitude, String content,  MultipartFile[] files,int fileType
            ,HttpServletRequest request){
        Activity activity = new Activity();
        activity.setCreateTime(new Date());
        activity.setUpdateTime(new Date());
        activity.setPublishId(uid);
        //使用Hutool进行json操作
        JSONArray array = JSONUtil.createArray();
        for (MultipartFile file : files) {
            if(fileType==1){
                Msg msg = FileUploadUtil.uploadUtil(file, "activity/img", request);
                if (msg.getCode() == 100) {
                    array.add(msg.getExtend().get("path"));
                }

            }
            else if(fileType==2){
                Msg msg = FileUploadUtil.uploadUtil(file, "activity/video", request);
                if (msg.getCode() == 100) {
                    array.add(msg.getExtend().get("path"));
                }
            }

        }
        if (array.size() == 0) {
            return 0;
        }
        if(fileType==1){
            activity.setFileType(1);
        }else if(fileType==2){
            activity.setFileType(2);
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
    public Msg listUserActivityByUid(int uid) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        //根据uid获取用户动态
        List<Activity> activities = listActivityByUid(uid);
        User user = userService.getUserById(uid);
        boolean boolUser = (user == null || user.getId() == 0 || user.getNickname().equals("找不到任何用户"));
        boolean flag = (activities ==null && activities.size()==0);
        //用户或动态不存在的话，返回默认信息，防止空指针异常
        if (boolUser|| flag) {
            return Msg.fail();
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
            hashMap.put("fileType", activity.getFileType());
            //发布时间
            Date createTime = activity.getCreateTime();
            //当前时间
            Date nowTime = DateUtil.date();
            //时间差
            String formatBetween = DateUtil.formatBetween(createTime, nowTime, BetweenFormater.Level.SECOND) + "前";
            //查询是否被我点赞过
            boolean boolLike = activityLikeService.getBoolLikeByAidUid(activity.getId(), uid);
            hashMap.put("time", formatBetween);
            hashMap.put("readNum", activity.getReadNum());
            hashMap.put("likeNum", activity.getLikeNum());
            hashMap.put("boolLike", boolLike);
            hashMap.put("commontNum", activity.getCommontNum());
            hashMap.put("uid", uid);
            hashMap.put("aid", activity.getId());
            list.add(hashMap);
        }
        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMsg("获取我的所有动态成功");
        msg.add("activityList", list);
        return msg;
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
        example.setOrderByClause("id DESC");
        ActivityExample.Criteria criteria = example.createCriteria();
        criteria.andPublishIdEqualTo(uid);
        return activityMapper.selectByExample(example);
    }

    @Override
    public List<Activity> listActivity() {
        List<Activity> activities = activityMapper.selectByExample(null);
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
        //从Redis获取附近的所有用户的动态id
        Coordinate coordinate = new Coordinate();
        coordinate.setKey(Integer.toString(uid));
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        List<GeoRadiusResponse> responseList = RedisUtil.geoQueryActivity(coordinate, radius);
        if (responseList == null || responseList.size() == 0) {
            return Msg.fail();
        }
        for (GeoRadiusResponse response : responseList) {
            //在Redis缓存中拿到动态id
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
                continue;
            }
            User user1 = userMapper.selectByPrimaryKey(activity.getPublishId());
            boolean boolUser = (user1 == null || user1.getId()==0 || user1.getNickname().equals("找不到任何用户"));
            if (boolUser) {
                continue;
            }
            //判断这条动态的主人是不是被屏蔽
            if (user1.getBoolClose()) {
                //如果被屏蔽就直接走下一条动态
                continue;
            }
            //===============如果activity存在==============
            //判断这条动态是否被屏蔽了
            if (activity.getBoolClose()) {
                //如果这条动态被屏蔽的话，直接走下一条附近动态
                continue;
            }
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            //获得该动态的用户信息
            User user = userService.getUserById(activity.getPublishId());
            //判断我自己是否点赞过这条动态
            boolean boolLike = activityLikeService.getBoolLikeByAidUid(activity.getId(), uid);
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
            hashMap.put("boolLike", boolLike);
            hashMap.put("uid", activity.getPublishId());
            hashMap.put("aid", activity.getId());
            hashMap.put("fileType",activity.getFileType());
            list.add(hashMap);
        }
        //对动态id从最新到最晚排序（按id递减排序）
        Collections.sort(list, new MyComparator());
        Msg msg = new Msg();
        msg.setCode(100);
        msg.setMsg("查找动态成功");
        msg.add("activityList",list);
        return msg;
    }

    /**
     * 获得我自己的所有动态
     * @param uid
     * @return
     */
    @Override
    public List<Activity> listMyActvityByUid(int uid) {
        List<Activity> list = new ArrayList<Activity>();
        ActivityExample example = new ActivityExample();
        ActivityExample.Criteria criteria = example.createCriteria();
        criteria.andPublishIdEqualTo(uid);
        List<Activity> activities = activityMapper.selectByExample(example);
        if (activities == null || activities.isEmpty()) {
            Activity activity = new Activity();
            activity.setId(0);
            activity.setContent("动态不存在");
            list.add(activity);
            return list;
        }
        return activities;
    }

}
