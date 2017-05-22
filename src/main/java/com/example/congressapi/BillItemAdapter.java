package com.example.congressapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BillItemAdapter extends ArrayAdapter<Bill> {

    public static final String TAG = "BillItemAdapter";
    public static List<Bill> billsList;
    LayoutInflater layoutInflater;
    public static Context adapterContext;

    public BillItemAdapter(Context context, List<Bill> objects) {
        super(context, R.layout.list_item_bill, objects);

        billsList = objects;
        layoutInflater = LayoutInflater.from(context);
        adapterContext = context;
    }

    @NonNull
    @Override
    // position - position of current item in dataset
    // convertView - reference to layout (may or may not be null)
    //
    // getView() is run for every item in the list view
    public View getView(int position, View convertView, ViewGroup parent) {
        // Since convertView may or may not be null because it can be new or resused from a previous item,
        //      we first check here if it is null.
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_bill, parent, false);
        }

        // Based on the position of this view, get the associated bill from the billsList created in the
        //      BillsFragment
        Bill bill = billsList.get(position);

        // Get handles to the TextViews in the list item layout
        TextView ID = (TextView) convertView.findViewById(R.id.textView_bill_id);
        TextView title = (TextView) convertView.findViewById(R.id.textView_bill_title);
        TextView introduced = (TextView) convertView.findViewById(R.id.textView_bill_introduced);

        // Set the text in the list item layout
        ID.setText(bill.getBill_id());
        title.setText(bill.getTitle());
        introduced.setText(bill.getIntroduced_on_formatted());

        // Set the background to our gradient before returning the view.
        convertView.setBackgroundResource(R.drawable.list_item_bg);

        return convertView;
    }

    // Overrides needed to prevent IndexOutOfBoundsException
    //      when using ArrayAdapter with Filter
    @Override
    public int getCount() {
        return billsList.size();
    }

    @Override
    public Bill getItem(int pos) {
        return billsList.get(pos);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filteredResults = new FilterResults();

                // When we don't define a filter for our adapter, just return the views as-is
                if(constraint == null || constraint.length() == 0) {
                    filteredResults.values = billsList;
                    filteredResults.count = billsList.size();
                }

                // Otherwise, filter the views based on the constraint
                //      For bills, we are always filtering based on its "active" attribute
                else {
                    List<Bill> filteredList = new ArrayList<>();
                    for(Bill b : billsList) {
                        if(constraint.toString().equalsIgnoreCase(b.getActive())) {
                            filteredList.add(b);
                        }
                    }

                    // Set the filtered result values and size
                    filteredResults.values = filteredList;
                    filteredResults.count = filteredList.size();
                }

                return filteredResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // To know how to publish the list view, we need to check to see if we have any results from the filter
                //      or not (count is 0 when filter was not used, so filterResults has no items)
                if(results.count == 0) {
                    notifyDataSetInvalidated();
                }
                else {
                    billsList = (List<Bill>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}
