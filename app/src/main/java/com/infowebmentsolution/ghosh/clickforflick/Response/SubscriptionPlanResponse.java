package com.infowebmentsolution.ghosh.clickforflick.Response;

import com.infowebmentsolution.ghosh.clickforflick.Model.SubscriptionPlansList;


public class SubscriptionPlanResponse {
    private SubscriptionPlansList[] result;
    String msg;

    public SubscriptionPlansList[] getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }
}
