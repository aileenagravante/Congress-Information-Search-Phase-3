package com.example.congressapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LegislatorItemAdapterFavorite extends ArrayAdapter<Legislator>{

    // *** NOTE: SEE COMMENTS IN BillItemAdapterFavorite.java *** //

    public static final String LEGISLATOR_ID_KEY = "legislator_id_key";
    private static final String TAG = "LegislatorItemAdapter";

    public static List<Legislator> legislatorsListFavorites;
    LayoutInflater layoutInflater;
    public static Context adapterContext;

    public LegislatorItemAdapterFavorite(Context context, List<Legislator> objects) {
        super(context, R.layout.list_item_legislator, objects);

        legislatorsListFavorites = objects;
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

        Legislator legislator = legislatorsListFavorites.get(position);

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

}
