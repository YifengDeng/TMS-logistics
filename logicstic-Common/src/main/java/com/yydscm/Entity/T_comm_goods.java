package com.yydscm.Entity;

public class T_comm_goods {
    private Long uuid;
    private Long point_uuid;
    private String goods_name;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;
    private Long goods_status;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getPoint_uuid() {
        return point_uuid;
    }

    public void setPoint_uuid(Long point_uuid) {
        this.point_uuid = point_uuid;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
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

    public Long getGoods_status() {
        return goods_status;
    }

    public void setGoods_status(Long goods_status) {
        this.goods_status = goods_status;
    }
}
