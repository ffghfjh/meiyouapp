package com.meiyou.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.meiyou.mapper.ActivityMapper;
import com.meiyou.pojo.Activity;
import com.meiyou.pojo.ActivityExample;
import com.meiyou.service.ActivityService;
import com.meiyou.utils.FileUploadUtil;
import com.meiyou.utils.Msg;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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

    @Override
    public int postActivity(int uid, String content, MultipartFile[] files
            , HttpServletRequest request, HttpServletResponse response){
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
        int i = activityMapper.insertSelective(activity);
        if (i == 1) {
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

    @Override
    public List<Activity> listActivityByUid(int uid) {
        return null;
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

    @Override
    public List<Activity> listActivityBy(float longitude, float latitude) {
        return null;
    }
}
