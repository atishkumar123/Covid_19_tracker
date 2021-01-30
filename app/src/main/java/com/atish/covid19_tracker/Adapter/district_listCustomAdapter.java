package com.atish.covid19_tracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.atish.covid19_tracker.District_Data_Activity;
import com.atish.covid19_tracker.R;

import com.atish.covid19_tracker.State_Details_Activity;
import com.atish.covid19_tracker.model.DistrictModel;


import java.util.ArrayList;
import java.util.List;

public class district_listCustomAdapter extends ArrayAdapter<DistrictModel> {
    Context context;
    List<DistrictModel> districtModelList;
      List<DistrictModel>districtModelListFiltered;
    public district_listCustomAdapter(Context context, List<DistrictModel> districtModelList) {
        super(context, R.layout.district_list_item,districtModelList);

        this.context=context;
        this.districtModelList=districtModelList;
        this.districtModelListFiltered=districtModelList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.district_list_item,null,true);
        TextView districtName=view.findViewById(R.id.district_list_district_name);
        TextView totalCase=view.findViewById(R.id.district_list_total_case);
        ImageView tap=view.findViewById(R.id.district_tap);
        tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), District_Data_Activity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });



        final DistrictModel model=districtModelListFiltered.get(position);


        districtName.setText(model.getDistrictName());
        totalCase.setText(model.getTotalCase());







        return view;
    }
    @Override
    public int getCount() {
        return districtModelListFiltered.size();
    }

    @Nullable
    @Override
    public DistrictModel getItem(int position) {
        return districtModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

//                if(constraint == null || constraint.length() == 0) {
//                      filterResults.count = state_modelList.size();
//                    filterResults.values = state_modelList;
                String charString=constraint.toString();
                if(charString.isEmpty()){
                    districtModelListFiltered=districtModelList;
                } else {
                    List<DistrictModel> filteredList = new ArrayList<>();
                    String searchStr = charString.toLowerCase();
                    for (DistrictModel itemModel : districtModelList) {
                        if (itemModel.getDistrictName().toLowerCase().contains(searchStr)) {
                            filteredList.add(itemModel);
                        }


                    }
                    districtModelListFiltered=filteredList;
                }
                filterResults.values=districtModelListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                districtModelListFiltered =(List<DistrictModel>) results.values;
                State_Details_Activity.districtModelList = (List<DistrictModel>) results.values;
                notifyDataSetChanged();
            }

        };
        return filter;
    }
}
