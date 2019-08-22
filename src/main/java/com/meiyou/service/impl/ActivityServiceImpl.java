package com.meiyou.service.impl;

import com.meiyou.mapper.ActivityMapper;
import com.meiyou.pojo.Activity;
import com.meiyou.pojo.ActivityExample;
import com.meiyou.service.ActivityService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public int postActivity(int uid, MultipartFile file, String content
            , HttpServletRequest request, HttpServletResponse responsee) throws IOException {
        //获得磁盘文件
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接暂存起来
        diskFileItemFactory.setSizeThreshold(10*1024*1024);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        //图片上传成功后，将图片的地址写到数据库
        String filePath = request.getServletContext().getRealPath("/resources/ActivityPic");//“/”表示WebRoot//保存图片的路径
        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        //新的文件名字
        String newFileName = UUID.randomUUID()+originalFilename;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        Activity activity = new Activity();
        activity.setCreateTime(new Date());
        activity.setUpdateTime(new Date());
        activity.setPublishId(uid);
        activity.setImgsUrl(newFileName);
        activity.setContent(content);
        activity.setReadNum(0);
        activity.setLikeNum(0);
        activity.setCommontNum(0);
        activity.setBoolClose(false);
        return activityMapper.insertSelective(activity);
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
