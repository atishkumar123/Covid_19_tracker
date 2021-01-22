package com.atish.covid19_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atish.covid19_tracker.model.DistrictModel;
import com.atish.covid19_tracker.model.State_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StateData_Activity extends AppCompatActivity {
    State_Model state_model;
    DistrictModel districtModel;
     public static List<State_Model> state_modelList=new ArrayList<>();
     List<DistrictModel>districtModelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_data_);
        getSupportActionBar().setTitle("Affected State");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fetchStateData();

    }

    private void fetchStateData() {
        String url="https://api.covidindiatracker.com/state_data.json";

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=  jsonArray.getJSONObject(i);
                        String stateName=jsonObject.getString("state");
                        String totalCase=jsonObject.getString("confirmed");
                        String totalDeath=jsonObject.getString("deaths");
                        String recovered=jsonObject.getString("recovered");
                        String active=jsonObject.getString("active");
                        JSONArray jsonArrayDistrict=jsonObject.getJSONArray("districtData");
                        for(int k=0;k<jsonArrayDistrict.length();k++){
                            JSONObject jsonObject_District=jsonArrayDistrict.getJSONObject(k);
                            String D_totalCase=jsonObject_District.getString("confirmed");
                            String districtName=jsonObject_District.getString("name");
                            String D_totalDeath=jsonObject_District.getString("deaths");
                            String D_recovere=jsonObject_District.getString("recovered");
                            districtModel=new DistrictModel(districtName,D_totalCase,D_totalDeath,D_recovere);
                            districtModelList.add(districtModel);

                        }
                        state_model=new State_Model(stateName,totalCase,totalDeath,recovered,active,districtModelList);
                        state_modelList.add(state_model);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}