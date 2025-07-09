package com.example.prm392_group_project.models;

import com.google.gson.annotations.SerializedName;

public class ResponseMessage<T> {
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("data")
    private T data;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isSuccess() { return isSuccess; }
    public void setSuccess(boolean success) { isSuccess = success; }
    public int getErrorCode() { return errorCode; }
    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}