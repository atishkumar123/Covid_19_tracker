package com.atish.covid19_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView totalCases, todayCases, totalDeaths, todayDeaths, recovered, todayRecovered, active, critical, affectedCountries;
    SimpleArcLoader simpleArcLoader;

    LinearLayout linearLayout;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        totalCases = findViewById(R.id.cases);
        todayCases = findViewById(R.id.today_case);
        totalDeaths = findViewById(R.id.death);
        todayDeaths = findViewById(R.id.today_death);

        recovered = findViewById(R.id.recovered);
        todayRecovered = findViewById(R.id.today_recovered);
        active = findViewById(R.id.active);
        critical = findViewById(R.id.critical);
        affectedCountries = findViewById(R.id.affected_countres);


        simpleArcLoader = findViewById(R.id.loader);
        pieChart = findViewById(R.id.piechart);
        linearLayout = findViewById(R.id.linearLayout);

        fetchData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.app_bar_search);
        searchMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.track_state) {
            startActivity(new Intent(MainActivity.this, StateList_Activity.class));
            finish();
        }
        if (id == R.id.homeIcon) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        }
        if (id == R.id.about) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            View mview = getLayoutInflater().inflate(R.layout.about_app, null);
            alert.setView(mview);
            final AlertDialog alertDialog = alert.create();
            TextView version=mview.findViewById(R.id.version);
            TextView close=mview.findViewById(R.id.close);
            version.setText("Version "+BuildConfig.VERSION_NAME);
            alertDialog.show();
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url = "https://disease.sh/v3/covid-19/all";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    totalCases.setText(jsonObject.getString("cases"));
                    todayCases.setText(jsonObject.getString("todayCases"));
                    totalDeaths.setText(jsonObject.getString("deaths"));
                    todayDeaths.setText(jsonObject.getString("todayDeaths"));
                    recovered.setText(jsonObject.getString("recovered"));
                    todayRecovered.setText(jsonObject.getString("todayRecovered"));
                    active.setText(jsonObject.getString("active"));
                    critical.setText(jsonObject.getString("critical"));
                    affectedCountries.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("totalCases", Integer.parseInt(totalCases.getText().toString()), Color.parseColor("#FDD835")));
                    pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(totalDeaths.getText().toString()), Color.parseColor("#E53935")));
                    pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(recovered.getText().toString()), Color.parseColor("#00897B")));
                    pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(active.getText().toString()), Color.parseColor("#039BE5")));
                    pieChart.startAnimation();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    public void goTrackCountry(View view) {
        Intent intent = new Intent(MainActivity.this, countries_list_Activity.class);
        startActivity(intent);
        finish();

    }

    public void onBackPressed() {

        new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_baseline_warning_24)
                .setTitle("Exit")
                .setMessage("Do you want to exit this app")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                    }
                }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

        // super.onBackPressed();
    }
//private Boolean exit = false;
//    @Override
//    public void onBackPressed() {
//        if (exit) {
//            finish(); // finish activity
//        } else {
//            Toast.makeText(this, "Press Back again to Exit.",
//                    Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    exit = false;
//                }
//            }, 3 * 1000);
//
//        }
//
//    }
}