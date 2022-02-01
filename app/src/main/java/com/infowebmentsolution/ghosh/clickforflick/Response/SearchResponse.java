package com.infowebmentsolution.ghosh.clickforflick.Response;


import com.infowebmentsolution.ghosh.clickforflick.Model.SearchList;

public class SearchResponse {


    public SearchList[] result;
    public String msg;

    public SearchResponse(SearchList[] result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public SearchList[] getResult() {
        return result;
    }

    public void setResult(SearchList[] result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
