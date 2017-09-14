package com.yydscm.Entity;

public class T_partner {
    private Long uuid;
    private Long company_uuid;
    private Long partner_company_id;
    private String partner_point_id;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getCompany_uuid() {
        return company_uuid;
    }

    public void setCompany_uuid(Long company_uuid) {
        this.company_uuid = company_uuid;
    }

    public Long getPartner_company_id() {
        return partner_company_id;
    }

    public void setPartner_company_id(Long partner_company_id) {
        this.partner_company_id = partner_company_id;
    }

    public String getPartner_point_id() {
        return partner_point_id;
    }

    public void setPartner_point_id(String partner_point_id) {
        this.partner_point_id = partner_point_id;
    }
}
