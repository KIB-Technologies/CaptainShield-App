package com.kibtechnologies.captainshieid.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.google.android.gms.fido.u2f.api.common.ResponseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Khushboo Jha on 6/1/21.
 */
public class OTPResponse implements Serializable {
    @SerializedName("response_code")
    private int response_code;
    @SerializedName("response_data")
    private AuthenticationData response_data;



    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public AuthenticationData getResponse_data() {
        return response_data;
    }

    public void setResponse_data(AuthenticationData response_data) {
        this.response_data = response_data;
    }

    @Override
    public String toString() {
        return "OTPResponse{" +
                "response_code=" + response_code +
                ", response_data=" + response_data +
                '}';
    }
}
