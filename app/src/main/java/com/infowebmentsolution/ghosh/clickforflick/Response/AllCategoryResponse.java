package com.infowebmentsolution.ghosh.clickforflick.Response;

import com.infowebmentsolution.ghosh.clickforflick.Model.AllCategoryList;

public class AllCategoryResponse {
    private AllCategoryList[] result;
    private String msg;

    public AllCategoryResponse(AllCategoryList[] result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public AllCategoryList[] getResult() {
        return result;
    }

    public void setResult(AllCategoryList[] result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
