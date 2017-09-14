package com.yydscm.Entity;

public class T_billing {
    private Long uuid;
    private Long waybill_number;
    private Long initial_station_id;
    private Long terminal_station_id;
    private Long owner;
    private java.sql.Timestamp invoice_date;
    private Long consigner_uuid;
    private String consigner;
    private String consigner_phone;
    private String consigner_addr;
    private String consignee;
    private String consignee_phone;
    private String consignee_addr;
    private String goods_name;
    private Long quantity;
    private String packaging;
    private Double weight;
    private Double volume;
    private String delivery_method;
    private Long number_of_copies;
    private Long drawer_uuid;
    private Long invoice_status;
    private String article_number;
    private String transit_destination;
    private Long loading_batches_id;
    private Long source;
    private Long is_unusual;
    private String remarks;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(Long waybill_number) {
        this.waybill_number = waybill_number;
    }

    public Long getInitial_station_id() {
        return initial_station_id;
    }

    public void setInitial_station_id(Long initial_station_id) {
        this.initial_station_id = initial_station_id;
    }

    public Long getTerminal_station_id() {
        return terminal_station_id;
    }

    public void setTerminal_station_id(Long terminal_station_id) {
        this.terminal_station_id = terminal_station_id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public java.sql.Timestamp getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(java.sql.Timestamp invoice_date) {
        this.invoice_date = invoice_date;
    }

    public Long getConsigner_uuid() {
        return consigner_uuid;
    }

    public void setConsigner_uuid(Long consigner_uuid) {
        this.consigner_uuid = consigner_uuid;
    }

    public String getConsigner() {
        return consigner;
    }

    public void setConsigner(String consigner) {
        this.consigner = consigner;
    }

    public String getConsigner_phone() {
        return consigner_phone;
    }

    public void setConsigner_phone(String consigner_phone) {
        this.consigner_phone = consigner_phone;
    }

    public String getConsigner_addr() {
        return consigner_addr;
    }

    public void setConsigner_addr(String consigner_addr) {
        this.consigner_addr = consigner_addr;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_phone() {
        return consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }

    public String getConsignee_addr() {
        return consignee_addr;
    }

    public void setConsignee_addr(String consignee_addr) {
        this.consignee_addr = consignee_addr;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(String delivery_method) {
        this.delivery_method = delivery_method;
    }

    public Long getNumber_of_copies() {
        return number_of_copies;
    }

    public void setNumber_of_copies(Long number_of_copies) {
        this.number_of_copies = number_of_copies;
    }

    public Long getDrawer_uuid() {
        return drawer_uuid;
    }

    public void setDrawer_uuid(Long drawer_uuid) {
        this.drawer_uuid = drawer_uuid;
    }

    public Long getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(Long invoice_status) {
        this.invoice_status = invoice_status;
    }

    public String getArticle_number() {
        return article_number;
    }

    public void setArticle_number(String article_number) {
        this.article_number = article_number;
    }

    public String getTransit_destination() {
        return transit_destination;
    }

    public void setTransit_destination(String transit_destination) {
        this.transit_destination = transit_destination;
    }

    public Long getLoading_batches_id() {
        return loading_batches_id;
    }

    public void setLoading_batches_id(Long loading_batches_id) {
        this.loading_batches_id = loading_batches_id;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getIs_unusual() {
        return is_unusual;
    }

    public void setIs_unusual(Long is_unusual) {
        this.is_unusual = is_unusual;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
