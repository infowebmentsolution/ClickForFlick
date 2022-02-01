package com.infowebmentsolution.ghosh.clickforflick.Model;

import java.io.Serializable;

public class SearchList implements Serializable {
    public String titleid;

    public SearchList(String titleid2) {
        this.titleid = titleid2;
    }

    public String getTitleid() {
        return this.titleid;
    }

    public void setTitleid(String titleid2) {
        this.titleid = titleid2;
    }
}