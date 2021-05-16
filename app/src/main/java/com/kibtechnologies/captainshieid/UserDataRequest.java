package com.kibtechnologies.captainshieid;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class UserDataRequest {
    public File getImages() {
        return images;
    }

    public void setImages(File images) {
        this.images = images;
    }

    @SerializedName("images")
    private File images;

}
