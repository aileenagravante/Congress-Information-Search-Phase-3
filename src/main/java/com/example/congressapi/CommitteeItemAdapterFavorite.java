package com.example.congressapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CommitteeItemAdapterFavorite extends ArrayAdapter<Committee>{

    // *** NOTE: SEE COMMENTS IN BillItemAdapter.java *** //

    public static List<Committee> committeesListFavorites;
    LayoutInflater layoutInflater;
    public static Context adapterContext;

    public CommitteeItemAdapterFavorite(Context context, List<Committee> objects) {
        super(context, R.layout.list_item_committee, objects);

        committeesListFavorites = objects;
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

        final Committee committee = committeesListFavorites.get(position);

        TextView ID = (TextView) convertView.findViewById(R.id.textView_committee_id);
        TextView name = (TextView) convertView.findViewById(R.id.textView_committee_name);
        TextView chamber = (TextView) convertView.findViewById(R.id.textView_committee_chamber);

        ID.setText(committee.getCommittee_id());
        name.setText(committee.getName());
        chamber.setText(committee.getChamber());

        convertView.setBackgroundResource(R.drawable.list_item_bg);

        return convertView;
    }

}
