package com.yydscm.Entity;

public class T_logistics_point {
    private Long uuid;
    private Long logistics_uuid;
    private Long market_uuid;
    private String point_name;
    private String point_addr;
    private String point_phone;
    private String point_owner;
    private String point_owner_phone_o;
    private String point_owner_phone_t;
    private String point_longitude;
    private String point_latitude;
    private Long point_status;
    private java.sql.Timestamp create_time;
    private String point_code;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getLogistics_uuid() {
        return logistics_uuid;
    }

    public void setLogistics_uuid(Long logistics_uuid) {
        this.logistics_uuid = logistics_uuid;
    }

    public Long getMarket_uuid() {
        return market_uuid;
    }

    public void setMarket_uuid(Long market_uuid) {
        this.market_uuid = market_uuid;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getPoint_addr() {
        return point_addr;
    }

    public void setPoint_addr(String point_addr) {
        this.point_addr = point_addr;
    }

    public String getPoint_phone() {
        return point_phone;
    }

    public void setPoint_phone(String point_phone) {
        this.point_phone = point_phone;
    }

    public String getPoint_owner() {
        return point_owner;
    }

    public void setPoint_owner(String point_owner) {
        this.point_owner = point_owner;
    }

    public String getPoint_owner_phone_o() {
        return point_owner_phone_o;
    }

    public void setPoint_owner_phone_o(String point_owner_phone_o) {
        this.point_owner_phone_o = point_owner_phone_o;
    }

    public String getPoint_owner_phone_t() {
        return point_owner_phone_t;
    }

    public void setPoint_owner_phone_t(String point_owner_phone_t) {
        this.point_owner_phone_t = point_owner_phone_t;
    }

    public String getPoint_longitude() {
        return point_longitude;
    }

    public void setPoint_longitude(String point_longitude) {
        this.point_longitude = point_longitude;
    }

    public String getPoint_latitude() {
        return point_latitude;
    }

    public void setPoint_latitude(String point_latitude) {
        this.point_latitude = point_latitude;
    }

    public Long getPoint_status() {
        return point_status;
    }

    public void setPoint_status(Long point_status) {
        this.point_status = point_status;
    }

    public java.sql.Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(java.sql.Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getPoint_code() {
        return point_code;
    }

    public void setPoint_code(String point_code) {
        this.point_code = point_code;
    }
}
