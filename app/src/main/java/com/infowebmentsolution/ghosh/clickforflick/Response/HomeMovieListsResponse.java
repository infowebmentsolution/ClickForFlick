package com.infowebmentsolution.ghosh.clickforflick.Response;

import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;

public class HomeMovieListsResponse {
    private HomeMovieList[] result;
    private String msg;

    public HomeMovieListsResponse(HomeMovieList[] result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public HomeMovieList[] getResult() {
        return result;
    }

    public void setResult(HomeMovieList[] result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
