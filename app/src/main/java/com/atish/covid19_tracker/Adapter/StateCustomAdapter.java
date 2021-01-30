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

import com.atish.covid19_tracker.R;
import com.atish.covid19_tracker.StateList_Activity;
import com.atish.covid19_tracker.State_Details_Activity;
import com.atish.covid19_tracker.model.State_Model;

import java.util.ArrayList;
import java.util.List;

public class StateCustomAdapter extends ArrayAdapter<State_Model> {
    private Context context;
     private List<State_Model> state_modelList;
     private  List<State_Model>state_modelListFiltered;


    public StateCustomAdapter( Context context,List<State_Model> state_modelList) {
        super(context, R.layout.state_list_item, state_modelList);

        this.context=context;
        this.state_modelList=state_modelList;
        this.state_modelListFiltered=state_modelList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_list_item,null,true);

        TextView stateName=view.findViewById(R.id.state_list_state_name);
        TextView totalCase=view.findViewById(R.id.state_list_total_case);
        ImageView tap=view.findViewById(R.id.tap);



        final State_Model model=state_modelListFiltered.get(position);


        stateName.setText(model.getStateName());
        totalCase.setText(model.getTotalCase());
        tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), State_Details_Activity.class);
                intent.putExtra("state_name",state_modelList.get(position).getStateName());
                intent.putExtra("state_position",position);
               context.startActivity(intent);
                ((Activity)context).finish();

            }
        });



        return view;
    }

    @Override
    public int getCount() {
        return state_modelListFiltered.size();
    }

    @Nullable
    @Override
    public State_Model getItem(int position) {
        return state_modelListFiltered.get(position);
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
                    state_modelListFiltered=state_modelList;
                } else {
                    List<State_Model> filteredList = new ArrayList<>();
                   String searchStr = charString.toLowerCase();
                    for (State_Model itemModel : state_modelList) {
                        if (itemModel.getStateName().toLowerCase().contains(searchStr)) {
                            filteredList.add(itemModel);
                        }


                    }
                    state_modelListFiltered=filteredList;
                }
                filterResults.values=state_modelListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               state_modelListFiltered =(List<State_Model>) results.values;
               StateList_Activity.state_modelList = (List<State_Model>) results.values;
                notifyDataSetChanged();
            }

        };
        return filter;
    }

}
