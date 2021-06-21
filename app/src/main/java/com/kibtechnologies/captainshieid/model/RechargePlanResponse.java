package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class RechargePlanResponse implements Serializable {
    @SerializedName("status")
    private String status;
    @SerializedName("error")
    private String error;
    @SerializedName("operator_code")
    private String operator_code;
    @SerializedName("operator_name")
    private String operator_name;
    @SerializedName("circle_code")
    private String circle_code;
    @SerializedName("circle_name")
    private String circle_name;
    @SerializedName("hits_left")
    private String hits_left;
    @SerializedName("plancategory")
    private List<Plancategory> plancategory;
    @SerializedName("plandetail")
    private List<Plandetail> plandetail;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public String getHits_left() {
        return hits_left;
    }

    public void setHits_left(String hits_left) {
        this.hits_left = hits_left;
    }

    public List<Plancategory> getPlancategory() {
        return plancategory;
    }

    public void setPlancategory(List<Plancategory> plancategory) {
        this.plancategory = plancategory;
    }

    public List<Plandetail> getPlandetail() {
        return plandetail;
    }

    public void setPlandetail(List<Plandetail> plandetail) {
        this.plandetail = plandetail;
    }
}
