package com.example.congressapi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static android.graphics.PorterDuff.Mode.SRC;

public class LegislatorDetail extends AppCompatActivity {

    // *** NOTE: SEE COMMENTS IN BillIDetail.java *** //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_detail);

        this.setTitle("Legislator Info");

        String legislatorID = getIntent().getExtras().getString(LegislatorsFragment.LEGISLATOR_ID_KEY);
        final Legislator legislator = LegislatorsFragment.legislatorsMap.get(legislatorID);

        // Set up favorite button
        final ImageButton fave = (ImageButton) findViewById(R.id.imageButton_legislator_fave);
        setFavoriteButton(legislator, fave);

        // Set up Facebook button to open a link in a browser when the link exists and show a toast message otherwise
        final ImageButton facebook = (ImageButton) findViewById(R.id.imageButton_legislator_facebook);
        facebook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String facebookID = legislator.getFacebook_id();
                if(facebookID.equals("N.A.")) {
                    Toast.makeText(LegislatorDetail.this, "Facebook not available", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(legislator.getFacebookUrl()));
                    if(facebookIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(facebookIntent);
                    }
                }
            }

        });

        // Set up the Twitter button to open a link in a browser when the link exists and show a toast message otherwise
        final ImageButton twitter = (ImageButton) findViewById(R.id.imageButton_legislator_twitter);
        twitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String twitterID = legislator.getTwitter_id();
                if(twitterID.equals("N.A.")) {
                    Toast.makeText(LegislatorDetail.this, "Twitter not available", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(legislator.getTwitterUrl()));
                    if(twitterIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(twitterIntent);
                    }
                }
            }

        });

        // Set up the website button to open a link in a browswer when the link exists and show a toast message otherwise
        final ImageButton website = (ImageButton) findViewById(R.id.imageButton_legislator_website);
        website.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String website = legislator.getWebsite();
                if(website.equals("N.A.")) {
                    Toast.makeText(LegislatorDetail.this, "Website not available", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(legislator.getWebsite()));
                    if(websiteIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(websiteIntent);
                    }
                }
            }

        });

        // Set up the legislator image using Picasso
        ImageView image = (ImageView) findViewById(R.id.imageView_legislator_detail_img);
        Picasso.with(this)
                .load(legislator.getImageURL())
//                .resize(180, 220)
//                .centerCrop()
                .into(image);

        setPartyImg(legislator.getParty());

        TextView partyName = (TextView) findViewById(R.id.detail_legislator_party);
        partyName.setText(legislator.getPartyFull());

        TextView name = (TextView) findViewById(R.id.detail_legislator_name);
        name.setText(legislator.getNameFull());

        // If an email exists, set up the email link to open a new email message to the legislator
        TextView email = (TextView) findViewById(R.id.detail_legislator_email);
        email.setText(legislator.getOc_email());
        if(!legislator.getOc_email().equals("N.A.")) {
            email.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String emailAddress = legislator.getOc_email();
                    String[] emailAddressArray = {emailAddress};
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(("mailto:")));
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddressArray);

                    if (emailIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(emailIntent);
                    }
                }

            });
        }

        TextView chamber = (TextView) findViewById(R.id.detail_legislator_chamber);
        chamber.setText(legislator.getChamber());

        TextView phone = (TextView) findViewById(R.id.detail_legislator_phone);
        phone.setText(legislator.getPhone());

        TextView startTerm = (TextView) findViewById(R.id.detail_legislator_start_term);
        startTerm.setText(legislator.formatDate(legislator.getTerm_start()));

        TextView endTerm = (TextView) findViewById(R.id.detail_legislator_end_term);
        endTerm.setText(legislator.formatDate(legislator.getTerm_end()));

        // Set up the progress bar
        ProgressBar termProgressBar = (ProgressBar) findViewById(R.id.progressBar_legislator);
        int progressValue = legislator.calculateTermProgress();

        // Set how much of the progress bar is filled based on the progressValue
        termProgressBar.setProgress(progressValue);

        // Set the color of the fill to yellow
        termProgressBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFA500"), PorterDuff.Mode.SRC_IN);

        // Place the percentage in the middle of the progress bar
        TextView termProgress = (TextView) findViewById(R.id.detail_legislator_progress);
        termProgress.setText(legislator.getTermProgress());

        TextView office = (TextView) findViewById(R.id.detail_legislator_office);
        office.setText(legislator.getOffice());

        TextView state = (TextView) findViewById(R.id.detail_legislator_state);
        state.setText(legislator.getState());

        TextView fax = (TextView) findViewById(R.id.detail_legislator_fax);
        fax.setText(legislator.getFax());

        TextView birthday = (TextView) findViewById(R.id.detail_legislator_birthday);
        birthday.setText(legislator.formatDate(legislator.getBirthday()));
    }

    public void setPartyImg(String party) {
        ImageView partyImage = (ImageView) findViewById(R.id.imageView_legislator_party);
        if(party.equals("R")) {
            partyImage.setImageResource(R.drawable.republican);
        }
        else if(party.equals("D")) {
            partyImage.setImageResource(R.drawable.democrat);
        }
        else {
            partyImage.setImageResource(R.drawable.independent);
        }

    }

    private void setFavoriteButton(final Legislator legislator, final ImageButton fave) {
        // When we display details, if the legislator is a favorite, display the filled star
        if(legislator.isFavorite().equalsIgnoreCase("true")) {
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
                if(legislator.isFavorite().equalsIgnoreCase("true")) {
                    fave.setImageResource(R.drawable.star_outline);
                    legislator.setFavorite("false");
                }

                // If it is not currently a favorite, change the image to the filled star and set the favorite value
                //      to true
                else {
                    fave.setImageResource(R.drawable.star_filled);
                    legislator.setFavorite("true");
                }

//                Toast.makeText(LegislatorDetail.this, legislator.toString(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
