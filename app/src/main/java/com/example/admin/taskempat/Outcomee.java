package com.example.admin.taskempat;

/**
 * Created by Admin on 10/02/2016.
 */
public class Outcomee {
    int _id=0;
    String descriptionOutcome = "";
    int amountOutcome = 0;

    public Outcomee()
    {

    }
    public Outcomee(String descriptionIncome, int amountIncome)
    {
        super();
        this.descriptionOutcome = descriptionIncome;
        this.amountOutcome = amountIncome;
    }

    public int getId()
    {
        return _id;
    }
    public void setId(int id)
    {
        this._id = id;
    }

    public String getDescriptionOutcome() {
        return descriptionOutcome;
    }
    public void setDescriptionOutcome(String descriptionOutcome) {
        this.descriptionOutcome = descriptionOutcome;
    }
    public int getAmountOutcome() {
        return amountOutcome;
    }
    public void setAmountOutcome(int amountOutcome) {
        this.amountOutcome = amountOutcome;
    }
}
