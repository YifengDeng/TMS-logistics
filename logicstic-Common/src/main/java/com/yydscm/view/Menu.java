package com.yydscm.view;

/**
 * Created by chenzhaopeng on 2017/6/21.
 */
public class Menu {
    private Long uuid;
    private Long parent_uuid;
    private String name;
    private String code;
    private String href;
    private String img;
    private Menu sub;


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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Menu getSub() {
        return sub;
    }

    public void setSub(Menu sub) {
        this.sub = sub;
    }
}
