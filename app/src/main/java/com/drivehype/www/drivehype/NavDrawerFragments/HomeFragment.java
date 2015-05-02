package com.drivehype.www.drivehype.NavDrawerFragments;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import com.drivehype.www.drivehype.R;
import com.drivehype.www.drivehype.ui.MainActivity;
import com.drivehype.www.drivehype.util.FB_Data_Pull;
import com.drivehype.www.drivehype.util.ImageFetcher;
import com.facebook.*;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.widget.ProfilePictureView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.InputStream;


public class HomeFragment extends Fragment {

    private static ProfilePictureView profilePictureView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    private static Bundle args;
    private static UiLifecycleHelper uiHelper ;
    protected boolean isResumed = false;
    private static final int REAUTH_ACTIVITY_CODE = 100;
    private LoginButton authButton;
    private TextView userNameView;
    private TextView user_label;
    public static Session session;
    private static FB_Data_Pull FBData;
    private ImageFetcher mImageFetcher;
    private static String userId;


    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };


    public interface OnFragmentInteractionListener {


        public void onFragmentInteraction(GraphUser user);
        public void onFragmentInteraction(Bitmap img);
        public void onFragmentInteraction(String userId);

    }

    public static HomeFragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();
        args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);
        return fragment;

    }

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int myImageThumbSize=getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
        mImageFetcher = new ImageFetcher(getActivity(), myImageThumbSize);
        if (getArguments() != null) {
          //  mParam1 = getArguments().getString(ARG_SECTION_NUMBER);

        }
    }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            //get FB user and albums, log in if needed
            session = Session.getActiveSession();
            profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
            profilePictureView.setCropped(true);
            userNameView = (TextView) view.findViewById(R.id.profile_name);
            user_label= (TextView) view.findViewById(R.id.user_label);

            authButton=(LoginButton) view.findViewById(R.id.authButton);
            authButton.setFragment(this);
            FBData=FB_Data_Pull.getInstance(this);

            if(FB_Data_Pull.user!=null) {
               profilePictureView.setProfileId(FB_Data_Pull.user.getId());
                userId=FB_Data_Pull.user.getId();
                Log.d("newpic1", "userData"+FB_Data_Pull.user.toString());
                Log.d("newpic3", "set on create view" + FB_Data_Pull.user.getId().toString());
                user_label.setText("User: "+FB_Data_Pull.user.getFirstName());
                userNameView.setText("");
                authButton.setVisibility(View.INVISIBLE);
                mListener.onFragmentInteraction(userId);
            }


            return view;
            }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REAUTH_ACTIVITY_CODE) {
            uiHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

    }

    public void setPicture(GraphUser user) {

        profilePictureView.setProfileId(FB_Data_Pull.user.getId());
        Log.d("newpic3", "User getID" + FB_Data_Pull.user.getId().toString());
        user_label.setText("User: "+user.getFirstName());
        userNameView.setText("");
        authButton.setVisibility(View.INVISIBLE);
        mListener.onFragmentInteraction(user);


    }








    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Only make changes if the activity is visible

        if (session != null && session.isOpened()) {
            // If the session state is open:
            // Show the authenticated fragment
            //userNameView.setVisibility(View.INVISIBLE);
           // authButton.setVisibility(View.INVISIBLE);


               if(FB_Data_Pull.user!=null) {
                   profilePictureView.setProfileId(FB_Data_Pull.user.getId());
                   Log.d("newpic2", "userData" + FB_Data_Pull.user.toString());
               }


        } else if (state.isClosed()) {
            // If the session state is closed:
            // Show the login fragment


        }
    }




private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


    @Override

    protected Bitmap doInBackground(String... param) {

        // TODO Auto-generated method stub

        return downloadBitmap(param[0]);

    }


    @Override

    protected void onPreExecute() {

        Log.i("Async", "onPreExecute Called");

        // simpleWaitDialog = ProgressDialog.show(ImageDownladerActivity.this,

        //   "Wait", "Downloading Image");



    }







    private Bitmap downloadBitmap(String url) {

        // initilize the default HTTP client object

        final DefaultHttpClient client = new DefaultHttpClient();



        //forming a HttoGet request

        final HttpGet getRequest = new HttpGet(url);

        try {



            HttpResponse response = client.execute(getRequest);



            //check 200 OK for success

            final int statusCode = response.getStatusLine().getStatusCode();



            if (statusCode != HttpStatus.SC_OK) {

                Log.w("ImageDownloader", "Error " + statusCode +

                        " while retrieving bitmap from " + url);

                return null;



            }



            final HttpEntity entity = response.getEntity();

            if (entity != null) {

                InputStream inputStream = null;

                try {

                    // getting contents from the stream

                    inputStream = entity.getContent();



                    // decoding stream data back into image Bitmap that android understands

                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mListener.onFragmentInteraction(bitmap);




                    return bitmap;

                } finally {

                    if (inputStream != null) {

                        inputStream.close();

                    }

                    entity.consumeContent();

                }

            }

        } catch (Exception e) {

            getRequest.abort();

            Log.e("ImageDownloader", "Something went wrong while" +

                    " retrieving bitmap from " + url + e.toString());

        }



        return null;

    }

}


}

