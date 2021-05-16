package com.kibtechnologies.captainshieid;

import com.google.gson.annotations.SerializedName;

public class UserRequest {
    @SerializedName("key")
    private String key;
    @SerializedName("primaryNumber")
    private String primaryNumber;

    public String getPrimaryNumber(String number) {
        return primaryNumber;
    }

    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public String getKey(String s) {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
