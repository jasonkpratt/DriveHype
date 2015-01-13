package com.drivehype.www.drivehype;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.*;
import com.facebook.model.GraphLocation;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import android.widget.TextView;
import com.facebook.widget.ProfilePictureView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class HomeFragment extends Fragment {

    private static ProfilePictureView profilePictureView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    public static GraphUser user=null;
    Session session=null;
    private static UiLifecycleHelper uiHelper ;
    protected boolean isResumed = false;
    private static final int REAUTH_ACTIVITY_CODE = 100;
    private LoginButton authButton;
    private TextView userNameView;
    public String [] albumList;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };


    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(GraphUser user);
        public void onFragmentInteraction(String [] albumList);
    }

    public static HomeFragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();


        Bundle args = new Bundle();
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
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
        if (getArguments() != null) {
          //  mParam1 = getArguments().getString(ARG_SECTION_NUMBER);

        }
    }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
            profilePictureView.setCropped(true);
            userNameView = (TextView) view.findViewById(R.id.profile_name);
            session = Session.getActiveSession();

            authButton=(LoginButton) view.findViewById(R.id.authButton);
            authButton.setFragment(this);

            if (session != null && session.isOpened()) {
                // Get the user's data
                makeMeRequest();
                makeAlbumRequest();

            }


            // Set the id for the ProfilePictureView
            // view that in turn displays the profile picture.


            return view;
            }


    private void makeMeRequest() {
        // Make an API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (user != null) {
                            //userNameView.setText(user.getName());
                            profilePictureView.setProfileId(user.getId());
                                mListener.onFragmentInteraction(user);


                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }

    private void makeAlbumRequest( ) {

        /* make the API call */
        new Request(
                session,
                "/me/albums",
                null,
                HttpMethod.GET,
                new Request.Callback() {


                    public void onCompleted(Response response) {
                        try {

                            JSONObject innerJson = response.getGraphObject().getInnerJSONObject();
                            JSONArray data = innerJson.getJSONArray("data");
                            albumList=new String [data.length()];
                            for (int i=0; i<data.length();i++) {

                                JSONObject oneAlbum = data.getJSONObject(i);
                                //get your values
                                albumList[i]=oneAlbum.getString("name"); // this will return you the album's name.
                            }
                            userNameView.setText(albumList[0]);
                            mListener.onFragmentInteraction(albumList);


                        }
                        catch (JSONException e) { }


            /* handle the result */

                    }
                }
        ).executeAsync();

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

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Only make changes if the activity is visible

        if (session != null && session.isOpened()) {
            // If the session state is open:
            // Show the authenticated fragment
            userNameView.setVisibility(View.INVISIBLE);
            authButton.setVisibility(View.INVISIBLE);
            makeMeRequest();
            makeAlbumRequest();



        } else if (state.isClosed()) {
            // If the session state is closed:
            // Show the login fragment


        }
    }


}

