package com.meiyou.myEnum;

/**
 * @description: 状态枚举类
 * @author: Mr.Z
 * @create: 2019-08-27 20:33
 **/
public enum StateEnum {
    INIT(0,"发布成功(未有人购买或报名),报名成功,购买成功"),
    COMPLETE(1,"已确定(已有人购买或报名,不能取消的),已完成"),
    INVALID(2,"取消,已失效"),
    DELETE(3,"已删除"),
    EXPIRE(4,"已过期");

    private int value;
    private String desc;

    StateEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
