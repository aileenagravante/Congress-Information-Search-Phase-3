package com.example.congressapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BillItemAdapterFavorite extends ArrayAdapter<Bill> {

    // *** NOTE: SEE COMMENTS IN BillItemAdapter.java *** //

    public static final String TAG = "BillItemAdapter";
    public static List<Bill> billsListFavorites;
    LayoutInflater layoutInflater;
    public static Context adapterContext;

    public BillItemAdapterFavorite(Context context, List<Bill> objects) {
        super(context, R.layout.list_item_bill, objects);

        billsListFavorites = objects;
        layoutInflater = LayoutInflater.from(context);
        adapterContext = context;
    }

    @NonNull
    @Override
    // position - position of current item in dataset
    // convertView - reference to layout (may or may not be null)
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_bill, parent, false);
        }

        Bill bill = billsListFavorites.get(position);

        TextView ID = (TextView) convertView.findViewById(R.id.textView_bill_id);
        TextView title = (TextView) convertView.findViewById(R.id.textView_bill_title);
        TextView introduced = (TextView) convertView.findViewById(R.id.textView_bill_introduced);

        ID.setText(bill.getBill_id());
        title.setText(bill.getTitle());
        introduced.setText(bill.getIntroduced_on_formatted());

        convertView.setBackgroundResource(R.drawable.list_item_bg);

        return convertView;
    }
}
