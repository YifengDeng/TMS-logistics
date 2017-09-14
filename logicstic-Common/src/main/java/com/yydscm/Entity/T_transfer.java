package com.yydscm.Entity;

public class T_transfer {
    private Long uuid;
    private Long to_point_uuid;
    private java.sql.Timestamp transfer_date;
    private Double transfer_fee;
    private Long point_uuid;
    private Long operator_id;
    private Long transfer_type;
    private String billing_uuid;
    private String remark;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getTo_point_uuid() {
        return to_point_uuid;
    }

    public void setTo_point_uuid(Long to_point_uuid) {
        this.to_point_uuid = to_point_uuid;
    }

    public java.sql.Timestamp getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(java.sql.Timestamp transfer_date) {
        this.transfer_date = transfer_date;
    }

    public Double getTransfer_fee() {
        return transfer_fee;
    }

    public void setTransfer_fee(Double transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    public Long getPoint_uuid() {
        return point_uuid;
    }

    public void setPoint_uuid(Long point_uuid) {
        this.point_uuid = point_uuid;
    }

    public Long getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(Long operator_id) {
        this.operator_id = operator_id;
    }

    public Long getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(Long transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getBilling_uuid() {
        return billing_uuid;
    }

    public void setBilling_uuid(String billing_uuid) {
        this.billing_uuid = billing_uuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
