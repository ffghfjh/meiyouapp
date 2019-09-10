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
    DELETE(3,"(取消状态2-3)购买者删除,购买者看不到的状态"),
    CUT(4,"(取消状态2-4)发布者删除,发布者看不到的状态"),
    IGNORE(5,"忽略状态，双方不想看到"),
    EXPIRE(6,"已过期"),
    DUMP(7,"(完成状态1-7)购买者删除,购买者看不到的状态"),
    RESET(8,"(完成状态1-8)发布者删除,发布者看不到的状态");


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
