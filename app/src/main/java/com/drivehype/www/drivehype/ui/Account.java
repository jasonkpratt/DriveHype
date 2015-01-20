package com.drivehype.www.drivehype.ui;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.drivehype.www.drivehype.R;
import com.drivehype.www.drivehype.util.FB_Data_Pull;

import org.json.JSONException;

public class Account extends ActionBarActivity {

    private  static TextView name,email,location,birthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_account, container, false);
            name=(TextView)rootView.findViewById(R.id.FB_name);
            email=(TextView)rootView.findViewById(R.id.FB_email);
            birthday=(TextView)rootView.findViewById(R.id.FB_bday);
            location=(TextView)rootView.findViewById(R.id.FB_location);

            name.setText(FB_Data_Pull.user.getName());
            try {
                email.setText(FB_Data_Pull.user.getInnerJSONObject().getString("email"));
                birthday.setText(FB_Data_Pull.user.getBirthday());
                location.setText(FB_Data_Pull.user.getLocation().getName());
            }
                catch (JSONException e) { }
            return rootView;
        }
    }
}
