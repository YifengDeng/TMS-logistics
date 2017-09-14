package com.yydscm.Entity;

public class T_billing_fee {
    private Long uuid;                        //主键
    private Long billing_uuid;                //运单号
    private Double declared_value;            //申明价值
    private Double valuation_fee;                //保价费
    private Double delivery_fee;                //送货费
    private Double advance;                    //垫付款
    private Double receiving_fee;                //接货费
    private Double handling_fee;                //装卸费
    private Double packing_fee;                //包装费
    private Double upstair_fee;                //上楼费
    private Double forklift_fee;                //叉吊费
    private Double return_fee;                //现返费
    private Double under_charge_fee;            //欠返费
    private Double warehousing_fee;            //仓储费
    private Double collection_fee;            //代收费
    private Double freight;                    //运费
    private Double total_freight;                //运费合计
    private Double total_freight_receipts;    //
    private String payment_method;            //付款方式
    private Double cash_payment;                //现付
    private Double collect_payment;            //到付
    private Double default_of_payment;        //欠付
    private Double back_payment;                //回付
    private Double monthly_payment;            //月结
    private Double payment_deduction;            //货款扣

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

    public Double getDeclared_value() {
        return declared_value;
    }

    public void setDeclared_value(Double declared_value) {
        this.declared_value = declared_value;
    }

    public Double getValuation_fee() {
        return valuation_fee;
    }

    public void setValuation_fee(Double valuation_fee) {
        this.valuation_fee = valuation_fee;
    }

    public Double getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(Double delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getReceiving_fee() {
        return receiving_fee;
    }

    public void setReceiving_fee(Double receiving_fee) {
        this.receiving_fee = receiving_fee;
    }

    public Double getHandling_fee() {
        return handling_fee;
    }

    public void setHandling_fee(Double handling_fee) {
        this.handling_fee = handling_fee;
    }

    public Double getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(Double packing_fee) {
        this.packing_fee = packing_fee;
    }

    public Double getUpstair_fee() {
        return upstair_fee;
    }

    public void setUpstair_fee(Double upstair_fee) {
        this.upstair_fee = upstair_fee;
    }

    public Double getForklift_fee() {
        return forklift_fee;
    }

    public void setForklift_fee(Double forklift_fee) {
        this.forklift_fee = forklift_fee;
    }

    public Double getReturn_fee() {
        return return_fee;
    }

    public void setReturn_fee(Double return_fee) {
        this.return_fee = return_fee;
    }

    public Double getUnder_charge_fee() {
        return under_charge_fee;
    }

    public void setUnder_charge_fee(Double under_charge_fee) {
        this.under_charge_fee = under_charge_fee;
    }

    public Double getWarehousing_fee() {
        return warehousing_fee;
    }

    public void setWarehousing_fee(Double warehousing_fee) {
        this.warehousing_fee = warehousing_fee;
    }

    public Double getCollection_fee() {
        return collection_fee;
    }

    public void setCollection_fee(Double collection_fee) {
        this.collection_fee = collection_fee;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getTotal_freight() {
        return total_freight;
    }

    public void setTotal_freight(Double total_freight) {
        this.total_freight = total_freight;
    }

    public Double getTotal_freight_receipts() {
        return total_freight_receipts;
    }

    public void setTotal_freight_receipts(Double total_freight_receipts) {
        this.total_freight_receipts = total_freight_receipts;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Double getCash_payment() {
        return cash_payment;
    }

    public void setCash_payment(Double cash_payment) {
        this.cash_payment = cash_payment;
    }

    public Double getCollect_payment() {
        return collect_payment;
    }

    public void setCollect_payment(Double collect_payment) {
        this.collect_payment = collect_payment;
    }

    public Double getDefault_of_payment() {
        return default_of_payment;
    }

    public void setDefault_of_payment(Double default_of_payment) {
        this.default_of_payment = default_of_payment;
    }

    public Double getBack_payment() {
        return back_payment;
    }

    public void setBack_payment(Double back_payment) {
        this.back_payment = back_payment;
    }

    public Double getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(Double monthly_payment) {
        this.monthly_payment = monthly_payment;
    }

    public Double getPayment_deduction() {
        return payment_deduction;
    }

    public void setPayment_deduction(Double payment_deduction) {
        this.payment_deduction = payment_deduction;
    }
}
