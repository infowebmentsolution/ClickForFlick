package com.infowebmentsolution.ghosh.clickforflick.Response;


import com.infowebmentsolution.ghosh.clickforflick.Model.ProfileUpdateList;

public class ProfileUpdateResponse {

    private ProfileUpdateList[] result;
    private String msg;


    public ProfileUpdateResponse(ProfileUpdateList[] result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public ProfileUpdateList[] getResult() {
        return result;
    }

    public void setResult(ProfileUpdateList[] result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
