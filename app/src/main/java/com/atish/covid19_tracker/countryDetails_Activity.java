package com.atish.covid19_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atish.covid19_tracker.model.CountryModel;
import com.bumptech.glide.Glide;

import static com.atish.covid19_tracker.countries_list_Activity.countryModelList;

public class countryDetails_Activity extends AppCompatActivity {
    private int countryPosition;
    TextView totalCases,todayCases,totalDeaths,todayDeaths,recovered,todayRecovered,active,critical,countryNameTrack;
    ImageView countryImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        //get Intent
        Intent intent=getIntent();
        countryPosition=intent.getIntExtra("position",0);
        // create a countryModel
        CountryModel model=countryModelList.get(countryPosition);

        // set Toolbar
        Toolbar toolbar =findViewById(R.id.country_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Of "+model.getCountryName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // fin all view by their Id
        totalCases=findViewById(R.id.con_total_case);
        todayCases=findViewById(R.id.con_today_case);
        totalDeaths=findViewById(R.id.con_total_death);
        todayDeaths=findViewById(R.id.con_today_death);
        recovered=findViewById(R.id.con_recovered);
        todayRecovered=findViewById(R.id.con_today_recovered);
        active=findViewById(R.id.con_active);
        countryNameTrack=findViewById(R.id.countryNameTrack);
        countryImage=findViewById(R.id.countryImage);


        //set the value on all view

        Glide.with(this).load(model.getFlag()).into(countryImage);
        countryNameTrack.setText(model.getCountryName());
        totalCases.setText(model.getTotalCases());
        totalDeaths.setText(model.getTotalDeath());
        recovered.setText(model.getRecovered());
        active.setText(model.getActive());
        todayCases.setText(model.getTodayCase());
        todayDeaths.setText(model.getTodayDeath());
        todayRecovered.setText(model.getTodayRecover());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(countryDetails_Activity.this, countries_list_Activity.class));
                finish();

            }
        });


    }
   //inflate all menu in toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchMenuItem=menu.findItem(R.id.app_bar_search);
        searchMenuItem.setVisible(false);
        return true;
    }
    //perform operation on menu Item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.homeIcon){
            startActivity(new Intent(countryDetails_Activity.this,MainActivity.class));
            finish();
        }
        if(id==R.id.track_state){
            startActivity(new Intent(countryDetails_Activity.this,StateList_Activity.class));
            finish();
        }
        if (id == R.id.about) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(countryDetails_Activity.this);
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
    //

    @Override
    public void onBackPressed() {
        startActivity(new Intent(countryDetails_Activity.this,countries_list_Activity.class));
        finish();
        super.onBackPressed();
    }



}