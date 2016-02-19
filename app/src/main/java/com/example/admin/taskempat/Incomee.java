package com.example.admin.taskempat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 10/02/2016.
 */
public class Incomee {

    @SerializedName("_id")
    int _id=0;
    @SerializedName("description")
    String descriptionIncome = "";
    @SerializedName("amount")
    int amountIncome = 0;

    public Incomee()
    {

    }

    public Incomee(int _id, String descriptionIncome, int amountIncome)
    {
        this._id = _id;
        this.descriptionIncome = descriptionIncome;
        this.amountIncome = amountIncome;
    }

    public int getId()
    {
        return _id;
    }
    public void setId(int _id)
    {
        this._id = _id;
    }

    public String getDescriptionIncome() {
        return descriptionIncome;
    }
    public void setDescriptionIncome(String descriptionIncome) {
        this.descriptionIncome = descriptionIncome;
    }
    public int getAmountIncome() {
        return amountIncome;
    }
    public void setAmountIncome(int amountIncome) {
        this.amountIncome = amountIncome;
    }
}
