package com.example.admin.taskempat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MenuDass extends AppCompatActivity {
    private DatabaseHelper db;
    ListView listIn,listOut;
    int totalBal=0;
    TextView  tvTotIn,tvTotOut,tvBal,desOut,amOut;
    Context context=this;
    Cursor Outcm,Incm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardView cardView = (CardView)findViewById(R.id.card_viewEx);
        cardView.setCardElevation(6f);

        tvTotIn = (TextView)findViewById(R.id.txtTotIn);
        tvTotOut = (TextView)findViewById(R.id.txtTotEx);
        tvBal = (TextView)findViewById(R.id.txtBalance);

        db = new DatabaseHelper(this);

        listIn = (ListView)findViewById(R.id.listIncome);
        listOut = (ListView)findViewById(R.id.listExpenses);

        loadDataIncome();
        loadDataOutcome();
        loadTotalBalance();

        listIn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Incm = db.getAllIncome();
                Incm.moveToPosition(position);
                final int idR = Incm.getInt(Incm.getColumnIndex(DatabaseHelper.COL_1));
                final String desR = Incm.getString(Incm.getColumnIndex(DatabaseHelper.COL_2));
                final String amR = Incm.getString(Incm.getColumnIndex(DatabaseHelper.COL_3));

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater=getLayoutInflater();
                final View views=inflater.inflate(R.layout.alert_income, null);
                builder.setTitle("Update or Delete Data Income");
                builder.setView(views);
                final EditText edt1 = (EditText)views.findViewById(R.id.eTDesIncomeUp);
                final EditText edt2 = (EditText)views.findViewById(R.id.eTAmoIncomeUp);
                edt1.setText(desR);
                edt2.setText(amR);
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Incomee income = new Incomee();
                        income.setId(idR);
                        income.setDescriptionIncome(edt1.getText().toString());
                        income.setAmountIncome(Integer.parseInt(edt2.getText().toString()));
                        db.updateIncome(income);
                        Toast.makeText(MenuDass.this, "Succes Change Data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadDataIncome();
                        loadTotalBalance();
                    }
                });
                builder.setNeutralButton("Delete", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteIncome(idR);
                        Toast.makeText(MenuDass.this, "Succes Delete Data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadDataIncome();
                        loadTotalBalance();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        listOut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                desOut = (TextView)findViewById(R.id.descriptionOutcome);
                amOut = (TextView) findViewById(R.id.amountOutcome);
                Outcm = db.getAllOutcome();
                Outcm.moveToPosition(position);
                final int idR = Outcm.getInt(Outcm.getColumnIndex(DatabaseHelper.COL_1Out));
                final String desR = Outcm.getString(Outcm.getColumnIndex(DatabaseHelper.COL_2Out));
                final String amR = Outcm.getString(Outcm.getColumnIndex(DatabaseHelper.COL_3Out));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = getLayoutInflater();
                final View views = inflater.inflate(R.layout.alert_income, null);
                builder.setTitle("Update or Delete Data Expenceses");
                builder.setView(views);

                final EditText edt1 = (EditText)views.findViewById(R.id.eTDesIncomeUp);
                final EditText edt2 = (EditText)views.findViewById(R.id.eTAmoIncomeUp);
                edt1.setText(desR);
                edt2.setText(amR);
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Outcomee outcome = new Outcomee();
                        outcome.setId(idR);
                        outcome.setDescriptionOutcome(edt1.getText().toString());
                        outcome.setAmountOutcome(Integer.parseInt(edt2.getText().toString()));
                        db.updateOutcome(outcome);
                        Toast.makeText(MenuDass.this, "Succes Change Data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadDataOutcome();
                        loadTotalBalance();
                    }
                });
                builder.setNeutralButton("Delete", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteOutcome(idR);
                        Toast.makeText(MenuDass.this, "Succes Delete Data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadDataOutcome();
                        loadTotalBalance();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
    private void loadDataIncome()
    {
        Cursor cur = db.getAllIncome();

        String[] from = new String[]
        {
            DatabaseHelper.COL_2, DatabaseHelper.COL_3
        };
        int[] to = new int[]
        {
            R.id.descriptionIncome, R.id.amountIncome
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_income, cur, from, to);
        adapter.notifyDataSetChanged();
        listIn.setAdapter(adapter);
    }
    private void loadDataOutcome()
    {
        Cursor cur = db.getAllOutcome();

        String[] from = new String[]
                {
                        DatabaseHelper.COL_2Out, DatabaseHelper.COL_3Out
                };
        int[] to = new int[]
                {
                        R.id.descriptionOutcome, R.id.amountOutcome
                };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_outcome, cur, from, to);
        adapter.notifyDataSetChanged();
        listOut.setAdapter(adapter);
    }
    private void loadTotalBalance(){
        int sum = db.getTotIn();
        tvTotIn.setText("$"+sum);

        int sum1 = db.getTotOut();
        tvTotOut.setText("$"+sum1);

        totalBal = sum - sum1;
        tvBal.setText(String.valueOf("$" + totalBal));
    }
}
