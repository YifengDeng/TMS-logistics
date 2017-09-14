package com.yydscm.Entity;

public class T_system_parameter {
    private Long uuid;
    private Long logistics_uuid;
    private Double insured_rate;
    private Double commission_rate;

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

    public Double getInsured_rate() {
        return insured_rate;
    }

    public void setInsured_rate(Double insured_rate) {
        this.insured_rate = insured_rate;
    }

    public Double getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(Double commission_rate) {
        this.commission_rate = commission_rate;
    }
}
