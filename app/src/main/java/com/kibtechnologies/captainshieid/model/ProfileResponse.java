package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class ProfileResponse implements Serializable {
    @SerializedName("response_code")
    public int response_code;

    @SerializedName("response_data")
    public Userprofile response_data;

    @SerializedName("premium")
    public Boolean premium;

    public Boolean getPremium() {
        return premium;
    }

    public void setPremium(Boolean premium) {
        this.premium = premium;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @SerializedName("key")
    public String key;

    @SerializedName("expiry")
    public String expiry;

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public Userprofile getResponse_data() {
        return response_data;
    }

    public void setResponse_data(Userprofile response_data) {
        this.response_data = response_data;
    }
}
