package com.drivehype.www.drivehype.NavDrawerFragments;

import android.app.Activity;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.drivehype.www.drivehype.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllMediaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllMediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllMediaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    String url = "http://www.drivehype.com/allMedia.html";
    String oneText = "";
    String selectedText = "";
    String albumID = "";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AllMediaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllMediaFragment newInstance() {
        AllMediaFragment fragment = new AllMediaFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AllMediaFragment() {
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
        View allMediaWebView = inflater.inflate(R.layout.fragment_allmedia, container, false);
        WebView mediaWebView = (WebView) allMediaWebView.findViewById(R.id.mediawebview);
        mediaWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                //String cookies = CookieManager.getInstance().getCookie(url);
                //android.util.Log.d(this.getClass().getSimpleName(), "Cookies: " + cookies);
                android.util.Log.d(this.getClass().getSimpleName()," " + getCookie(url, "oneText"));
                android.util.Log.d(this.getClass().getSimpleName()," " + getCookie(url, "selectedText"));
                android.util.Log.d(this.getClass().getSimpleName()," " + getCookie(url, "albumID"));
            }
        });
        mediaWebView.getSettings().setJavaScriptEnabled(true);
        mediaWebView.loadUrl("http://www.drivehype.com/allMedia.html");
        CookieManager.getInstance().setAcceptCookie(true);

        mediaWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String cookies = CookieManager.getInstance().getCookie(url);
                    android.util.Log.d(this.getClass().getSimpleName(), "Cookies: " + cookies);
                    oneText = getCookie(url, "oneText");
                    android.util.Log.d(this.getClass().getSimpleName(), "oneText " + oneText);
                    selectedText = getCookie(url, "selectedText");
                    android.util.Log.d(this.getClass().getSimpleName(), "selectedText " + selectedText);
                    albumID = getCookie(url, "albumID");
                    android.util.Log.d(this.getClass().getSimpleName(), "albumID " + albumID);

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    post.setHeader("Content-type", "application/json");
                    post.setHeader("Accept", "application/json");
                    JSONObject obj = new JSONObject();

                    try {
                        obj.put("oneText", oneText);
                        obj.put("selectedText", selectedText);
                        obj.put("albumID", albumID);
                        post.setEntity(new StringEntity(obj.toString(), "UTF-8"));
                        HttpResponse response = client.execute(post);
                        String respStr = EntityUtils.toString(response.getEntity());
                    }catch(JSONException jexc){
                        android.util.Log.d(this.getClass().getSimpleName(), jexc.toString());
                    }catch(IOException ioexc){
                        android.util.Log.d(this.getClass().getSimpleName(), ioexc.toString());
                    }

                //String query = "document.cookie=oneText=" + getCookie(url, "oneText") +
                //        "document.cookie=selectedText=" + getCookie(url, "selectedText") +
                //        "document.cookie=albumID=" + getCookie(url, "albumID");
                //return false;
            }
        });

        return allMediaWebView;

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

    public String getCookie(String url, String cookieName){
        String cookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(url);
        String[] temp = cookies.split(";");
        for (String ar1 : temp){
            if(ar1.contains(cookieName)){
                String[] temp1=ar1.split("=");
                cookieValue = temp1[1];
            }
        }
        return cookieValue;
    }

    private void makePostRequest() {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://www.drivehype.com/allMedia.html#");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("oneText", getCookie(url, "oneText")));
        pairs.add(new BasicNameValuePair("selectedText", getCookie(url, "selectedText")));
        pairs.add(new BasicNameValuePair("albumID", getCookie(url, "albumID")));
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
        } catch (UnsupportedEncodingException uee) {
            android.util.Log.d(this.getClass().getSimpleName(), uee.toString());
        }
        try {
            HttpResponse response = client.execute(post);
        } catch (ClientProtocolException cpe) {
            cpe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /*public void postData(){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("oneText", getCookie(url, "oneText")));
            nameValuePairs.add(new BasicNameValuePair("selectedText", getCookie(url, "selectedText")));
            nameValuePairs.add(new BasicNameValuePair("albumID", getCookie(url, "albumID")));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);
        } catch (ClientProtocolException cpe) {
            android.util.Log.d(this.getClass().getSimpleName(), cpe.toString());
        } catch (IOException ioe) {
            android.util.Log.d(this.getClass().getSimpleName(), ioe.toString());
        }
    }*/


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
