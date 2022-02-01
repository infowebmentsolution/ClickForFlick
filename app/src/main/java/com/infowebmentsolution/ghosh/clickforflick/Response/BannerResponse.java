package com.infowebmentsolution.ghosh.clickforflick.Response;

import com.infowebmentsolution.ghosh.clickforflick.Model.BannerList;

public class BannerResponse {
    private BannerList[] result;
    private String msg;

    public BannerResponse(BannerList[] result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public BannerList[] getResult() {
        return result;
    }

    public void setResult(BannerList[] result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

