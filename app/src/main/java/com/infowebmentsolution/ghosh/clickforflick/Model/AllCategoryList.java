package com.infowebmentsolution.ghosh.clickforflick.Model;

public class AllCategoryList {
    private String cid ;
    private String cname;
    private String code;
    private String cat_url;
    private String catstatus;

    public AllCategoryList(String cid, String cname, String code, String cat_url, String catstatus) {
        this.cid = cid;
        this.cname = cname;
        this.code = code;
        this.cat_url = cat_url;
        this.catstatus = catstatus;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCat_url() {
        return cat_url;
    }

    public void setCat_url(String cat_url) {
        this.cat_url = cat_url;
    }

    public String getCatstatus() {
        return catstatus;
    }

    public void setCatstatus(String catstatus) {
        this.catstatus = catstatus;
    }
}
