package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class AllOpratoersResponse implements Serializable {
    @SerializedName("response_code")
    private int response_code;
    @SerializedName("response_data")
    private OperatorsData response_data;


    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public OperatorsData getResponse_data() {
        return response_data;
    }

    public void setResponse_data(OperatorsData response_data) {
        this.response_data = response_data;
    }
}
