package com.atish.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atish.covid19_tracker.model.CountryModel;
import com.bumptech.glide.Glide;

import static com.atish.covid19_tracker.countriesDataActivity.countryModelList;

public class countryDetails_Activity extends AppCompatActivity {
    private int countryPosition;
    TextView totalCases,todayCases,totalDeaths,todayDeaths,recovered,todayRecovered,active,critical,countryNameTrack;
    ImageView countryImage;
    Button btn_state_tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        Intent intent=getIntent();
        countryPosition=intent.getIntExtra("position",0);

        CountryModel model=countryModelList.get(countryPosition);

        getSupportActionBar().setTitle("Data Of "+model.getCountryName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        totalCases=findViewById(R.id.con_total_case);
        todayCases=findViewById(R.id.con_today_case);
        totalDeaths=findViewById(R.id.con_total_death);
        todayDeaths=findViewById(R.id.con_today_death);
        recovered=findViewById(R.id.con_recovered);
        todayRecovered=findViewById(R.id.con_today_recovered);
        active=findViewById(R.id.con_active);
        critical=findViewById(R.id.con_critical);
        countryNameTrack=findViewById(R.id.countryNameTrack);

        countryImage=findViewById(R.id.countryImage);
        btn_state_tracker=findViewById(R.id.btn_State_traker);



        Glide.with(this).load(model.getFlag()).into(countryImage);
        countryNameTrack.setText(model.getCountryName());
        totalCases.setText(model.getTotalCases());
        totalDeaths.setText(model.getTotalDeath());
        recovered.setText(model.getRecovered());
        active.setText(model.getActive());
        critical.setText(model.getCriticle());
        todayCases.setText(model.getTodayCase());
        todayDeaths.setText(model.getTotalDeath());
        todayRecovered.setText(model.getRecovered());



        if(model.getCountryName().equals("India")){
            btn_state_tracker.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void goTrackState(View view) {
        Intent intent=new Intent(countryDetails_Activity.this,StateData_Activity.class);
        startActivity(intent);


    }
}