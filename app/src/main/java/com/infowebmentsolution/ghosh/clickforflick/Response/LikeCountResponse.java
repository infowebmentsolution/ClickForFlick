package com.infowebmentsolution.ghosh.clickforflick.Response;

import com.infowebmentsolution.ghosh.clickforflick.Model.LikeCountList;

public class LikeCountResponse {

    private LikeCountList[] result;

    private String likecount;

    public LikeCountResponse(LikeCountList[] result, String likecount) {
        this.result = result;
        this.likecount = likecount;
    }

    public LikeCountList[] getResult() {
        return result;
    }

    public void setResult(LikeCountList[] result) {
        this.result = result;
    }

    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }
}