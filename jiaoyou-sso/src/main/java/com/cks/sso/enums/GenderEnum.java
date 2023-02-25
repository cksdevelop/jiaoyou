package com.cks.sso.enums;

public enum GenderEnum {

    MAN(1,"男"), WOMAN(2,"女");

    private   int value;
    private  String desc;

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    GenderEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
