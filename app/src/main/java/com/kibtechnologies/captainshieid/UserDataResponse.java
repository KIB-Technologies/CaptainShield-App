package com.kibtechnologies.captainshieid;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class UserDataResponse {

    public String message;

    @SerializedName("response_code")
    public String status;

    @SerializedName("stop")
    public boolean stop;

    @SerializedName("track")
    public String trackid;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @SerializedName("response_data")
    public String link;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public String getTrackid() {
        return trackid;
    }

    public void setTrackid(String trackid) {
        this.trackid = trackid;
    }
}
