package com.yydscm.Entity;

public class T_logistics_notice {
    private Long uuid;
    private Long logistics_uuid;
    private Long logistics_sort;
    private String logistics_notice;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;

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

    public Long getLogistics_sort() {
        return logistics_sort;
    }

    public void setLogistics_sort(Long logistics_sort) {
        this.logistics_sort = logistics_sort;
    }

    public String getLogistics_notice() {
        return logistics_notice;
    }

    public void setLogistics_notice(String logistics_notice) {
        this.logistics_notice = logistics_notice;
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
}
