package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class Banner implements Serializable {
    @SerializedName("banners")
    private List<SliderItem> banners;
    @SerializedName("total")
    private int total;

    public List<SliderItem> getBanners() {
        return banners;
    }

    public void setBanners(List<SliderItem> banners) {
        this.banners = banners;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
