package com.infowebmentsolution.ghosh.clickforflick.Response;

import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;

public class CategoryWiseResponse {
    private HomeMovieList[] catBanner;
    private HomeMovieList[] catVideo;
    private String msg;

    public CategoryWiseResponse(HomeMovieList[] catBanner2, HomeMovieList[] catVideo2, String msg2) {
        this.catBanner = catBanner2;
        this.catVideo = catVideo2;
        this.msg = msg2;
    }

    public HomeMovieList[] getCatBanner() {
        return this.catBanner;
    }

    public void setCatBanner(HomeMovieList[] catBanner2) {
        this.catBanner = catBanner2;
    }

    public HomeMovieList[] getCatVideo() {
        return this.catVideo;
    }

    public void setCatVideo(HomeMovieList[] catVideo2) {
        this.catVideo = catVideo2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }
}