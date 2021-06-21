package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class Plandetail implements Serializable {
    @SerializedName("category")
    private String category;
    @SerializedName("categoryid")
    private String categoryid;
    @SerializedName("description")
    private String description;
    @SerializedName("amount")
    private String amount;
    @SerializedName("talktime")
    private String talktime;
    @SerializedName("validity")
    private String validity;
    @SerializedName("validityInDays")
    private String validityInDays;
    @SerializedName("isNewPlan")
    private String isNewPlan;
    @SerializedName("popular")
    private String popular;
    @SerializedName("operator_code")
    private String operator_code;
    @SerializedName("operator_name")
    private String operator_name;
    @SerializedName("circle_code")
    private String circle_code;
    @SerializedName("circle_name")
    private String circle_name;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTalktime() {
        return talktime;
    }

    public void setTalktime(String talktime) {
        this.talktime = talktime;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getValidityInDays() {
        return validityInDays;
    }

    public void setValidityInDays(String validityInDays) {
        this.validityInDays = validityInDays;
    }

    public String getIsNewPlan() {
        return isNewPlan;
    }

    public void setIsNewPlan(String isNewPlan) {
        this.isNewPlan = isNewPlan;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getOperator_code() {
        return operator_code;
    }

    public void setOperator_code(String operator_code) {
        this.operator_code = operator_code;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getCircle_code() {
        return circle_code;
    }

    public void setCircle_code(String circle_code) {
        this.circle_code = circle_code;
    }

    public String getCircle_name() {
        return circle_name;
    }

    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
    }
}
