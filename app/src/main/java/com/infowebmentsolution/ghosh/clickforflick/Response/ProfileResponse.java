package com.infowebmentsolution.ghosh.clickforflick.Response;


import com.infowebmentsolution.ghosh.clickforflick.Model.ProfileList;

public class ProfileResponse {

    private ProfileList[] result;
    private String msg;

    public ProfileResponse(ProfileList[] result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public ProfileList[] getResult() {
        return result;
    }

    public void setResult(ProfileList[] result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
