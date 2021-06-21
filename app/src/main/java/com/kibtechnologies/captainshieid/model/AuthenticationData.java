package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Khushboo Jha on 6/2/21.
 */
public class AuthenticationData implements Serializable {
    @SerializedName("primaryNumber")
    private String primaryNumber;
    @SerializedName("hash")
    private String hash;
    @SerializedName("message")
    private String  message;
    @SerializedName("token")
    private String token;
    @SerializedName("_id")
    private String _id;

    public String getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "AuthenticationData{" +
                "primaryNumber='" + primaryNumber + '\'' +
                ", hash='" + hash + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}


