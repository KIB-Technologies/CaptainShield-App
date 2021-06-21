package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class Plancategory implements Serializable {
    @SerializedName("category")
    private String category;
    @SerializedName("categoryid")
    private String categoryid;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
}
