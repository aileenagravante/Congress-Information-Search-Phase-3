package com.example.congressapi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_LEGISLATORS_TAG = "Legislators";
    private static final String FRAGMENT_BILLS_TAG = "Bills";
    private static final String FRAGMENT_COMMITTEES_TAG = "Committees";
    private static final String FRAGMENT_FAVORITES_TAG = "Favorites";

    private static final String TAG = "MainActivity";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(FRAGMENT_LEGISLATORS_TAG);

        // For the first fragment added, we do not add to back stack so that upon press of the back button
        //      it just exits the app instead of removing the fragment and then exiting the app
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.tabhost_container, new LegislatorsFragment(), FRAGMENT_LEGISLATORS_TAG)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        // When we click the back button, if there is nothing left in the back stack
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            // Get the last fragment in the back stack by taking the size of the back stack
            //      and subtracting 1
            int index = (getSupportFragmentManager().getBackStackEntryCount()-1);

            // Remember to reset the title to the corresponding fragment which we'll be going
            //      back to (since currently, our titles are being set when the corresponding
            //      menu item is clicked)
            setTitle(getSupportFragmentManager().getBackStackEntryAt(index).getName());

            // After going back to the fragment, pop it from the back stack
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_legislators) {

            // Set the title
            this.setTitle(FRAGMENT_LEGISLATORS_TAG);

            // We pass in the corresponding FRAGMENT_TAG for this menu item, because we have logic
            //      in hideCurrentlyActiveFragment to skip the check for this menu item's fragment
            //      because we don't need to hide the fragment if it's the same as menu item
            hideCurrentlyActiveFragment(FRAGMENT_LEGISLATORS_TAG);

            // Get a handle to the memu item's corresponding fragment
            Fragment thisFragment = getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_LEGISLATORS_TAG);

            // If the fragment has not been previously added or instantiated, do so.
            if(thisFragment == null) {
                addFragment(R.id.tabhost_container, new LegislatorsFragment(), FRAGMENT_LEGISLATORS_TAG);
            }

            // Otherwise, just show the fragment
            else if(thisFragment.isAdded() && thisFragment.isHidden()) {
                showFragment(thisFragment, FRAGMENT_FAVORITES_TAG);
            }

        } else if (id == R.id.nav_bills) {

            this.setTitle(FRAGMENT_BILLS_TAG);

            hideCurrentlyActiveFragment(FRAGMENT_BILLS_TAG);

            Fragment thisFragment = getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_BILLS_TAG);

            if(thisFragment == null) {
                addFragment(R.id.tabhost_container, new BillsFragment(), FRAGMENT_BILLS_TAG);
            }
            else if(thisFragment.isAdded() && thisFragment.isHidden()) {
                showFragment(thisFragment, FRAGMENT_BILLS_TAG);
            }

        } else if (id == R.id.nav_committees) {

            this.setTitle(FRAGMENT_COMMITTEES_TAG);

            hideCurrentlyActiveFragment(FRAGMENT_COMMITTEES_TAG);

            Fragment thisFragment = getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_COMMITTEES_TAG);

            if(thisFragment == null) {
                addFragment(R.id.tabhost_container, new CommitteesFragment(), FRAGMENT_COMMITTEES_TAG);
            }

            else if(thisFragment.isAdded() && thisFragment.isHidden()) {
                showFragment(thisFragment, FRAGMENT_COMMITTEES_TAG);
            }

        } else if (id == R.id.nav_favorites) {

            this.setTitle(FRAGMENT_FAVORITES_TAG);

            hideCurrentlyActiveFragment(FRAGMENT_FAVORITES_TAG);

            Fragment thisFragment = getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_FAVORITES_TAG);

            if(thisFragment == null) {
                addFragment(R.id.tabhost_container, new FavoritesFragment(), FRAGMENT_FAVORITES_TAG);
            }

            else if(thisFragment.isAdded() && thisFragment.isHidden()) {
                showFragment(thisFragment, FRAGMENT_FAVORITES_TAG);
            }

        } else if (id == R.id.nav_aboutMe) {
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://aileenagravante.com"));
            if(websiteIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(websiteIntent);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // When we click a menu item, we want to hide the active fragment (fragment currently being shown)
    // Our single argument is the FRAGMENT_TAG of the menu item's corresponding fragment because
    //      if we're clicking the same menu item, we don't need to hide the corresponding fragment
    //      that's already being shown
    public void hideCurrentlyActiveFragment(String thisFragment) {
        Fragment legislatorsFrag = getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_LEGISLATORS_TAG);

        Fragment billsFrag = getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_BILLS_TAG);

        Fragment committeesFrag = getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_COMMITTEES_TAG);

        Fragment favoritesFrag = getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_FAVORITES_TAG);


        if(!thisFragment.equals(FRAGMENT_LEGISLATORS_TAG) && isFragmentActive(legislatorsFrag)) {
            hideFragment(legislatorsFrag, FRAGMENT_LEGISLATORS_TAG);
        }

        else if(!thisFragment.equals(FRAGMENT_BILLS_TAG) && isFragmentActive(billsFrag)) {
            hideFragment(billsFrag, FRAGMENT_BILLS_TAG);
        }

        else if(!thisFragment.equals(FRAGMENT_COMMITTEES_TAG) && isFragmentActive(committeesFrag)) {
            hideFragment(committeesFrag, FRAGMENT_COMMITTEES_TAG);
        }

        else if(!thisFragment.equals(FRAGMENT_FAVORITES_TAG) && isFragmentActive(favoritesFrag)) {
            hideFragment(favoritesFrag, FRAGMENT_FAVORITES_TAG);
        }
    }

    // Helper method to check if the fragment isn't null, has been added, and is visible
    public boolean isFragmentActive(Fragment fragment) {
        if(fragment != null && fragment.isAdded() && fragment.isVisible()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addFragment(int containerID, Fragment fragmentToAdd, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerID, fragmentToAdd, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
//        Log.i(TAG, "addFragment: " + fragmentTag + " " + getSupportFragmentManager().getBackStackEntryCount());
    }

    // Helper method to hide the given fragment
    public void hideFragment(Fragment fragment, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(fragmentTag)
                .hide(fragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
//        Log.i(TAG, "hideFragment: " + fragmentTag + " " + getSupportFragmentManager().getBackStackEntryCount());
    }

    // Helper function to show the fragment
    public void showFragment(Fragment fragmentToShow, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(fragmentTag)
                .show(fragmentToShow)
                .commit();

        getSupportFragmentManager().executePendingTransactions();
//        Log.i(TAG, "showFragment: " + fragmentTag + " " + getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}
