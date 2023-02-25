package com.cks.sso.enums;

public class GenderUtil {

    public  static GenderUtil MAN= new GenderUtil(1,"男");
    public  static GenderUtil WOMAN= new GenderUtil(2,"女");

    private   int value;
    private  String desc;

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    private GenderUtil(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
