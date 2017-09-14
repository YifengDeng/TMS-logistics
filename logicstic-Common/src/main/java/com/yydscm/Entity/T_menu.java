package com.yydscm.Entity;

public class T_menu {
    private Long uuid;
    private Long parent_uuid;
    private String name;
    private String code;
    private String href;
    private Long seq_no;
    private String remark;
    private String creater_id;
    private String create_time;
    private String status;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getParent_uuid() {
        return parent_uuid;
    }

    public void setParent_uuid(Long parent_uuid) {
        this.parent_uuid = parent_uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Long getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(Long seq_no) {
        this.seq_no = seq_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(String creater_id) {
        this.creater_id = creater_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
