package com.infowebmentsolution.ghosh.clickforflick.Response;

import com.infowebmentsolution.ghosh.clickforflick.Model.PaymentinfoList;

import java.util.ArrayList;

public class PaymentinfoResponse {

    private PaymentinfoList[] result;
    private String msg;

    public PaymentinfoResponse(PaymentinfoList[] result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public PaymentinfoList[] getResult() {
        return result;
    }

    public void setResult(PaymentinfoList[] result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
