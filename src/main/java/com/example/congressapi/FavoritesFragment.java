package com.example.congressapi;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    public static final String TAG = "FavoritesFragment";

    public static final String FAVORITES_TAB_1 = "Favorites Tab 1";
    public static final String FAVORITES_TAB_2 = "Favorites Tab 2";
    public static final String FAVORITES_TAB_3 = "Favorites Tab 3";

    public static final String LEGISLATOR_ID_KEY = "legislator_id_key";
    public static final String BILL_ID_KEY = "bill_id_key";
    public static final String COMMITTEE_ID_KEY = "committee_id_key";

    private TabHost tabhost;
    private View view;
    Bundle fragmentBundle;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentBundle = savedInstanceState;

        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        tabhost = (TabHost) view.findViewById(R.id.tabhost_favorites);
        tabhost.setup();

        TabHost.TabSpec spec = tabhost.newTabSpec(FAVORITES_TAB_1);
        spec.setContent(R.id.tab1_favorites);
        spec.setIndicator("LEGISLATORS");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec(FAVORITES_TAB_2);
        spec.setContent(R.id.tab2_favorites);
        spec.setIndicator("BILLS");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec(FAVORITES_TAB_3);
        spec.setContent(R.id.tab3_favorites);
        spec.setIndicator("COMMITTEES");
        tabhost.addTab(spec);

        return view;
    }

    @Override
    public void onStart() {
        // Our fragment is where our data resides, therefore, before possibly ("possibly" because there may not even
        //      be any favorites) populating the list view with favorites we have to first check if the array lists
        //      have data (by making sure they're not null)
        //
        // We do our processing in the onStart() method because it is called when the fragment first starts and is again called
        //      when coming back to the fragment, since favorites may have changed between leaving the fragment and coming back
        //      onStart() is a good place to run our processes
        if(LegislatorsFragment.legislatorsList != null) {

            // Each of our object (legistlatos, bills, and committees) have a favorite field, which is initialized as false,
            //      when the favorite button is clicked/unclicked, the object is favorited/unfavorited using this field.
            // Here, we create a new array list, and add to that array list legislators that are favorited.
            List<Legislator> favoriteLegislators = new ArrayList<>();
            for(Legislator l : LegislatorsFragment.legislatorsList) {
                if(l.isFavorite().equalsIgnoreCase("true")) {
                    favoriteLegislators.add(l);
                }
            }

            // Sort the resulting array list by last name
            Collections.sort(favoriteLegislators, new Comparator<Legislator>() {
                @Override
                public int compare(Legislator o1, Legislator o2) {
                    return o1.getLast_name().compareTo(o2.getLast_name());
                }
            });

            // The remaining is the same as what happens in the other corresponding adapters, see those for comments.
            LegislatorItemAdapterFavorite favoriteLegislatorsAdapter = new LegislatorItemAdapterFavorite(getActivity(), favoriteLegislators);

            final ListView favoriteLegislatorsListView = (ListView) view.findViewById(R.id.listview1_favorites);

            favoriteLegislatorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(LegislatorItemAdapterFavorite.adapterContext, LegislatorDetail.class);
                    intent.putExtra(LEGISLATOR_ID_KEY, LegislatorItemAdapterFavorite.legislatorsListFavorites.get(position).getBioguide_id());
                    LegislatorItemAdapterFavorite.adapterContext.startActivity(intent);
                }
            });

            favoriteLegislatorsListView.setAdapter(favoriteLegislatorsAdapter);

            final Map<String, Integer> mapIndex = createIndexList(favoriteLegislators);

            LinearLayout sideIndexLayout = (LinearLayout) view.findViewById(R.id.side_index_favorites_legislators);
            sideIndexLayout.removeAllViewsInLayout();
            TextView indexValue;
            List<String> indexList = new ArrayList<>(mapIndex.keySet());
            for(final String index: indexList) {
                indexValue = (TextView) getLayoutInflater(fragmentBundle).inflate(R.layout.side_index_item, null);
                indexValue.setText(index);
                indexValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favoriteLegislatorsListView.setSelection(mapIndex.get(index));
                    }
                });
                sideIndexLayout.addView(indexValue);
            }
        }

        if(BillsFragment.billsList != null) {

            List<Bill> favoriteBills = new ArrayList<>();
            for(Bill b : BillsFragment.billsList) {
                if(b.isFavorite().equalsIgnoreCase("true")) {
                    favoriteBills.add(b);
                }
            }

            // Unlike in the BillsFragment where we don't have to sort because the data comes in two JSON responses
            //      (one for Active bills and one for New bills) that are already sorted, we have to sort in the
            //      FavoritesFragment because favorited Active bills and favorited New bills are consolidated into one list.
            Collections.sort(favoriteBills, new Comparator<Bill>() {
                @Override
                public int compare(Bill o1, Bill o2) {
                    return o2.parseDate() - o1.parseDate();
                }
            });

            BillItemAdapterFavorite favoriteBillsAdapter = new BillItemAdapterFavorite(getActivity(), favoriteBills);
            ListView favoriteBillsListView = (ListView) view.findViewById(R.id.listview2_favorites);

            favoriteBillsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(BillItemAdapterFavorite.adapterContext, BillDetail.class);
                    intent.putExtra(BILL_ID_KEY, BillItemAdapterFavorite.billsListFavorites.get(position).getBill_id());
                    BillItemAdapterFavorite.adapterContext.startActivity(intent);
                }
            });

            favoriteBillsListView.setAdapter(favoriteBillsAdapter);
        }

        if(CommitteesFragment.committeesList != null) {

            List<Committee> favoriteCommittees = new ArrayList<>();
            for(Committee c : CommitteesFragment.committeesList) {
                if(c.isFavorite().equalsIgnoreCase("true")) {
                    favoriteCommittees.add(c);
                }
            }

            Collections.sort(favoriteCommittees, new Comparator<Committee>() {
                @Override
                public int compare(Committee o1, Committee o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            CommitteeItemAdapterFavorite favoriteCommitteesAdapter = new CommitteeItemAdapterFavorite(getActivity(), favoriteCommittees);
            ListView favoriteCommitteesListView = (ListView) view.findViewById(R.id.listview3_favorites);

            favoriteCommitteesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CommitteeItemAdapterFavorite.adapterContext, CommitteeDetail.class);
                    intent.putExtra(COMMITTEE_ID_KEY, CommitteeItemAdapterFavorite.committeesListFavorites.get(position).getCommittee_id());
                    CommitteeItemAdapterFavorite.adapterContext.startActivity(intent);
                }

            });

            favoriteCommitteesListView.setAdapter(favoriteCommitteesAdapter);
        }

        super.onStart();
    }

    // See LegislatorsFragment.java for an explanation of this indexing method.
    private Map<String, Integer> createIndexList(List<Legislator> legislatorsList) {
        Map<String, Integer> mapIndex = new LinkedHashMap<>();

        for (int i = 0; i < legislatorsList.size(); i++) {
            String index = legislatorsList.get(i).getLast_name().substring(0, 1);
            if (mapIndex.get(index) == null) {
                mapIndex.put(index, i);
            }
        }

        return mapIndex;
    }
}
