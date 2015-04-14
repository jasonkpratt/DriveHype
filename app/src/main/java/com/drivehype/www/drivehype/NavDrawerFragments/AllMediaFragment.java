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

                    postData();

                    /*
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://www.drivehype.com/rest/rest.php/allMedia");
                    post.setHeader("Content-type", "application/json");
                    post.setHeader("Accept", "application/json");
                    JSONObject obj = new JSONObject();

                    try {
                        //obj.put("oneText", oneText);
                        //obj.put("selectedText", selectedText);
                        obj.put("id", albumID);
                        post.setEntity(new StringEntity(obj.toString(), "UTF-8"));
                        HttpResponse response = client.execute(post);
                        String respStr = EntityUtils.toString(response.getEntity());
                        android.util.Log.d(this.getClass().getSimpleName(), "respStr: " + respStr);
                    }catch(JSONException jexc){
                        android.util.Log.d(this.getClass().getSimpleName(), jexc.toString());
                    }catch(IOException ioexc){
                        android.util.Log.d(this.getClass().getSimpleName(), ioexc.toString());
                    }*/
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

    public void postData(){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.drivehype.com/rest/rest.php/elliott");
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            //nameValuePairs.add(new BasicNameValuePair("oneText", oneText));
            //nameValuePairs.add(new BasicNameValuePair("selectedText", selectedText));
            nameValuePairs.add(new BasicNameValuePair("id", "helloelliott"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);
            android.util.Log.d(this.getClass().getSimpleName(), "response: " + response);
        } catch (ClientProtocolException cpe) {
            android.util.Log.d(this.getClass().getSimpleName(), cpe.toString());
        } catch (IOException ioe) {
            android.util.Log.d(this.getClass().getSimpleName(), ioe.toString());
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}
