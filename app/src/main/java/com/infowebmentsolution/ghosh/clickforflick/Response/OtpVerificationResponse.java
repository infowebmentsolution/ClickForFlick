package com.infowebmentsolution.ghosh.clickforflick.Response;

public class OtpVerificationResponse {
    private String result;
    private String msg;
    private String userID;

    public OtpVerificationResponse(String result, String msg,String userID) {
        this.result = result;
        this.msg = msg;
        this.userID= userID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getuserID() {
        return userID;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }
}
