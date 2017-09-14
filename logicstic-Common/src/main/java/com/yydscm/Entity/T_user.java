package com.yydscm.Entity;

import java.io.Serializable;

public class T_user implements Serializable {
    private Long uuid;
    private Long company_uuid;
    private Long company_type;
    private Long role_uuid;
    private String head_img;
    private String user_name;
    private String phone_one;
    private String phone_two;
    private String pwd;
    private java.sql.Timestamp createtime;
    private java.sql.Timestamp last_login;
    private Long login_sum;
    private Long user_status;
    private Long recieve_msg;
    private String remark;
    private String userinfo_parameter;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getCompany_uuid() {
        return company_uuid;
    }

    public void setCompany_uuid(Long company_uuid) {
        this.company_uuid = company_uuid;
    }

    public Long getCompany_type() {
        return company_type;
    }

    public void setCompany_type(Long company_type) {
        this.company_type = company_type;
    }

    public Long getRole_uuid() {
        return role_uuid;
    }

    public void setRole_uuid(Long role_uuid) {
        this.role_uuid = role_uuid;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_one() {
        return phone_one;
    }

    public void setPhone_one(String phone_one) {
        this.phone_one = phone_one;
    }

    public String getPhone_two() {
        return phone_two;
    }

    public void setPhone_two(String phone_two) {
        this.phone_two = phone_two;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public java.sql.Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(java.sql.Timestamp createtime) {
        this.createtime = createtime;
    }

    public java.sql.Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(java.sql.Timestamp last_login) {
        this.last_login = last_login;
    }

    public Long getLogin_sum() {
        return login_sum;
    }

    public void setLogin_sum(Long login_sum) {
        this.login_sum = login_sum;
    }

    public Long getUser_status() {
        return user_status;
    }

    public void setUser_status(Long user_status) {
        this.user_status = user_status;
    }

    public Long getRecieve_msg() {
        return recieve_msg;
    }

    public void setRecieve_msg(Long recieve_msg) {
        this.recieve_msg = recieve_msg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserinfo_parameter() {
        return userinfo_parameter;
    }

    public void setUserinfo_parameter(String userinfo_parameter) {
        this.userinfo_parameter = userinfo_parameter;
    }
}
