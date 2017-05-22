package com.example.congressapi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BillDetail extends AppCompatActivity {

    public static final String TAG = "BillDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        // Set the title for this detail page
        this.setTitle("Bill Info");

        // Get the bill ID from the intent sent by the fragment to this activity
        String billID = getIntent().getExtras().getString(BillsFragment.BILL_ID_KEY);

        // Using that bill ID, get the actual bill object using the billsMap built by the BillsFragment
        final Bill bill = BillsFragment.billsMap.get(billID);

        // Get handles to the TextViews in the detail layout
        TextView ID = (TextView) findViewById(R.id.detail_bill_id);
        TextView title = (TextView) findViewById(R.id.detail_bill_title);
        TextView type = (TextView) findViewById(R.id.detail_bill_type);
        TextView sponsor = (TextView) findViewById(R.id.detail_bill_sponsor);
        TextView chamber = (TextView) findViewById(R.id.detail_bill_chamber);
        TextView status = (TextView) findViewById(R.id.detail_bill_status);
        TextView introduced = (TextView) findViewById(R.id.detail_bill_introduced);
        TextView congressURL = (TextView) findViewById(R.id.detail_bill_congress_url);
        TextView version = (TextView) findViewById(R.id.detail_bill_version);
        TextView billURL = (TextView) findViewById(R.id.detail_bill_url);

        // Use the bill object to gather the requied data to display in the detail activity page and
        //      set the text in the TextViews in the detail layout
        ID.setText(bill.getBill_id());
        title.setText(bill.getTitle());
        type.setText(bill.getBill_type());
        sponsor.setText(bill.getSponsor_full_name());
        chamber.setText(bill.getChamber());
        status.setText(bill.getActive());
        introduced.setText(bill.getIntroduced_on_formatted());
        congressURL.setText(bill.getUrl());
        version.setText(bill.getVersion_name());
        billURL.setText(bill.getPdf());

        // Set up the URLs for the Congress website and Bill PDF
        setupURLs(bill, congressURL, billURL);

        // Establish the logic for the favorite button
        ImageButton fave = (ImageButton) findViewById(R.id.imageButton_bill_fave);
        setFavoriteButton(bill, fave);
    }

    private void setFavoriteButton(final Bill bill, final ImageButton fave) {
        // When we display details, if the bill is a favorite, display the filled star
        if(bill.isFavorite().equalsIgnoreCase("true")) {
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
                if(bill.isFavorite().equalsIgnoreCase("true")) {
                    fave.setImageResource(R.drawable.star_outline);
                    bill.setFavorite("false");
                }

                // If it is not currently a favorite, change the image to the filled star and set the favorite value
                //      to true
                else {
                    fave.setImageResource(R.drawable.star_filled);
                    bill.setFavorite("true");
                }
            }
        });
    }

    private void setupURLs(final Bill bill, final TextView congressURL, final TextView billURL) {
        congressURL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String website = bill.getUrl();
                if(website.equals("N.A.")) {
                    Toast.makeText(BillDetail.this, "Website not available", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bill.getUrl()));
                    if(websiteIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(websiteIntent);
                    }
                }
            }

        });

        // Here we prepend "https://docs.google.com/viewer?url=" to the bill PDF URL so that it will open a view
        //      of the PDF in the browser instead of download the PDF file
        billURL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String website = bill.getPdf();
                if(website.equals("N.A.")) {
                    Toast.makeText(BillDetail.this, "Website not available", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/viewer?url="+bill.getPdf()));
                    if(websiteIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(websiteIntent);
                    }
                }
            }

        });
    }
}
