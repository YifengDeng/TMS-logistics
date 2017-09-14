package com.yydscm.Enum;

/**
 * Created by chenzhaopeng on 2017/6/19.
 */
public enum Const {
    USER,
    JWT_SECRET("hesheng168"),
    TOKEN;


    private String value;

    Const() {
    }

    Const(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
