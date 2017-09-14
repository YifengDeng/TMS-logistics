package com.yydscm.Entity;

public class T_public_packing {
    private Long uuid;
    private String packing_name;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;
    private Long packing_status;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getPacking_name() {
        return packing_name;
    }

    public void setPacking_name(String packing_name) {
        this.packing_name = packing_name;
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

    public Long getPacking_status() {
        return packing_status;
    }

    public void setPacking_status(Long packing_status) {
        this.packing_status = packing_status;
    }
}
