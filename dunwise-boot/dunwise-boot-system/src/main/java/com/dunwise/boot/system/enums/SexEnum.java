package com.dunwise.boot.system.enums;

/**
 * 性别枚举
 */
public enum SexEnum {

    MALE("1", "男"),
    FEMALE("2", "女"),
    UNKNOWN("0", "未知");

    private final String code;
    private final String desc;

    SexEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
