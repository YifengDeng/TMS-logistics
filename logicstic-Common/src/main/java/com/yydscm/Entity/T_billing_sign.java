package com.yydscm.Entity;

public class T_billing_sign {
    private Long uuid;
    private Long billing_id;
    private Double receipts_total_freight;
    private Double receipts_collection_fee;
    private String sign_man;
    private String sign_man_idno;
    private Long point_uuid;
    private String point_operator;
    private java.sql.Timestamp sign_date;
    private String remark;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getBilling_id() {
        return billing_id;
    }

    public void setBilling_id(Long billing_id) {
        this.billing_id = billing_id;
    }

    public Double getReceipts_total_freight() {
        return receipts_total_freight;
    }

    public void setReceipts_total_freight(Double receipts_total_freight) {
        this.receipts_total_freight = receipts_total_freight;
    }

    public Double getReceipts_collection_fee() {
        return receipts_collection_fee;
    }

    public void setReceipts_collection_fee(Double receipts_collection_fee) {
        this.receipts_collection_fee = receipts_collection_fee;
    }

    public String getSign_man() {
        return sign_man;
    }

    public void setSign_man(String sign_man) {
        this.sign_man = sign_man;
    }

    public String getSign_man_idno() {
        return sign_man_idno;
    }

    public void setSign_man_idno(String sign_man_idno) {
        this.sign_man_idno = sign_man_idno;
    }

    public Long getPoint_uuid() {
        return point_uuid;
    }

    public void setPoint_uuid(Long point_uuid) {
        this.point_uuid = point_uuid;
    }

    public String getPoint_operator() {
        return point_operator;
    }

    public void setPoint_operator(String point_operator) {
        this.point_operator = point_operator;
    }

    public java.sql.Timestamp getSign_date() {
        return sign_date;
    }

    public void setSign_date(java.sql.Timestamp sign_date) {
        this.sign_date = sign_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
