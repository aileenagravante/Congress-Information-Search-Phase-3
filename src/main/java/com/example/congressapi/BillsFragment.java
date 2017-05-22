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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillsFragment extends Fragment implements TabHost.OnTabChangeListener {

    public final static String TAG ="BillsFragment";
    public static final String BILLS_TAB_1 = "Bills Tab 1";
    public static final String BILLS_TAB_2 = "Bills Tab 2";

    public final static String AWS_BILLS_ACTIVE = "http://cs571.c2vrp7jmpp.us-east-1.elasticbeanstalk.com/index.php?database=bills&keyword=active";
    public final static String AWS_BILLS_NEW = "http://cs571.c2vrp7jmpp.us-east-1.elasticbeanstalk.com/index.php?database=bills&keyword=new";
    public static final String BILL_ID_KEY = "bill_id_key";

    public static final String ACTIVE = "Active";
    public static final String NEW = "New";

    private TabHost tabhost;
    private TextView text;
    private View view;
    private boolean firstAPItask = false;

    public static List<Bill> billsList;
    public static Map<String, Bill> billsMap;

    public BillsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate our fragment layout so we can use it
        view = inflater.inflate(R.layout.fragment_bills, container, false);

        // Setup our TabHost
        tabhost = (TabHost) view.findViewById(R.id.tabhost_bills);
        tabhost.setup();

        // Set up this tab's unique identifier
        TabHost.TabSpec spec = tabhost.newTabSpec(BILLS_TAB_1);

        // Identify this tabs content
        spec.setContent(R.id.tab1_bills);

        // Name of the tab
        spec.setIndicator("ACTIVE BILLS");
        tabhost.addTab(spec);

        // Repeat for the other tabs
        spec = tabhost.newTabSpec(BILLS_TAB_2);
        spec.setContent(R.id.tab2_bills);
        spec.setIndicator("NEW BILLS");
        tabhost.addTab(spec);

        // Attach a listener to tell us when tab has changed so we know what list view
        //      to display
        tabhost.setOnTabChangedListener(this);

        // Task to get active bills
        APItask firstTask = new APItask();
        firstTask.execute(AWS_BILLS_ACTIVE);

        // Task to get new bills
        APItask secondTask = new APItask();
        secondTask.execute(AWS_BILLS_NEW);

        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        // Note: In retrospect, this process could have been moved into its own method, since it's repeated
        //      only using different filters.
        if(tabId.equals(BILLS_TAB_1) && billsList != null) {
            // Initialize a new BillItemAdapter
            BillItemAdapter adapterBillsActive = new BillItemAdapter(getActivity(), billsList);

            // Get a handle to the list view where we want to populate this data
            ListView listViewBillsActive = (ListView) view.findViewById(R.id.listview1_bills);

            // Filter the data, in this case we want "active" bills
            adapterBillsActive.getFilter().filter(ACTIVE);

            // Give each list item an onClick listener which will trigger the detail activity when clicked
            listViewBillsActive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(BillItemAdapter.adapterContext, BillDetail.class);
                    intent.putExtra(BILL_ID_KEY, BillItemAdapter.billsList.get(position).getBill_id());
                    BillItemAdapter.adapterContext.startActivity(intent);
                }
            });

            // Build the list view using the adapter
            listViewBillsActive.setAdapter(adapterBillsActive);
        }

        // Look at previous comments - process is the same for both tabs
        else if(tabId.equals(BILLS_TAB_2) && billsList != null && firstAPItask) {
            BillItemAdapter adapterBillsNew = new BillItemAdapter(getActivity(), billsList);

            ListView listViewBillsNew = (ListView) view.findViewById(R.id.listview2_bills);

            adapterBillsNew.getFilter().filter(NEW);

            listViewBillsNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(BillItemAdapter.adapterContext, BillDetail.class);
                    intent.putExtra(BILL_ID_KEY, BillItemAdapter.billsList.get(position).getBill_id());
                    BillItemAdapter.adapterContext.startActivity(intent);
                }
            });

            listViewBillsNew.setAdapter(adapterBillsNew);
        }
    }

    // We need to check if we're creating our bills data structure for the first time or not
    //      because we make two calls to the API. One to get Active Bills and a second
    //      to get New Bills, then we consolidate both results into one billsList and one billsMap
    // Also note, that unlike Legislators and Committees, we do not have to do any sorting to the
    //      Collection because the Bills are returned to us in descending date order because our
    //      API call request for them in that order (unlike the API call for Legislators and
    //      Committees that does not define any sort order)
    public void createBillsDataStructures(String APIresult) {
        if(!firstAPItask) {
            // The array list is to store each of our bill objects
            billsList = new ArrayList<>();

            // The hash map is to store our bill ID as our key, and its corresponding bill object
            //      as the value for easier access
            billsMap = new HashMap<>();
            firstAPItask = true;
        }
        try {
            JSONObject object = new JSONObject(APIresult);
            JSONArray array = object.getJSONArray("results");

            for(int i = 0; i < array.length(); i++) {
                JSONObject currBill = (JSONObject) array.get(i);

                JSONObject sponsor = currBill.getJSONObject("sponsor");
                JSONObject history = currBill.getJSONObject("history");
                JSONObject urls = currBill.getJSONObject("urls");
                JSONObject last_version = currBill.getJSONObject("last_version");

                Bill newBill = new Bill(
                        checkStringAttribute(currBill, "bill_id"),
                        getBillTitle(currBill),
                        checkStringAttribute(currBill, "introduced_on"),
                        checkStringAttribute(currBill, "bill_type"),
                        checkStringAttribute(sponsor, "title"),
                        checkStringAttribute(sponsor, "last_name"),
                        checkStringAttribute(sponsor, "first_name"),
                        checkStringAttribute(currBill, "chamber"),
                        getBillStatus(history),
                        checkStringAttribute(urls, "congress"),
                        checkStringAttribute(last_version, "version_name"),
                        checkStringAttribute(last_version.getJSONObject("urls"), "pdf")
                );

                billsList.add(newBill);
                billsMap.put(newBill.getBill_id(), newBill);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // For bill titles, we want the "short_title" when it exists, otherwise, we want the
    //      "official_title".
    public String getBillTitle(JSONObject object) {
        String title = "";
        if(object.isNull("short_title")) {
            title = checkStringAttribute(object, "official_title");
        }
        else {
            title = checkStringAttribute(object, "short_title");
        }
        return title;
    }

    // Active bills have "active" attribute in the JSON as "true" and New bills have "active" attribute
    //      in the JSON as false, therefore we set the bill object's active field to "Active" if the "active"
    //      field in the JSON is true, and we set the bill object's active field to "New" otherwise
    public String getBillStatus(JSONObject object) {
        String status = "";
        try {
            if(object.has("active")) {
                if(object.getBoolean("active")) {
                    status = ACTIVE;
                }
                else {
                    status = NEW;
                }
            }
            else {
                status = "N.A.";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }

    // If the JSON has the given attribute for the given bill, return the value,
    //      otherwise return "N.A."
    public String checkStringAttribute(JSONObject object, String attribute) {
        if(object.has(attribute)) {
            try {
                return (String) object.get(attribute);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            return "N.A.";
        }
        return "";
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
                // Capture the value of firstAPItask before it's changed by the call to createBillsDataStructures
                boolean currentValueFirstAPItask = firstAPItask;

                createBillsDataStructures(APIresult);

                // Because our PHP returns our Active and New bills in two separate JSON responses, we need to call
                //      AsyncTask twice. The first time it's called, we want to create the list view for Active bills.
                //      The second time, we don't want to do this (because at that point it would be the JSON response
                //      for New Bills), and we just want to add it to our Bills data structure via createBillsStructures()
                //
                // Note: In retrospect, the "firstAPItask" variable is not intuitively initialized. It is initialized as false,
                //      then we use that to do our checks if this is indeed the first task, when intuitively, we should have
                //      initialized it to true, and do our checks based on that.
                if(!currentValueFirstAPItask) {
                    BillItemAdapter adapterBillsActive = new BillItemAdapter(getActivity(), billsList);

                    ListView listViewBillsActive = (ListView) view.findViewById(R.id.listview1_bills);

                    adapterBillsActive.getFilter().filter(ACTIVE);

                    listViewBillsActive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(BillItemAdapter.adapterContext, BillDetail.class);
                            intent.putExtra(BILL_ID_KEY, BillItemAdapter.billsList.get(position).getBill_id());
                            BillItemAdapter.adapterContext.startActivity(intent);
                        }
                    });

                    listViewBillsActive.setAdapter(adapterBillsActive);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
