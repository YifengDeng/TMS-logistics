package com.yydscm.Entity;

public class T_loading_batches {
    private Long uuid;
    private String batch_number;
    private Long loading_point;
    private java.sql.Timestamp loading_time;
    private String plate_numbers;
    private String driver_name;
    private String driver_phone;
    private Double pay_the_freight;
    private String pay_the_oil_card;
    private Double pay_back_freight;
    private Double monthly_freight;
    private String destination1;
    private String destination1_freight;
    private String destination2;
    private String destination2_freight;
    private String destination3;
    private String destination3_freight;
    private Long arrive_status;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getBatch_number() {
        return batch_number;
    }

    public void setBatch_number(String batch_number) {
        this.batch_number = batch_number;
    }

    public Long getLoading_point() {
        return loading_point;
    }

    public void setLoading_point(Long loading_point) {
        this.loading_point = loading_point;
    }

    public java.sql.Timestamp getLoading_time() {
        return loading_time;
    }

    public void setLoading_time(java.sql.Timestamp loading_time) {
        this.loading_time = loading_time;
    }

    public String getPlate_numbers() {
        return plate_numbers;
    }

    public void setPlate_numbers(String plate_numbers) {
        this.plate_numbers = plate_numbers;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public Double getPay_the_freight() {
        return pay_the_freight;
    }

    public void setPay_the_freight(Double pay_the_freight) {
        this.pay_the_freight = pay_the_freight;
    }

    public String getPay_the_oil_card() {
        return pay_the_oil_card;
    }

    public void setPay_the_oil_card(String pay_the_oil_card) {
        this.pay_the_oil_card = pay_the_oil_card;
    }

    public Double getPay_back_freight() {
        return pay_back_freight;
    }

    public void setPay_back_freight(Double pay_back_freight) {
        this.pay_back_freight = pay_back_freight;
    }

    public Double getMonthly_freight() {
        return monthly_freight;
    }

    public void setMonthly_freight(Double monthly_freight) {
        this.monthly_freight = monthly_freight;
    }

    public String getDestination1() {
        return destination1;
    }

    public void setDestination1(String destination1) {
        this.destination1 = destination1;
    }

    public String getDestination1_freight() {
        return destination1_freight;
    }

    public void setDestination1_freight(String destination1_freight) {
        this.destination1_freight = destination1_freight;
    }

    public String getDestination2() {
        return destination2;
    }

    public void setDestination2(String destination2) {
        this.destination2 = destination2;
    }

    public String getDestination2_freight() {
        return destination2_freight;
    }

    public void setDestination2_freight(String destination2_freight) {
        this.destination2_freight = destination2_freight;
    }

    public String getDestination3() {
        return destination3;
    }

    public void setDestination3(String destination3) {
        this.destination3 = destination3;
    }

    public String getDestination3_freight() {
        return destination3_freight;
    }

    public void setDestination3_freight(String destination3_freight) {
        this.destination3_freight = destination3_freight;
    }

    public Long getArrive_status() {
        return arrive_status;
    }

    public void setArrive_status(Long arrive_status) {
        this.arrive_status = arrive_status;
    }
}
