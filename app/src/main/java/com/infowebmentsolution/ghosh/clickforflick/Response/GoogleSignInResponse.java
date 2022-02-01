package com.infowebmentsolution.ghosh.clickforflick.Response;

import com.infowebmentsolution.ghosh.clickforflick.Model.GoogleSignInList;

public class GoogleSignInResponse {
    private GoogleSignInList[] results;
    private String msg;
    private String userID;

    public GoogleSignInResponse(GoogleSignInList[] results, String msg, String userID) {
        this.results = results;
        this.msg = msg;
        this.userID = userID;
    }

    public GoogleSignInList[] getResults() {
        return results;
    }

    public void setResults(GoogleSignInList[] results) {
        this.results = results;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
