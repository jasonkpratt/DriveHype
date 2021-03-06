package com.drivehype.www.drivehype.NavDrawerFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * {@link PhotoAlbumsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotoAlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class PhotoAlbumsFragment extends Fragment {

    String url = "http://www.drivehype.com/PhotoAlbumMobileView.html";


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment PhotoAlbumsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoAlbumsFragment newInstance() {
        PhotoAlbumsFragment fragment = new PhotoAlbumsFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PhotoAlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View photoAlbumWebView = inflater.inflate(R.layout.fragment_photoalbums, container, false);
        WebView photoWebView = (WebView) photoAlbumWebView.findViewById(R.id.photowebview);
        photoWebView.setWebViewClient(new WebViewClient());
        photoWebView.getSettings().setJavaScriptEnabled(true);
        photoWebView.loadUrl("http://www.drivehype.com/PhotoAlbumMobileView.html");
        CookieManager cookieManager = CookieManager.getInstance();

        String cookieString = "uid="+ MainActivity.uid;
        Log.d("photoPage", "uid " + MainActivity.uid);
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, cookieString);

        photoWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //Required functionality here
                return super.onJsAlert(view, url, message, result);
            }
        });
        return photoAlbumWebView;
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
