package com.meiyou.model;

import java.io.Serializable;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/21 14:38
 * @description：动态视图模型
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
public class ActivityModel implements Serializable {

    //用户id
    private int uid;

    //用户头像
    private String headPic;

    //动态文字
    private String content;

    //动态图片
    private String imgsUrl;

    //距离
    private String distance;

    //发布时间
    private String postTime;

    //点赞数
    private int likeNum;

    //评论数
     private int commentNum;



}
