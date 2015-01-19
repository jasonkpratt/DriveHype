package com.drivehype.www.drivehype.NavDrawerFragments;
import android.app.Activity;
import android.content.Intent;
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
import com.facebook.*;
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

    private static Bundle args;
    private static UiLifecycleHelper uiHelper ;
    protected boolean isResumed = false;
    private static final int REAUTH_ACTIVITY_CODE = 100;
    private LoginButton authButton;
    private TextView userNameView;
    public static Session session;
    private static FB_Data_Pull FBData;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };


    public interface OnFragmentInteractionListener {


        public void onFragmentInteraction(Uri uri);

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
            Log.d("newpic", "made it here, oh yeah");
            session = Session.getActiveSession();
            profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
            profilePictureView.setCropped(true);
            userNameView = (TextView) view.findViewById(R.id.profile_name);
            authButton=(LoginButton) view.findViewById(R.id.authButton);
            authButton.setFragment(this);
            FBData=FB_Data_Pull.getInstance(this);
            if(FB_Data_Pull.user!=null) {
               profilePictureView.setProfileId(FB_Data_Pull.user.getId());
                Log.d("newpic1", "userData"+FB_Data_Pull.user.toString());
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
        Log.d("newpic3", "setting picture" + FB_Data_Pull.user.getId().toString());

    }



    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Only make changes if the activity is visible

        if (session != null && session.isOpened()) {
            // If the session state is open:
            // Show the authenticated fragment
            userNameView.setVisibility(View.INVISIBLE);
            authButton.setVisibility(View.INVISIBLE);


               if(FB_Data_Pull.user!=null) {
                   profilePictureView.setProfileId(FB_Data_Pull.user.getId());
                   Log.d("newpic2", "userData" + FB_Data_Pull.user.getId().toString());
               }


        } else if (state.isClosed()) {
            // If the session state is closed:
            // Show the login fragment


        }
    }


}

