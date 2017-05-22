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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommitteesFragment extends Fragment implements TabHost.OnTabChangeListener {

    // *** NOTE: SEE COMMENTS IN BillsFragment.java *** //

    public static final String COMMITTEES_TAB_1 = "Committees Tab 1";
    public static final String COMMITTEES_TAB_2 = "Committees Tab 2";
    public static final String COMMITTEES_TAB_3 = "Committees Tab 3";

    public final static String AWS_COMMITTEES = "http://cs571.c2vrp7jmpp.us-east-1.elasticbeanstalk.com/index.php?database=committees&keyword=all";
    public static final String COMMITTEE_ID_KEY = "committee_id_key";
    public static final String TAG = "CommitteesFragment";
    public static final String HOUSE = "HOUSE";
    public static final String SENATE = "SENATE";
    public static final String JOINT = "JOINT";

    private TabHost tabhost;
    private TextView text;
    private View view;

    public static List<Committee> committeesList;
    public static Map<String, Committee> committeesMap;

    public CommitteesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_committees, container, false);

        tabhost = (TabHost) view.findViewById(R.id.tabhost_committees);
        tabhost.setup();

        TabHost.TabSpec spec = tabhost.newTabSpec(COMMITTEES_TAB_1);
        spec.setContent(R.id.tab1_committees);
        spec.setIndicator(HOUSE);
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec(COMMITTEES_TAB_2);
        spec.setContent(R.id.tab2_committees);
        spec.setIndicator(SENATE);
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec(COMMITTEES_TAB_3);
        spec.setContent(R.id.tab3_committees);
        spec.setIndicator(JOINT);
        tabhost.addTab(spec);

        tabhost.setOnTabChangedListener(this);

        APItask task = new APItask();
        task.execute(AWS_COMMITTEES);

        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        if(tabId.equals(COMMITTEES_TAB_1) && committeesList != null) {
            CommitteeItemAdapter adapterCommitteesHouse = new CommitteeItemAdapter(getActivity(), committeesList);
            adapterCommitteesHouse.getFilter().filter(HOUSE);

            ListView listViewCommitteesHouse = (ListView) view.findViewById(R.id.listview1_committees);

            listViewCommitteesHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CommitteeItemAdapter.adapterContext, CommitteeDetail.class);
                    intent.putExtra(COMMITTEE_ID_KEY, CommitteeItemAdapter.committeesList.get(position).getCommittee_id());
                    CommitteeItemAdapter.adapterContext.startActivity(intent);
                }
            });

            listViewCommitteesHouse.setAdapter(adapterCommitteesHouse);
        }

        else if(tabId.equals(COMMITTEES_TAB_2) && committeesList != null) {
            CommitteeItemAdapter adapterCommitteesSenate = new CommitteeItemAdapter(getActivity(), committeesList);

            adapterCommitteesSenate.getFilter().filter(SENATE);

            ListView listViewCommitteesSenate = (ListView) view.findViewById(R.id.listview2_committees);

            listViewCommitteesSenate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CommitteeItemAdapter.adapterContext, CommitteeDetail.class);
                    intent.putExtra(COMMITTEE_ID_KEY, CommitteeItemAdapter.committeesList.get(position).getCommittee_id());
                    CommitteeItemAdapter.adapterContext.startActivity(intent);
                }
            });

            listViewCommitteesSenate.setAdapter(adapterCommitteesSenate);
        }

        else if(tabId.equals(COMMITTEES_TAB_3) && committeesList != null) {
            CommitteeItemAdapter adapterCommitteesJoint = new CommitteeItemAdapter(getActivity(), committeesList);

            adapterCommitteesJoint.getFilter().filter(JOINT);

            ListView listViewCommitteesJoint = (ListView) view.findViewById(R.id.listview3_committees);

            listViewCommitteesJoint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CommitteeItemAdapter.adapterContext, CommitteeDetail.class);
                    intent.putExtra(COMMITTEE_ID_KEY, CommitteeItemAdapter.committeesList.get(position).getCommittee_id());
                    CommitteeItemAdapter.adapterContext.startActivity(intent);
                }
            });

            listViewCommitteesJoint.setAdapter(adapterCommitteesJoint);
        }
    }

    public void createCommitteesDataStructures(String APIresult) {
        committeesList = new ArrayList<>();
        committeesMap = new HashMap<>();
        try {
            JSONObject object = new JSONObject(APIresult);
            JSONArray array = object.getJSONArray("results");

            for(int i = 0; i < array.length(); i++) {
                JSONObject currCommittee = (JSONObject) array.get(i);

                Committee newCommittee = new Committee(
                        checkAttribute(currCommittee, "committee_id"),
                        checkAttribute(currCommittee, "name"),
                        checkAttribute(currCommittee, "chamber"),
                        checkAttribute(currCommittee, "parent_committee_id"),
                        checkAttribute(currCommittee, "phone"),
                        checkAttribute(currCommittee, "office")
                );

                committeesList.add(newCommittee);
                committeesMap.put(newCommittee.getCommittee_id(), newCommittee);
            }

            Collections.sort(committeesList, new Comparator<Committee>() {
                @Override
                public int compare(Committee o1, Committee o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String checkAttribute(JSONObject object, String attribute) {
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
                createCommitteesDataStructures(APIresult);

                CommitteeItemAdapter adapter = new CommitteeItemAdapter(getActivity(), committeesList);
                adapter.getFilter().filter(HOUSE);

                ListView listView = (ListView) view.findViewById(R.id.listview1_committees);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(CommitteeItemAdapter.adapterContext, CommitteeDetail.class);
                        intent.putExtra(COMMITTEE_ID_KEY, CommitteeItemAdapter.committeesList.get(position).getCommittee_id());
                        CommitteeItemAdapter.adapterContext.startActivity(intent);
                    }
                });

                listView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}