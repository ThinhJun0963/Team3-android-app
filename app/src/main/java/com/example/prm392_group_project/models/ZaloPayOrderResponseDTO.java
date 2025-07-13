package com.example.prm392_group_project.models;

import com.google.gson.annotations.SerializedName;

public class ZaloPayOrderResponseDTO {
    @SerializedName("return_code")
    private int returnCode;

    @SerializedName("return_message")
    private String returnMessage;

    @SerializedName("sub_return_code")
    private int subReturnCode;

    @SerializedName("sub_return_message")
    private String subReturnMessage;

    @SerializedName("order_url")
    private String orderUrl;

    @SerializedName("zp_trans_token")
    private String zpTransToken;

    @SerializedName("order_token")
    private String orderToken;

    @SerializedName("qr_code")
    private String qrCode;

    private Integer appId;
}
