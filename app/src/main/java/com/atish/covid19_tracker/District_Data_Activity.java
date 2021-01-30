package com.atish.covid19_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atish.covid19_tracker.model.DistrictModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.atish.covid19_tracker.State_Details_Activity.districtModelList;

public class District_Data_Activity extends AppCompatActivity {
    int districtPosition;

    DistrictModel districtModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district__data_);
        //get Intent
        Intent intent = getIntent();
        districtPosition = intent.getIntExtra("position", 0);
//      create a districtModel
        DistrictModel districtModel = districtModelList.get(districtPosition);
        //set Tollbar
        Toolbar toolbar = findViewById(R.id.district_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data of " + districtModel.getDistrictName());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView district_total_case = findViewById(R.id.district_total_cases);
        TextView district_total_death = findViewById(R.id.district_total_death);
        TextView district_recovered = findViewById(R.id.district_recovered);
        TextView district_active = findViewById(R.id.district_active);
        TextView district_today_case = findViewById(R.id.district_today_case);
        TextView district_today_death = findViewById(R.id.district_today_death);
        TextView district_today_recovered = findViewById(R.id.district_today_recovered);

        district_total_case.setText(districtModel.getTotalCase());
        district_total_death.setText(districtModel.getDeceased());
        district_recovered.setText(districtModel.getRecovered());
        district_active.setText(districtModel.getActive());
        district_today_case.setText(districtModel.getToday_case());
        district_today_death.setText(districtModel.getToday_death());
        district_today_recovered.setText(districtModel.getToday_recovered());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(District_Data_Activity.this, State_Details_Activity.class));
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchMenuItem=menu.findItem(R.id.app_bar_search);
        searchMenuItem.setVisible(false);
        MenuItem track_state=menu.findItem(R.id.track_state);
        track_state.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.homeIcon){
            startActivity(new Intent(District_Data_Activity.this,MainActivity.class));
            finish();
        }if (id == R.id.about) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(District_Data_Activity.this);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(District_Data_Activity.this, State_Details_Activity.class));
        finish();
        super.onBackPressed();
    }

}