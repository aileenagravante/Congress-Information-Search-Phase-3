package com.example.congressapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LegislatorItemAdapter extends ArrayAdapter<Legislator> {

    // *** NOTE: SEE COMMENTS IN BillItemAdapter.java *** //

    public static final String LEGISLATOR_ID_KEY = "legislator_id_key";
    private static final String TAG = "LegislatorItemAdapter";

    public static List<Legislator> legislatorsList;
    LayoutInflater layoutInflater;
    public static Context adapterContext;

    public LegislatorItemAdapter(Context context, List<Legislator> objects) {
        super(context, R.layout.list_item_legislator, objects);

        legislatorsList = objects;
        layoutInflater = LayoutInflater.from(context);
        adapterContext = context;
    }

    @NonNull
    @Override
    // position - position of current item in dataset
    // convertView - reference to layout (may or may not be null)
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_legislator, parent, false);
        }

        Legislator legislator = legislatorsList.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.textView_legislator_name);
        TextView details = (TextView) convertView.findViewById(R.id.textView_legislator_details);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView_legislator_img);

        Picasso.with(adapterContext)
                .load(legislator.getImageURL())
                .resize(180, 220)
                .centerCrop()
                .into(image);

        name.setText(legislator.getName());
        details.setText(legislator.getDetailsForListView());

        convertView.setBackgroundResource(R.drawable.list_item_bg);

        return convertView;
    }

    // Overrides needed to prevent IndexOutOfBoundsException
    //      when using ArrayAdapter with Filter
    @Override
    public int getCount() {
        return legislatorsList.size();
    }

    @Override
    public Legislator getItem(int pos) {
        return legislatorsList.get(pos);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filteredResults = new FilterResults();

                if(constraint == null || constraint.length() == 0) {
                    filteredResults.values = legislatorsList;
                    filteredResults.count = legislatorsList.size();
                }

                else {
                    List<Legislator> filteredList = new ArrayList<>();

                    for(Legislator l : legislatorsList) {
                        if(constraint.toString().equalsIgnoreCase(l.getChamber())) {
                            filteredList.add(l);
                        }
                    }

                    // When we display legislators by state, we don't use a filter and sorting by state is taken
                    //      care of in the LegislatorsFragment, but when we display legislators by chamber,
                    //      we need to sort by last name, therefore we so the sorting here after the filtering.
                    Collections.sort(filteredList, new Comparator<Legislator>() {
                        @Override
                        public int compare(Legislator o1, Legislator o2) {
                            return o1.getLast_name().compareTo(o2.getLast_name());
                        }
                    });

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
                    legislatorsList = (List<Legislator>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}
