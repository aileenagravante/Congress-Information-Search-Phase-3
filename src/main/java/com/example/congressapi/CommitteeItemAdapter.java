package com.example.congressapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommitteeItemAdapter extends ArrayAdapter<Committee> {

    // *** NOTE: SEE COMMENTS IN BillItemAdapter.java *** //

    public static final String COMMITTEE_ID_KEY = "committee_id_key";
    private static final String TAG = "CommitteeItemAdapter";

    public static List<Committee> committeesList;
    LayoutInflater layoutInflater;
    public static Context adapterContext;

    public CommitteeItemAdapter(Context context, List<Committee> objects) {
        super(context, R.layout.list_item_committee, objects);

        committeesList = objects;
        layoutInflater = LayoutInflater.from(context);
        adapterContext = context;
    }

    @NonNull
    @Override
    // position - position of current item in dataset
    // convertView - reference to layout (may or may not be null)
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_committee, parent, false);
        }

        final Committee committee = committeesList.get(position);

        TextView ID = (TextView) convertView.findViewById(R.id.textView_committee_id);
        TextView name = (TextView) convertView.findViewById(R.id.textView_committee_name);
        TextView chamber = (TextView) convertView.findViewById(R.id.textView_committee_chamber);

        ID.setText(committee.getCommittee_id());
        name.setText(committee.getName());
        chamber.setText(committee.getChamber());

        // The below code for setting onclick listeners also works, except the rows
        //      in the listview do not animate (highlight) like default onclick
        //      listview item behavior
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(adapterContext, CommitteeDetail.class);
//                intent.putExtra(COMMITTEE_ID_KEY, committee.getCommittee_id());
//                adapterContext.startActivity(intent);
//            }
//        });

        convertView.setBackgroundResource(R.drawable.list_item_bg);

        return convertView;
    }

    // Overrides needed to prevent IndexOutOfBoundsException
    //      when using ArrayAdapter with Filter
    @Override
    public int getCount() {
        return committeesList.size();
    }

    @Override
    public Committee getItem(int pos) {
        return committeesList.get(pos);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filteredResults = new FilterResults();

                if(constraint == null || constraint.length() == 0) {
                    filteredResults.values = committeesList;
                    filteredResults.count = committeesList.size();
                }

                else {
                    List<Committee> filteredList = new ArrayList<>();

                    for(Committee c : committeesList) {
                        if(constraint.toString().equalsIgnoreCase(c.getChamber())) {
                            filteredList.add(c);
                        }
                    }

                    filteredResults.values = filteredList;
                    filteredResults.count = filteredList.size();
                }

                return filteredResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.count == 0) {
                    notifyDataSetInvalidated();
                }
                else {
                    committeesList = (List<Committee>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}
