package com.infowebmentsolution.ghosh.clickforflick.Model;

import java.io.Serializable;

public class SubscriptionPlansList implements Serializable {

    private String subscriptionid;
    private String subscriptionamount;
    private String subscriptionduration;
    private String subscriptionstatus;

    public SubscriptionPlansList(String subscriptionid, String subscriptionamount, String subscriptionduration, String subscriptionstatus) {
        this.subscriptionid = subscriptionid;
        this.subscriptionamount = subscriptionamount;
        this.subscriptionduration = subscriptionduration;
        this.subscriptionstatus = subscriptionstatus;
    }

    public String getSubscriptionid() {
        return subscriptionid;
    }

    public void setSubscriptionid(String subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

    public String getSubscriptionamount() {
        return subscriptionamount;
    }

    public void setSubscriptionamount(String subscriptionamount) {
        this.subscriptionamount = subscriptionamount;
    }

    public String getSubscriptionduration() {
        return subscriptionduration;
    }

    public void setSubscriptionduration(String subscriptionduration) {
        this.subscriptionduration = subscriptionduration;
    }

    public String getSubscriptionstatus() {
        return subscriptionstatus;
    }

    public void setSubscriptionstatus(String subscriptionstatus) {
        this.subscriptionstatus = subscriptionstatus;
    }
}
