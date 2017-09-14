package com.yydscm.Entity;

public class T_logistics {
    private Long uuid;
    private Long market_uuid;
    private String company_name;
    private String company_addr;
    private String company_phone;
    private String company_owner;
    private String company_owner_phone_o;
    private String company_owner_phone_t;
    private String company_longitude;
    private String company_latitude;
    private String company_scope;
    private Long company_status;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;
    private String salespeople;
    private java.sql.Timestamp registration_date;
    private Long default_order;
    private String point_introduction;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getMarket_uuid() {
        return market_uuid;
    }

    public void setMarket_uuid(Long market_uuid) {
        this.market_uuid = market_uuid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_addr() {
        return company_addr;
    }

    public void setCompany_addr(String company_addr) {
        this.company_addr = company_addr;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public String getCompany_owner() {
        return company_owner;
    }

    public void setCompany_owner(String company_owner) {
        this.company_owner = company_owner;
    }

    public String getCompany_owner_phone_o() {
        return company_owner_phone_o;
    }

    public void setCompany_owner_phone_o(String company_owner_phone_o) {
        this.company_owner_phone_o = company_owner_phone_o;
    }

    public String getCompany_owner_phone_t() {
        return company_owner_phone_t;
    }

    public void setCompany_owner_phone_t(String company_owner_phone_t) {
        this.company_owner_phone_t = company_owner_phone_t;
    }

    public String getCompany_longitude() {
        return company_longitude;
    }

    public void setCompany_longitude(String company_longitude) {
        this.company_longitude = company_longitude;
    }

    public String getCompany_latitude() {
        return company_latitude;
    }

    public void setCompany_latitude(String company_latitude) {
        this.company_latitude = company_latitude;
    }

    public String getCompany_scope() {
        return company_scope;
    }

    public void setCompany_scope(String company_scope) {
        this.company_scope = company_scope;
    }

    public Long getCompany_status() {
        return company_status;
    }

    public void setCompany_status(Long company_status) {
        this.company_status = company_status;
    }

    public java.sql.Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(java.sql.Timestamp create_time) {
        this.create_time = create_time;
    }

    public java.sql.Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(java.sql.Timestamp update_time) {
        this.update_time = update_time;
    }

    public String getSalespeople() {
        return salespeople;
    }

    public void setSalespeople(String salespeople) {
        this.salespeople = salespeople;
    }

    public java.sql.Timestamp getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(java.sql.Timestamp registration_date) {
        this.registration_date = registration_date;
    }

    public Long getDefault_order() {
        return default_order;
    }

    public void setDefault_order(Long default_order) {
        this.default_order = default_order;
    }

    public String getPoint_introduction() {
        return point_introduction;
    }

    public void setPoint_introduction(String point_introduction) {
        this.point_introduction = point_introduction;
    }
}
