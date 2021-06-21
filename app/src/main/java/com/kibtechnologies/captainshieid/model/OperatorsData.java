package com.kibtechnologies.captainshieid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class OperatorsData implements Serializable {
    @SerializedName("operators")
    private List<Operator> operators;
    @SerializedName("total")
    private int total;

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
