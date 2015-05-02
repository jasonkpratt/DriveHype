package com.drivehype.www.drivehype.NavDrawerFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.drivehype.www.drivehype.R;
import com.drivehype.www.drivehype.ui.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WiFiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WiFiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WiFiFragment extends Fragment {
    String url = "http://www.drivehype.com/display.html";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *


     * @return A new instance of fragment WiFiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WiFiFragment newInstance() {
        WiFiFragment fragment = new WiFiFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
      //  args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WiFiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View display= inflater.inflate(R.layout.fragment_wifi, container, false);
        WebView displayWebView = (WebView) display.findViewById(R.id.displaywebview);
        displayWebView.setWebViewClient(new WebViewClient());
        displayWebView.getSettings().setJavaScriptEnabled(true);
        displayWebView.loadUrl("http://www.drivehype.com/display.html");
        CookieManager cookieManager = CookieManager.getInstance();


        displayWebView.setWebChromeClient(new WebChromeClient() {
                                              @Override
                                              public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                                                  //Required functionality here
                                                  return super.onJsAlert(view, url, message, result);
                                              }
                                          });

            String cookieString = "uid=" + MainActivity.uid;
            Log.d("displayPage","uid "+MainActivity.uid);
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(url,cookieString);
            return display;

        }

                // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
