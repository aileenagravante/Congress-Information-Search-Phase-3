package com.example.congressapi;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LegislatorsFragment extends Fragment implements TabHost.OnTabChangeListener {

    // *** NOTE: SEE COMMENTS IN BillsFragment.java *** //

    public static final String LEGISLATORS_TAB_1 = "Legislators Tab 1";
    public static final String LEGISLATORS_TAB_2 = "Legislators Tab 2";
    public static final String LEGISLATORS_TAB_3 = "Legislators Tab 3";

    public final static String AWS_LEGISLATORS = "http://cs571.c2vrp7jmpp.us-east-1.elasticbeanstalk.com/index.php?database=legislators&keyword=all";
    public static final String LEGISLATOR_ID_KEY = "legislator_id_key";
    public static final String TAG = "LegislatorsFragment";

    public static final String HOUSE = "HOUSE";
    public static final String SENATE = "SENATE";

    private TabHost tabhost;
    private TextView text;
    private View view;
    private Bundle fragmentBundle;

    public static List<Legislator> legislatorsList;
    public static Map<String, Legislator> legislatorsMap;

    public boolean tab1FirstClick;
    public boolean tab2FirstClick;
    public boolean tab3FirstClick;

    public LegislatorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentBundle = savedInstanceState;

        view = inflater.inflate(R.layout.fragment_legislators, container, false);

        tabhost = (TabHost) view.findViewById(R.id.tabhost_legislators);
        tabhost.setup();

        TabHost.TabSpec spec = tabhost.newTabSpec(LEGISLATORS_TAB_1);
        spec.setContent(R.id.tab1_legislators);
        spec.setIndicator("BY STATES");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec(LEGISLATORS_TAB_2);
        spec.setContent(R.id.tab2_legislators);
        spec.setIndicator(HOUSE);
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec(LEGISLATORS_TAB_3);
        spec.setContent(R.id.tab3_legislators);
        spec.setIndicator(SENATE);
        tabhost.addTab(spec);

        tabhost.setOnTabChangedListener(this);

        // Since we know our list view won't change (we won't be adding or removing legislators), we only want to
        //      create the alphabet index once, so we set some flags here to check if this is the first time we're
        //      creating a particular list view
        tab1FirstClick = true;
        tab2FirstClick = true;
        tab3FirstClick = true;

        APItask task = new APItask();
        task.execute(AWS_LEGISLATORS);

        return view;
    }

    @Override
    public void onTabChanged(String tabId) {

        if(tabId.equals(LEGISLATORS_TAB_1) && legislatorsList != null) {
            LegislatorItemAdapter adapter = new LegislatorItemAdapter(getActivity(), legislatorsList);

            final ListView listView = (ListView) view.findViewById(R.id.listview1_legislators);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(LegislatorItemAdapter.adapterContext, LegislatorDetail.class);
                    intent.putExtra(LEGISLATOR_ID_KEY, LegislatorItemAdapter.legislatorsList.get(position).getBioguide_id());
                    LegislatorItemAdapter.adapterContext.startActivity(intent);
                }
            });

            listView.setAdapter(adapter);
        }

        else if(tabId.equals(LEGISLATORS_TAB_2) && legislatorsList != null) {
            LegislatorItemAdapter adapter = new LegislatorItemAdapter(getActivity(), legislatorsList);

            adapter.getFilter().filter(HOUSE);

            final ListView listView = (ListView) view.findViewById(R.id.listview2_legislators);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(LegislatorItemAdapter.adapterContext, LegislatorDetail.class);
                    intent.putExtra(LEGISLATOR_ID_KEY, LegislatorItemAdapter.legislatorsList.get(position).getBioguide_id());
                    LegislatorItemAdapter.adapterContext.startActivity(intent);
                }
            });

            listView.setAdapter(adapter);

            if(tab2FirstClick) {

                tab2FirstClick = false;

                // Create the alphabetical index
                final Map<String, Integer> mapIndex = createIndexList(HOUSE);

                // Get a handle to the side index layout (the vertical gray bar)
                LinearLayout sideIndexLayout = (LinearLayout) view.findViewById(R.id.side_index_legislators_house);

                // Declare a TextView where each of our indices will live
                TextView indexValue;

                // Create a new array list of the keys (indices) from the mapIndex
                List<String> indexList = new ArrayList<>(mapIndex.keySet());

                // For each of the indices in the indexList, inflate the layout for our individual indices, then set the
                //      text to that index, then set an onClick listener which when clicked will set the listView to the
                //      position (value) paired with that index.
                // Finally, add the view to the side index layout.
                for(final String index: indexList) {
                    indexValue = (TextView) getLayoutInflater(fragmentBundle).inflate(R.layout.side_index_item, null);
                    indexValue.setText(index);
                    indexValue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listView.setSelection(mapIndex.get(index));
                        }
                    });
                    sideIndexLayout.addView(indexValue);
                }
            }

        }

        else if(tabId.equals(LEGISLATORS_TAB_3) && legislatorsList != null) {
            LegislatorItemAdapter adapter = new LegislatorItemAdapter(getActivity(), legislatorsList);

            adapter.getFilter().filter(SENATE);

            final ListView listView = (ListView) view.findViewById(R.id.listview3_legislators);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(LegislatorItemAdapter.adapterContext, LegislatorDetail.class);
                    intent.putExtra(LEGISLATOR_ID_KEY, LegislatorItemAdapter.legislatorsList.get(position).getBioguide_id());
                    LegislatorItemAdapter.adapterContext.startActivity(intent);
                }
            });

            listView.setAdapter(adapter);

            if(tab3FirstClick) {
                tab3FirstClick = false;

                final Map<String, Integer> mapIndex = createIndexList(SENATE);

                LinearLayout sideIndexLayout = (LinearLayout) view.findViewById(R.id.side_index_legislators_senate);
                TextView indexValue;
                List<String> indexList = new ArrayList<>(mapIndex.keySet());
                for(final String index: indexList) {
                    indexValue = (TextView) getLayoutInflater(fragmentBundle).inflate(R.layout.side_index_item, null);
                    indexValue.setText(index);
                    indexValue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listView.setSelection(mapIndex.get(index));
                        }
                    });
                    sideIndexLayout.addView(indexValue);
                }
            }
        }
    }

    public void createLegislatorsDataStructures(String APIresult) {
        legislatorsList = new ArrayList<>();
        legislatorsMap = new HashMap<>();

        try {
            JSONObject object = new JSONObject(APIresult);
            JSONArray array = object.getJSONArray("results");

            for(int i = 0; i < array.length(); i++) {
                JSONObject currLegislator = (JSONObject) array.get(i);

                Legislator newLegislator = new Legislator(
                        checkAttribute(currLegislator, "bioguide_id"),
                        checkAttribute(currLegislator, "title"),
                        checkAttribute(currLegislator, "last_name"),
                        checkAttribute(currLegislator, "first_name"),
                        checkAttribute(currLegislator, "party"),
                        checkAttribute(currLegislator, "state"),
                        checkAttribute(currLegislator, "state_name"),
                        checkAttribute(currLegislator, "district"),
                        checkAttribute(currLegislator, "oc_email"),
                        checkAttribute(currLegislator, "chamber"),
                        checkAttribute(currLegislator, "phone"),
                        checkAttribute(currLegislator, "term_start"),
                        checkAttribute(currLegislator, "term_end"),
                        checkAttribute(currLegislator, "office"),
                        checkAttribute(currLegislator, "fax"),
                        checkAttribute(currLegislator, "birthday"),
                        checkAttribute(currLegislator, "facebook_id"),
                        checkAttribute(currLegislator, "twitter_id"),
                        checkAttribute(currLegislator, "website")
                );

                legislatorsList.add(newLegislator);
                legislatorsMap.put(newLegislator.getBioguide_id(), newLegislator);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Note: The district attribute is returned as an int, unlike all other attributes which are
    //      returned as Strings. Therefore, we need to include special conditions to handle it.
    // Also, when a legislator does not have a district, we return "0";
    public String checkAttribute(JSONObject object, String attribute) {
        String value = "";
        if(object.has(attribute)) {
            if(!object.isNull(attribute)) {
                try {
                    if(attribute.equals("district")) {
                        value = "" + object.getInt(attribute);
                    }
                    else {
                        value = (String) object.get(attribute);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                if(attribute.equals("district")) {
                    value = "0";
                }
                else {
                    value = "N.A.";
                }
            }
        }
        else {
            if(attribute.equals("district")) {
                value = "0";
            }
            else {
                value = "N.A.";
            }
        }
        return value;
    }

    // This method is to create the alphabetical index, which we store in a hash map, where
    //      the key is a letter of the alphabet, and the value is the location in the array list
    //      where this key first exists.
    // For example, if there are 3 legislators whose last name starts with A followed by a legislator
    //      whose last name starts with B, then the hash map would contain [{A, 0}, {B, 3}]
    // Note: The list must be sorted beforehand in order for this indexing to work properly
    //      In other words, the list must be sorted so that it's location in the array list
    //      corresponds to it's position in the list view
    private Map<String, Integer> createIndexList(String indexBy) {
        Map<String, Integer> mapIndex = new LinkedHashMap<>();

        // We're either going to index by state name or by legislator last name
        if(indexBy.equalsIgnoreCase("state")) {
            for(int i = 0; i < legislatorsList.size(); i++) {
                // For each of the legislators, get the first letter of their state name
                String index = legislatorsList.get(i).getState_name().substring(0,1);

                // If we have not yet saved a position (i) for this letter, add one to the hash map
                if(mapIndex.get(index) == null) {
                    mapIndex.put(index, i);
                }
            }
        }

        else {
            List<Legislator> filteredList = new ArrayList<>();

            // Go through legislators list and add to a new array list the legislators whose
            //      chamber match the indexBy attribute
            for(Legislator l : legislatorsList) {
                if(l.getChamber().equalsIgnoreCase(indexBy)) {
                    filteredList.add(l);
                }
            }

            // Sort the filteredList by last name
            Collections.sort(filteredList, new Comparator<Legislator>() {
                @Override
                public int compare(Legislator o1, Legislator o2) {
                    return o1.getLast_name().compareTo(o2.getLast_name());
                }
            });

            // Use the same logic previously explained to build the alphabetical side index
            for(int i = 0; i < filteredList.size(); i++) {
                String index = filteredList.get(i).getLast_name().substring(0,1);
                if(mapIndex.get(index) == null) {
                    mapIndex.put(index, i);
                }
            }

        }

        return mapIndex;
    }

    // Comparator to compare legislators last names - to be used when displaying legislators by state
    //      and we need to sort by state then by last name
    public class LegislatorLastNameComparator implements Comparator<Legislator> {

        @Override
        public int compare(Legislator o1, Legislator o2) {
            return o1.getLast_name().compareTo(o2.getLast_name());
        }
    }

    // Comparator to compare legislators state names - to be used when displaying legislators by state
    //      and we need to sort by state then by last name
    public class LegislatorStateNameComparator implements Comparator<Legislator> {

        @Override
        public int compare(Legislator o1, Legislator o2) {
            return o1.getState_name().compareTo(o2.getState_name());
        }
    }

    // Chains the two comparators together
    public class LegislatorChainedComparator implements Comparator<Legislator> {

        private List<Comparator<Legislator>> listComparators;

        public LegislatorChainedComparator(Comparator<Legislator>... comparators) {
            this.listComparators = Arrays.asList(comparators);
        }

        @Override
        public int compare(Legislator o1, Legislator o2) {
            for(Comparator<Legislator> comparator : listComparators) {
                int result = comparator.compare(o1, o2);
                if(result != 0) {
                    return result;
                }
            }
            return 0;
        }
    }

    private class APItask extends AsyncTask<String, String, String> {
        @Override
        // Does not have access to main thread
        // This is where we make the call to get the data in its raw form
        //      and it returns the results to onPostExecute as a string
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        // Receives of a result whose type is determined by the third argument
        // doInBackground returns value to onPostExecute
        // Has access to the main thread
        // This is where we will receive the data as a string and parse it
        protected void onPostExecute(String APIresult) {
            try {
                createLegislatorsDataStructures(APIresult);

//                Collections.sort(legislatorsList, new Comparator<Legislator>() {
//                    @Override
//                    public int compare(Legislator o1, Legislator o2) {
//                        return o1.getState_name().compareTo(o2.getState_name());
//                    }
//                });

                // Use the chained comparator so that we can sort by state name and then last name
                Collections.sort(legislatorsList, new LegislatorChainedComparator(
                        new LegislatorStateNameComparator(),
                        new LegislatorLastNameComparator()
                ));

                LegislatorItemAdapter adapter = new LegislatorItemAdapter(getActivity(), legislatorsList);

                final ListView listView = (ListView) view.findViewById(R.id.listview1_legislators);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(LegislatorItemAdapter.adapterContext, LegislatorDetail.class);
                        intent.putExtra(LEGISLATOR_ID_KEY, LegislatorItemAdapter.legislatorsList.get(position).getBioguide_id());
                        LegislatorItemAdapter.adapterContext.startActivity(intent);
                    }
                });

                listView.setAdapter(adapter);

                if(tab1FirstClick) {
                    tab1FirstClick = false;

                    final Map<String, Integer> mapIndex = createIndexList("state");

                    LinearLayout sideIndexLayout = (LinearLayout) view.findViewById(R.id.side_index_legislators_state);
                    TextView indexValue;
                    List<String> indexList = new ArrayList<>(mapIndex.keySet());
                    for(final String index: indexList) {
                        indexValue = (TextView) getLayoutInflater(fragmentBundle).inflate(R.layout.side_index_item, null);
                        indexValue.setText(index);
                        indexValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listView.setSelection(mapIndex.get(index));
                            }
                        });
                        sideIndexLayout.addView(indexValue);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
