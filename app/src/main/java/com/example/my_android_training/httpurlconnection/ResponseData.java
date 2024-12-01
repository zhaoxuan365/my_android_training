package com.example.my_android_training.httpurlconnection;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;

public class ResponseData {

    private JsonElement data;
    private int errorCode;
    private String errorMsg;

    public JsonElement getData() {
        return data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseData{" +
                "data=" + data +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
