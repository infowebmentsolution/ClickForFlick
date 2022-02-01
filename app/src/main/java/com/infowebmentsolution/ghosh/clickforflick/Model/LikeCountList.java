package com.infowebmentsolution.ghosh.clickforflick.Model;

import java.io.Serializable;

public class LikeCountList {

    private String likecount;

    public LikeCountList(String likecount) {
        this.likecount = likecount;
    }


    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }
}
