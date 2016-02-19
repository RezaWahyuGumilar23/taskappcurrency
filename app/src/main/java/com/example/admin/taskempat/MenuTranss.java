package com.example.admin.taskempat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuTranss extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper db;
    EditText descriptionIn, amountIn, descriptionOut,amountOut;
    Button addIn, addOut;
    int status = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_transs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);

        descriptionIn = (EditText)findViewById(R.id.eTDesIncome);
        descriptionOut = (EditText)findViewById(R.id.eTDesExpenses);
        amountIn = (EditText)findViewById(R.id.eTAmoIncome);
        amountOut = (EditText)findViewById(R.id.eTAmoExpenses);
        addIn = (Button)findViewById(R.id.btnIncome);
        addOut = (Button)findViewById(R.id.btnExpen);

        addIn.setOnClickListener(this);
        addOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnIncome:
                Incomee income = new Incomee();
                income.setDescriptionIncome(descriptionIn.getText().toString());
                income.setAmountIncome(Integer.parseInt(amountIn.getText().toString()));
                db.addIncome(income);
                status = 1;
                if(status == 1){
                    Toast.makeText(MenuTranss.this, "Income Added", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MenuTranss.this, "Fails Add Income", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnExpen:
                Outcomee outcome = new Outcomee();
                outcome.setDescriptionOutcome(descriptionOut.getText().toString());
                outcome.setAmountOutcome(Integer.parseInt(amountOut.getText().toString()));
                db.addOutcome(outcome);
                status = 1;
                if(status == 1){
                    Toast.makeText(MenuTranss.this, "Expenses Added", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MenuTranss.this, "Fails Add Expenses", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
