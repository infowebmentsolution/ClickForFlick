package com.infowebmentsolution.ghosh.clickforflick.Model;
public class PaymentinfoList {
    private String amount;
    private String date;
    private String expirationdate;
    private String paymentby;
    private String paymentid;
    private String pid;
    private String useridforpayment;

    public PaymentinfoList(String pid2, String useridforpayment2, String paymentid2, String paymentby2, String date2, String expirationdate2, String amount2) {
        this.pid = pid2;
        this.useridforpayment = useridforpayment2;
        this.paymentid = paymentid2;
        this.paymentby = paymentby2;
        this.date = date2;
        this.expirationdate = expirationdate2;
        this.amount = amount2;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid2) {
        this.pid = pid2;
    }

    public String getUseridforpayment() {
        return this.useridforpayment;
    }

    public void setUseridforpayment(String useridforpayment2) {
        this.useridforpayment = useridforpayment2;
    }

    public String getPaymentid() {
        return this.paymentid;
    }

    public void setPaymentid(String paymentid2) {
        this.paymentid = paymentid2;
    }

    public String getPaymentby() {
        return this.paymentby;
    }

    public void setPaymentby(String paymentby2) {
        this.paymentby = paymentby2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public String getExpirationdate() {
        return this.expirationdate;
    }

    public void setExpirationdate(String expirationdate2) {
        this.expirationdate = expirationdate2;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount2) {
        this.amount = amount2;
    }
}
