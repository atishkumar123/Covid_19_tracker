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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atish.covid19_tracker.Adapter.CountryCustomAdapter;
import com.atish.covid19_tracker.model.CountryModel;
import com.leo.simplearcloader.SimpleArcLoader;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class countries_list_Activity extends AppCompatActivity {
    EditText editSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;
    public static List<CountryModel> countryModelList = new ArrayList<>();
    CountryModel countryModel;
    CountryCustomAdapter countryCustomAdapter;
    private MaterialSearchView materialSearchView;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_list);

        listView = findViewById(R.id.listView);
        simpleArcLoader = findViewById(R.id.loader);
        Toolbar toolbar = findViewById(R.id.country_data_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Affected country");

        searchView = findViewById(R.id.search_view);
//        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(countries_list_Activity.this, MainActivity.class));
                finish();

            }
        });

        fetchData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(countries_list_Activity.this, countryDetails_Activity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            }
        });



    }






    private void fetchData() {
        String url = "https://disease.sh/v3/covid-19/countries";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String countryName = jsonObject.getString("country");
                        String totalCases = jsonObject.getString("cases");
                        String totalDeath = jsonObject.getString("deaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");
                        String todayDeath = jsonObject.getString("todayDeaths");
                        String todayCases = jsonObject.getString("todayCases");
                        String todayRecovered = jsonObject.getString("todayRecovered");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("countryInfo");
                        String flag = jsonObject1.getString("flag");
                        countryModel = new CountryModel(countryName, totalCases, totalDeath, recovered, active, critical, todayCases, todayDeath, todayRecovered, flag);
                        countryModelList.add(countryModel);
                    }

                    countryCustomAdapter = new CountryCustomAdapter(countries_list_Activity.this, countryModelList);
                    listView.setAdapter(countryCustomAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);


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

                Toast.makeText(countries_list_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        searchView.setMenuItem(menuItem);
        searchView.setHint("Search Corona States By States..");

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countryCustomAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryCustomAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();


        if(id==R.id.homeIcon){
            startActivity(new Intent(countries_list_Activity.this,MainActivity.class));
            finish();
        }
        if(id==R.id.track_state){
            startActivity(new Intent(countries_list_Activity.this,StateList_Activity.class));
            finish();
        }
        if (id == R.id.about) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(countries_list_Activity.this);
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
        startActivity(new Intent(countries_list_Activity.this,MainActivity.class));
        finish();
        super.onBackPressed();
    }
}