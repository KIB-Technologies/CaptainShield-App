package com.kibtechnologies.captainshieid;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    private String data;
    private String _user;
    private String activationCode;

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

}
