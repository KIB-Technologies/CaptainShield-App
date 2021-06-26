package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SecNumResponse implements Serializable {
    @SerializedName("response_code")
    public int response_code;

    @SerializedName("response_data")
    public Userprofile response_data;
}
