package com.infowebmentsolution.ghosh.clickforflick.Model;

import java.io.Serializable;

public class ProfileUpdateList implements Serializable {

    private String name;
    private String useraddress;
    private String userid;


    public ProfileUpdateList(String name, String useraddress, String userid) {
        this.name = name;
        this.useraddress = useraddress;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
