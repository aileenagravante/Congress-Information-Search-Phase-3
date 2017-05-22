package com.example.congressapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CommitteeDetail extends AppCompatActivity {

    // *** NOTE: SEE COMMENTS IN BillDetail.java *** //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_detail);

        this.setTitle("Committee Info");

//        String committeeID = getIntent().getExtras().getString(CommitteeItemAdapter.COMMITTEE_ID_KEY);
        String committeeID = getIntent().getExtras().getString(CommitteesFragment.COMMITTEE_ID_KEY);
        final Committee committee = CommitteesFragment.committeesMap.get(committeeID);

        TextView ID = (TextView) findViewById(R.id.detail_committee_id);
        TextView name = (TextView) findViewById(R.id.detail_committee_name);
        TextView chamber = (TextView) findViewById(R.id.detail_committee_chamber);
        TextView parent = (TextView) findViewById(R.id.detail_committee_parent);
        TextView contact = (TextView) findViewById(R.id.detail_committee_phone);
        TextView office = (TextView) findViewById(R.id.detail_committee_office);

        ID.setText(committee.getCommittee_id());
        name.setText(committee.getName());
        chamber.setText(committee.getChamber());
        parent.setText(committee.getParent_committee_id());
        contact.setText(committee.getPhone());
        office.setText(committee.getOffice());

        setChamberImg(committee.getChamber());

        final ImageButton fave = (ImageButton) findViewById(R.id.imageButton_committee_fave);
        setFavoriteButton(committee, fave);
    }

    public void setChamberImg(String chamber) {
        ImageView chamberImg = (ImageView)findViewById(R.id.image_committee_chamber);
        if(chamber.equals("House")) {
            chamberImg.setImageResource(R.drawable.house);
        }
        else {
            chamberImg.setImageResource(R.drawable.senate);
        }

    }

    private void setFavoriteButton(final Committee committee, final ImageButton fave) {
        // When we display details, if the committee is a favorite, display the filled star
        if(committee.isFavorite().equalsIgnoreCase("true")) {
            fave.setImageResource(R.drawable.star_filled);
        }
        // Otherwise, display the outline star
        else {
            fave.setImageResource(R.drawable.star_outline);
        }

        fave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // When favorite is clicked, if it is currently a favorite, change the image to the outline star
                //      and set the favorite value to false
                if(committee.isFavorite().equalsIgnoreCase("true")) {
                    fave.setImageResource(R.drawable.star_outline);
                    committee.setFavorite("false");
                }

                // If it is not currently a favorite, change the image to the filled star and set the favorite value
                //      to true
                else {
                    fave.setImageResource(R.drawable.star_filled);
                    committee.setFavorite("true");
                }

//                Toast.makeText(CommitteeDetail.this, committee.toString(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
