package com.drivehype.www.drivehype.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.drivehype.www.drivehype.NavDrawerFragments.ActivitiesFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.BluetoothFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.ChannelsFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.EncountersFragment;

import com.drivehype.www.drivehype.NavDrawerFragments.FriendsFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.HomeFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.MessageFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.MyScreenFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.NavigationDrawerFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.NearByFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.PullFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.PushFragment;
import com.drivehype.www.drivehype.NavDrawerFragments.WIFI_Fragment;
import com.drivehype.www.drivehype.R;
import com.drivehype.www.drivehype.provider.Images;
import com.drivehype.www.drivehype.ui.ImageDetailActivity;
import com.drivehype.www.drivehype.util.FB_Data_Pull;
import com.drivehype.www.drivehype.util.ImageFetcher;
import com.facebook.Session;
import com.facebook.model.GraphUser;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.Permission;


public class MainActivity extends ActionBarActivity
        implements HomeFragment.OnFragmentInteractionListener,WIFI_Fragment.OnFragmentInteractionListener,NearByFragment.OnFragmentInteractionListener,MyScreenFragment.OnFragmentInteractionListener,FriendsFragment.OnFragmentInteractionListener,EncountersFragment.OnFragmentInteractionListener,ChannelsFragment.OnFragmentInteractionListener,BluetoothFragment.OnFragmentInteractionListener,ActivitiesFragment.OnFragmentInteractionListener,PullFragment.OnFragmentInteractionListener,PushFragment.OnFragmentInteractionListener,MessageFragment.OnFragmentInteractionListener,NavigationDrawerFragment.NavigationDrawerCallbacks {

 /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ImageFetcher mImageFetcher;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private MenuItem logoutItem;
    public static Bitmap splashMap;
    public static boolean mapIsSet=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment objFragment=null;

            switch (position) {
                case 0:
                    objFragment = HomeFragment.newInstance(position + 1);
                    break;
                case 1:
                    objFragment = PushFragment.newInstance(position + 1);
                    break;
                case 2:
                    objFragment = WIFI_Fragment.newInstance();
                   break;
                case 3:
                    objFragment = BluetoothFragment.newInstance();
                    break;
                case 4:
                    objFragment = MyScreenFragment.newInstance();
                    break;
                case 5:
                    //fb images
                    final Intent i= new Intent( this,ImageGridActivity.class);
                    Images.setImageSource(0);
                    startActivity(i);
                    return;
                case 6:
                    //storage images
                    final Intent intent= new Intent( this,ImageGridActivity.class);
                    Images.setImageSource(1);
                    startActivity(intent);
                    return;
                case 7:
                    objFragment = FriendsFragment.newInstance();
                    break;
                case 8:
                    objFragment = ChannelsFragment.newInstance();
                    break;
                case 9:
                    objFragment = MessageFragment.newInstance(position + 1);
                    break;
                case 10:
                    objFragment = EncountersFragment.newInstance();
                    break;
                case 11:
                    objFragment = NearByFragment.newInstance();
                    break;
                case 12:
                    objFragment = ActivitiesFragment.newInstance();
                    break;


            }


            onSectionAttached(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFragment)
                    .commit();

    }

    public void onSectionAttached(int number) {
        switch (number+1) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
            case 3:
                mTitle = "WIFI";
                break;
            case 4:
                mTitle = "Bluetooth";
                break;
            case 5:
                mTitle = "My Screen";
                break;
            case 6:
                mTitle = "Facebook";
                break;
            case 7:
                mTitle = "Local Storage";
                break;
            case 8:
                mTitle= "Friends";
                break;
            case 9:
                mTitle=  "Channels";
                break;
            case 10:
                mTitle=  "Messages";
                break;
            case 11:
                mTitle=  "Encounters";
                break;
            case 12:
                mTitle=  "Near By";
                break;
            case 13:
                mTitle=  "Activities";
                break;
        }


    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        if(FB_Data_Pull.user!=null)
        logoutItem.setTitle(FB_Data_Pull.user.getFirstName()+": Sign Out");
        else
            logoutItem.setTitle("Log In");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            logoutItem = menu.findItem(R.id.action_logout);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.account:
                showAccount();
                return true;
            case R.id.permissions:
                showPermissions();
                return true;
            case R.id.share:
                shareApp();
                return true;
            case R.id.rate:
                rate();
                return true;
            case R.id.action_logout:
                logOut();
                return true;
            case R.id.action_settings:
                settings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAccount(){

        Intent i = new Intent(MainActivity.this, Account.class);
        startActivity(i);

    }
    private void shareApp() {
        Intent i = new Intent(MainActivity.this, Share.class);
        startActivity(i);

    }

    private void rate() {
        Intent i = new Intent(MainActivity.this, Rate.class);
        startActivity(i);

    }

    private void settings() {
        Intent i = new Intent(MainActivity.this, Settings.class);
        startActivity(i);

    }

    private void logOut() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure you want to log out? ");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //TODO
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    private void showPermissions() {
        Intent i = new Intent(MainActivity.this, AppPermissions.class);
        startActivity(i);

    }



    @Override
    public void onFragmentInteraction(Uri uri) {
        // what should this activity do when fragment calls this method
    }

    @Override
    public void onFragmentInteraction(GraphUser user) {
        logoutItem.setTitle(user.getFirstName()+": Sign Out");
        // what should this activity do when fragment calls this method
    }

    @Override
    public void onFragmentInteraction (Bitmap img) {

        splashMap=img;
        mapIsSet=true;
        // what should this activity do when fragment calls this method
    }


}

