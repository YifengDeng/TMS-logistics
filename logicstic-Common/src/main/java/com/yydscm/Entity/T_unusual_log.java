package com.yydscm.Entity;

public class T_unusual_log {
    private Long uuid;
    private Long billing_uuid;
    private java.sql.Timestamp record_date;
    private Long record_point_uuid;
    private Long recorder_uuid;
    private String unusual_type;
    private String unusual_des;
    private String processor;
    private java.sql.Timestamp processed_date;
    private String processed_results;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getBilling_uuid() {
        return billing_uuid;
    }

    public void setBilling_uuid(Long billing_uuid) {
        this.billing_uuid = billing_uuid;
    }

    public java.sql.Timestamp getRecord_date() {
        return record_date;
    }

    public void setRecord_date(java.sql.Timestamp record_date) {
        this.record_date = record_date;
    }

    public Long getRecord_point_uuid() {
        return record_point_uuid;
    }

    public void setRecord_point_uuid(Long record_point_uuid) {
        this.record_point_uuid = record_point_uuid;
    }

    public Long getRecorder_uuid() {
        return recorder_uuid;
    }

    public void setRecorder_uuid(Long recorder_uuid) {
        this.recorder_uuid = recorder_uuid;
    }

    public String getUnusual_type() {
        return unusual_type;
    }

    public void setUnusual_type(String unusual_type) {
        this.unusual_type = unusual_type;
    }

    public String getUnusual_des() {
        return unusual_des;
    }

    public void setUnusual_des(String unusual_des) {
        this.unusual_des = unusual_des;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public java.sql.Timestamp getProcessed_date() {
        return processed_date;
    }

    public void setProcessed_date(java.sql.Timestamp processed_date) {
        this.processed_date = processed_date;
    }

    public String getProcessed_results() {
        return processed_results;
    }

    public void setProcessed_results(String processed_results) {
        this.processed_results = processed_results;
    }
}
