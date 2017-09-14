package com.yydscm.Entity;

public class T_billing_log {
    private Long uuid;
    private Long billing_uuid;
    private Long point_uuid;
    private java.sql.Timestamp operator_date;
    private Long operator_uuid;
    private Long billing_status;
    private String billing_remark;

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

    public Long getPoint_uuid() {
        return point_uuid;
    }

    public void setPoint_uuid(Long point_uuid) {
        this.point_uuid = point_uuid;
    }

    public java.sql.Timestamp getOperator_date() {
        return operator_date;
    }

    public void setOperator_date(java.sql.Timestamp operator_date) {
        this.operator_date = operator_date;
    }

    public Long getOperator_uuid() {
        return operator_uuid;
    }

    public void setOperator_uuid(Long operator_uuid) {
        this.operator_uuid = operator_uuid;
    }

    public Long getBilling_status() {
        return billing_status;
    }

    public void setBilling_status(Long billing_status) {
        this.billing_status = billing_status;
    }

    public String getBilling_remark() {
        return billing_remark;
    }

    public void setBilling_remark(String billing_remark) {
        this.billing_remark = billing_remark;
    }
}
