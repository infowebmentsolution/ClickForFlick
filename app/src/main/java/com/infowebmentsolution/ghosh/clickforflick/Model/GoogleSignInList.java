package com.infowebmentsolution.ghosh.clickforflick.Model;

import java.io.Serializable;

public class GoogleSignInList implements Serializable {
    private String username;
    private String useremail;
    private String authId;
    private String userFingerPrint;

    public GoogleSignInList(String username, String useremail, String authId, String userFingerPrint) {
        this.username = username;
        this.useremail = useremail;
        this.authId = authId;
        this.userFingerPrint = userFingerPrint;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getUserFingerPrint() {
        return userFingerPrint;
    }

    public void setUserFingerPrint(String userFingerPrint) {
        this.userFingerPrint = userFingerPrint;
    }
}
