package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Khushboo Jha on 5/29/21.
 */
public class SliderItem {
    @SerializedName("filePath")
    private String filePath;
    @SerializedName("imageURL")
    private String imageURL;
    @SerializedName("_id")
    private String _id;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "SliderItem{" +
                "filePath='" + filePath + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", _id=" + _id +
                '}';
    }
}
