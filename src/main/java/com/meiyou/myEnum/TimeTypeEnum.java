package com.meiyou.myEnum;

/**
 * @description: 时间状态枚举类
 * @author: Mr.Z
 * @create: 2019-08-27 21:08
 **/
public enum TimeTypeEnum {
    DAY(1,"一天"),
    WEEK(7,"一周"),
    MONTH(30,"一月"),
    QUARTER(90,"一季"),
    YEAR(365,"一年");

    private int value;
    private String desc;

    TimeTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 通过desc取枚举
     * @param desc
     * @return
     */
    public static TimeTypeEnum getTimeTypeByDesc(String desc){
        for(TimeTypeEnum timeTypeEnum : TimeTypeEnum.values()){
            if(timeTypeEnum.getDesc().equals(desc)){
                return timeTypeEnum;
            }
        }
        return null;
    }

    public static  TimeTypeEnum getTimeTypeByValue(int value){
        for(TimeTypeEnum timeTypeEnum : TimeTypeEnum.values()){
            if(timeTypeEnum.getValue() == value){
                return timeTypeEnum;
            }
        }
        return null;
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
