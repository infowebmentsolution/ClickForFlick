package com.infowebmentsolution.ghosh.clickforflick.Model;


import java.io.Serializable;

public class ProfileList implements Serializable {
    private String userid;
    private String  username;
    private String  name;
    private String  useremail;
    private String  userphoneno;
    private String  useraddress;
    private String  usrstatus;
    private String  subscription;
    private String  userpassword;
    private String  userotp;
    private String  loginstatus;
    private String oauth_uid;

    public ProfileList(String userid, String username, String name, String useremail, String userphoneno, String useraddress, String usrstatus, String subscription, String userpassword, String userotp, String loginstatus, String oauth_uid) {
        this.userid = userid;
        this.username = username;
        this.name = name;
        this.useremail = useremail;
        this.userphoneno = userphoneno;
        this.useraddress = useraddress;
        this.usrstatus = usrstatus;
        this.subscription = subscription;
        this.userpassword = userpassword;
        this.userotp = userotp;
        this.loginstatus = loginstatus;
        this.oauth_uid = oauth_uid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserphoneno() {
        return userphoneno;
    }

    public void setUserphoneno(String userphoneno) {
        this.userphoneno = userphoneno;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getUsrstatus() {
        return usrstatus;
    }

    public void setUsrstatus(String usrstatus) {
        this.usrstatus = usrstatus;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUserotp() {
        return userotp;
    }

    public void setUserotp(String userotp) {
        this.userotp = userotp;
    }

    public String getLoginstatus() {
        return loginstatus;
    }

    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus;
    }

    public String getOauth_uid() {
        return oauth_uid;
    }

    public void setOauth_uid(String oauth_uid) {
        this.oauth_uid = oauth_uid;
    }
}

