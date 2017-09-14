package com.yydscm.view;

/**
 * Created by chenzhaopeng on 2017/6/8.
 */


/**
 * 最顶层
 *
 * @param <T>
 */
public class Result<T> {
    /*状态码*/
    private Integer code;
    private String codeStr;
    /*提示信息*/
    private String msg;
    /*数据*/
    private T data;
    /*状态*/
    private Boolean flag;
    /*token*/
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Result(String codeStr, String msg, Boolean flag) {
        this.codeStr = codeStr;
        this.msg = msg;
        this.flag = flag;
    }


    public Result(Integer code, String msg, Boolean flag) {
        this.code = code;
        this.msg = msg;
        this.flag = flag;
    }

    public Result() {

    }

    public Result(Integer code, String msg, T data, Boolean flag) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.flag = flag;
    }

    public Result(String codeStr, String msg, T data, Boolean flag) {
        this.codeStr = codeStr;
        this.msg = msg;
        this.data = data;
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return this.data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public String getCodeStr() {
        return codeStr;
    }
}
