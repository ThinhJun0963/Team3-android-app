package com.example.prm392_group_project.models;

public class ZaloPayResponseDTO {
    private int returnCode;
    private String returnMessage;
    private int subReturnCode;
    private String subReturnMessage;
    private String orderUrl;
    private String zpTransToken;
    private String orderToken;
    private String qrCode;
    private int appId;

    // Getters and Setters
    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public int getSubReturnCode() {
        return subReturnCode;
    }

    public void setSubReturnCode(int subReturnCode) {
        this.subReturnCode = subReturnCode;
    }

    public String getSubReturnMessage() {
        return subReturnMessage;
    }

    public void setSubReturnMessage(String subReturnMessage) {
        this.subReturnMessage = subReturnMessage;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public String getZpTransToken() {
        return zpTransToken;
    }

    public void setZpTransToken(String zpTransToken) {
        this.zpTransToken = zpTransToken;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
}
