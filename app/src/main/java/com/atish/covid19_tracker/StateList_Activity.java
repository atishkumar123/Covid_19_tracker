package com.atish.covid19_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atish.covid19_tracker.Adapter.StateCustomAdapter;
import com.atish.covid19_tracker.model.DistrictModel;
import com.atish.covid19_tracker.model.State_Model;
import com.leo.simplearcloader.SimpleArcLoader;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StateList_Activity extends AppCompatActivity {
    EditText editSearch;
    ListView listView;
    State_Model state_model;
    DistrictModel districtModel;
    SimpleArcLoader simpleArcLoader;
    public static List<State_Model> state_modelList = new ArrayList<>();
    MaterialSearchView searchView;

    StateCustomAdapter stateCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list_);
        Toolbar toolbar=findViewById(R.id.state_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Affected State");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        searchView=findViewById(R.id.State_search_view);
        listView = findViewById(R.id.state_listView);
        simpleArcLoader = findViewById(R.id.state_loader);


        fetchStateData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StateList_Activity.this, MainActivity.class));
                finish();

            }
        });



    }public void fetchStateData() {
        simpleArcLoader.start();

        String url = "https://api.covid19india.org/data.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObjectmain = new JSONObject(response);

                    JSONArray jsonArray = jsonObjectmain.getJSONArray("statewise");
                    for (int i = 1; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String stateName = jsonObject.getString("state");
                        String totalCase = jsonObject.getString("confirmed");
                        String totalDeath = jsonObject.getString("deaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String today_case=jsonObject.getString("deltaconfirmed");
                        String today_death=jsonObject.getString("deltadeaths");
                        String today_recovered=jsonObject.getString("deltarecovered");

//                        JSONArray jsonArrayDistrict = jsonObject.getJSONArray("districtData");
//                        for (int k = 0; k < jsonArrayDistrict.length(); k++) {
//                            JSONObject jsonObject_District = jsonArrayDistrict.getJSONObject(k);
//                            String D_totalCase = jsonObject_District.getString("confirmed");
//                            String districtName = jsonObject_District.getString("name");
//                            String D_totalDeath = jsonObject_District.getString("deaths");
//                            String D_recovered = jsonObject_District.getString("recovered");
//                            districtModel = new DistrictModel(districtName, D_totalCase, D_totalDeath, D_recovered);
//                            districtModelList.add(districtModel);

                        //  }
                        state_model = new State_Model(stateName, totalCase, totalDeath, recovered, active,today_case,today_death,today_recovered);
                        state_modelList.add(state_model);


                    }
                    stateCustomAdapter = new StateCustomAdapter(StateList_Activity.this, state_modelList);
                    listView.setAdapter(stateCustomAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    // Toast.makeText(StateData_Activity.this, state_modelList.get(1).getStateName(), Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.homeIcon){
            startActivity(new Intent(StateList_Activity.this,MainActivity.class));
            finish();
        }
        if (id == R.id.about) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(StateList_Activity.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        MenuItem track_state=menu.findItem(R.id.track_state);
        track_state.setVisible(false);

        searchView.setMenuItem(menuItem);
        searchView.setHint("Search Corona States By States..");

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                stateCustomAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                stateCustomAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;

    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(StateList_Activity.this,MainActivity.class));
        finish();
        super.onBackPressed();
    }



}