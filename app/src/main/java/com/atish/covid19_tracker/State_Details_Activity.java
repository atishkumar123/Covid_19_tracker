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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atish.covid19_tracker.Adapter.district_listCustomAdapter;
import com.atish.covid19_tracker.model.DistrictModel;
import com.atish.covid19_tracker.model.State_Model;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.atish.covid19_tracker.StateList_Activity.state_modelList;

public class State_Details_Activity extends AppCompatActivity {
    public static List<DistrictModel> districtModelList = new ArrayList<>();
    String stateName;
    int state_position;

    DistrictModel districtModel;
    ListView District_listView;
    district_listCustomAdapter district_listCustomAdapter;
    State_Model model;
    LinearLayout state_data_layout;
    MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state__details_);
//      get Intent
        Intent intent = getIntent();
        stateName = intent.getStringExtra("state_name");
        state_position = intent.getIntExtra("state_position", 0);

//        set custom toolbar
        Toolbar toolbar = findViewById(R.id.state_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data of " + stateName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        searchView=findViewById(R.id.state_detail_search_view);
        state_data_layout=findViewById(R.id.state_data_layout);

        District_listView = findViewById(R.id.district_name_listView);
        TextView state_total_cases = findViewById(R.id.state_total_case);
        TextView state_total_deaths = findViewById(R.id.state_total_death);
        TextView state_recovered = findViewById(R.id.state_total_recovered);
        TextView state_active = findViewById(R.id.state_active);
        TextView state_daily_case = findViewById(R.id.state_daily_case);
        TextView state_daily_death = findViewById(R.id.state_daily_death);
        TextView state_daily_recovered = findViewById(R.id.state_daily_recovered);

        //create a model
        model = state_modelList.get(state_position);
//      set the values
        state_total_cases.setText(model.getTotalCase());
        state_total_deaths.setText(model.getTotalDeath());
        state_recovered.setText(model.getRecovered());
        state_active.setText(model.getActive());
        state_daily_case.setText("[+" + model.getToday_Case() + " ]");
        state_daily_death.setText("[+" + model.getToday_death() + " ]");
        state_daily_recovered.setText("[+" + model.getToday_recovered() + " ]");

        fetchDistrictDta();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(State_Details_Activity.this, StateList_Activity.class));
                finish();

            }
        });


    }

    private void fetchDistrictDta() {
        String url = "https://api.covid19india.org/state_district_wise.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject_state = jsonObject.getJSONObject(model.getStateName());
                    JSONObject distObject = jsonObject_state.getJSONObject("districtData");


                    JSONArray key = distObject.names();
                    for (int i = 0; i < key.length(); i++) {
                        String distNmae = key.getString(i);
                        JSONObject particular_dist = distObject.getJSONObject(distNmae);
                        String confirm_case = particular_dist.getString("confirmed");
                        String active = particular_dist.getString("active");
                        String recovered = particular_dist.getString("recovered");
                        String death = particular_dist.getString("deceased");
                        JSONObject delta=particular_dist.getJSONObject("delta");
                        String today_case=delta.getString("confirmed");
                        String today_death=delta.getString("deceased");
                        String today_recovered=delta.getString("recovered");
                        districtModel = new DistrictModel(distNmae, confirm_case, death, recovered, active,today_case,today_death,today_recovered);
                        districtModelList.add(districtModel);
                    }
                    district_listCustomAdapter = new district_listCustomAdapter(State_Details_Activity.this, districtModelList);
                    District_listView.setAdapter(district_listCustomAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        MenuItem track_state=menu.findItem(R.id.track_state);
        track_state.setVisible(false);
        searchView.setMenuItem(item);
        searchView.setHint("Search By District...");
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                district_listCustomAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                district_listCustomAdapter.getFilter().filter(newText);
                return false;
            }


        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                state_data_layout.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                state_data_layout.setVisibility(View.VISIBLE);
            }
        });

        return true;
    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            startActivity(new Intent(State_Details_Activity.this, StateList_Activity.class));
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();



        if(id==R.id.homeIcon){
            startActivity(new Intent(State_Details_Activity.this,MainActivity.class));
            finish();
        }
        if (id == R.id.about) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(State_Details_Activity.this);
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

}