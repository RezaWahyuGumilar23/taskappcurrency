package com.example.admin.taskempat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AplCurrency extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressBar progres;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apl_currency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progres = (ProgressBar)findViewById(R.id.progressBarSync);
        progres.setVisibility(View.INVISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.apl_currency, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dass) {
            Intent in = new Intent(AplCurrency.this, MenuDass.class);
            startActivity(in);
        } else if (id == R.id.nav_transs) {
            Intent an = new Intent(AplCurrency.this, MenuTranss.class);
            startActivity(an);
        } else if (id == R.id.nav_syncc) {
            sync();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sync() {
        final ProgressDialog progresdialog = new ProgressDialog(AplCurrency.this);
        progres.setVisibility(View.VISIBLE);
        progresdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progresdialog.setTitle("Please Wait...");
        progresdialog.setCancelable(false);

        progresdialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                progres.setVisibility(View.INVISIBLE);
                progresdialog.dismiss();
            }
        });
        progresdialog.show();


        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://private-d0f9d-taskfour1.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Incomee data = new Incomee();
        CurrenApi curren_api = retrofit.create(CurrenApi.class);
        Call<Incomee> call = curren_api.postData(data);
        call.enqueue(new Callback<Incomee>() {
            @Override
            public void onResponse(Response<Incomee> response, Retrofit retrofit) {
                if (response.code()==201) {
                    progres.setVisibility(View.INVISIBLE);
                    progresdialog.dismiss();
                    Toast.makeText(AplCurrency.this, "Succes synchronize", Toast.LENGTH_SHORT).show();
                }else if (response.code()==400){
                    Toast.makeText(AplCurrency.this, "Failed synchronize", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progres.setVisibility(View.INVISIBLE);
                progresdialog.dismiss();
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Network or Server Problem");
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sync();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
    }
}