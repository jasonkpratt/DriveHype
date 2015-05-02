package com.drivehype.www.drivehype.NavDrawerFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.drivehype.www.drivehype.R;
import com.drivehype.www.drivehype.ui.MainActivity;

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
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Gravity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllMediaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllMediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllMediaFragment extends Fragment {

private static final String DEBUG_TAG = "Gestures";
private GestureDetectorCompat mDetector;


    private OnFragmentInteractionListener mListener;
    String url = "http://www.drivehype.com/allMedia.html";
    String oneText1="";
    String selectedText = "5";
    String albumID = "";
    String userID = "";
    String oneText="0";

    CookieManager cookieManager;
    private String[] arraySpinner;
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

        // Set the gesture detector as the double tap
        // listener
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View allMediaWebView = inflater.inflate(R.layout.fragment_allmedia, container, false);
        WebView mediaWebView = (WebView) allMediaWebView.findViewById(R.id.mediawebview);

        this.arraySpinner = new String[]{
                "", "", "", "", ""
        };


       // Spinner s = (Spinner) allMediaWebView.findViewById(R.id.text_spinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        //s.setAdapter(adapter);
        //s.setOnItemSelectedListener(
          //      new OnItemSelectedListener() {
            //        public void onItemSelected(
              //              AdapterView<?> parent, View view, int position, long id) {
                //        Log.d("touch", "selected text " + position);
                  //      selectedText = String.valueOf(position);
                    //    Log.d("touch", "selected " + selectedText);

                    //}

                    //public void onNothingSelected(AdapterView<?> parent) {
                      //  showToast("Spinner1: unselected");
                    //}
               // });

        final Button pushbutton = (Button) allMediaWebView.findViewById(R.id.push_btn);
        pushbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                oneText1 = getCookie(url, "oneText");
                String text0=getCookie(url, "text0");
                String text1=getCookie(url, "text1");
                String text2=getCookie(url, "text2");
                String text3=getCookie(url, "text3");
                String text4=getCookie(url, "text4");
                if (text0.equals("none")){
                    arraySpinner[0]="";}
                else{
                    arraySpinner[0]=text0;
                }

                if (text1.equals("none")){
                    arraySpinner[1]="";}
                else{
                    arraySpinner[1]=text1;
                }

                if (text2.equals("none")){
                    arraySpinner[2]="";}
                else{
                    arraySpinner[2]=text2;
                }

                if (text3.equals("none")){
                    arraySpinner[3]="";}
                else{
                    arraySpinner[3]=text3;
                }

                if (text4.equals("none")){
                    arraySpinner[4]="";}
                else{
                    arraySpinner[4]=text4;
                }

                Log.d("touch", "albumId "+albumID);

                Log.d("touch", "one text "+oneText1);
                Log.d("touch", "text0 "+text0);
                Log.d("touch", "text1 "+text1);
                Log.d("touch", "text2 "+text2);
                if (oneText1.equals("true")){

                    Log.d("touch", "im inside");
                    final ArrayList mSelectedItems = new ArrayList();  // Where we track the selected items
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    // Set the dialog title
                    builder.setTitle("Pick a text message");
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    int position=0;
                    builder.setSingleChoiceItems(arraySpinner, position,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    selectedText=String.valueOf(which);
                                    Log.d("touch", "selected Text");

                                    }
                                });




                            // Set the action buttons
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK, so save the mSelectedItems results somewhere
                                    // or return them to the component that opened the dialog
                                    postData();

                                }
                            });





                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                                    }
                else{
                    postData();
                }



                // Perform action on click
            }
        });


        mediaWebView.getSettings().setJavaScriptEnabled(true);
        mediaWebView.loadUrl("http://www.drivehype.com/allMedia.html");
        cookieManager = CookieManager.getInstance();

        String cookieString = "uid=" + MainActivity.uid;
        Log.d("newpic1", "uid " + MainActivity.uid);
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, cookieString);

        return allMediaWebView;

    }



    void showToast(CharSequence msg) {


       Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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

    public void postData(){ // method for grabbing cookies and using REST calls to push album/user data

        showToast("Pushing media");

        Log.d("touch", "albumId "+albumID);


        Log.d("touch", "selected text in push"+selectedText);

        String cookies = CookieManager.getInstance().getCookie(url);
        android.util.Log.d(this.getClass().getSimpleName(), "Cookies: " + cookies);
        oneText1 = getCookie(url, "oneText");
        // android.util.Log.d(this.getClass().getSimpleName(), "selectedText " + selectedText);
        albumID = getCookie(url, "albumID");
        android.util.Log.d(this.getClass().getSimpleName(), "albumID " + albumID);
        userID = getCookie(url, "uid");


        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.drivehype.com/rest/rest.php/pushData");
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            oneText1 = getCookie(url, "oneText");
            if(oneText1.equals("true"))
                oneText="1";
            else oneText="0";

            Log.d("touch", "one text "+oneText);

            albumID = getCookie(url, "albumID");
            userID = getCookie(url, "uid");



            nameValuePairs.add(new BasicNameValuePair("oneText", oneText));
            nameValuePairs.add(new BasicNameValuePair("selectedText", selectedText));
            nameValuePairs.add(new BasicNameValuePair("id", albumID));
            nameValuePairs.add(new BasicNameValuePair("user", userID));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            android.util.Log.d(this.getClass().getSimpleName(), "setEntity");

            HttpResponse response = httpClient.execute(httpPost);
            android.util.Log.d(this.getClass().getSimpleName(), "response executed");

            InputStream is = response.getEntity().getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(20);

            int current = 0;
            while((current = bis.read()) != -1){
                baf.append((byte)current);
            }

            // Convert the Bytes read to a String.
            String text = new String(baf.toByteArray());

            android.util.Log.d(this.getClass().getSimpleName(), "response: " + text);
        } catch(ClientProtocolException cpe){
            android.util.Log.d(this.getClass().getSimpleName(), cpe.toString());
        } catch(IOException ioe){
            android.util.Log.d(this.getClass().getSimpleName(), ioe.toString());
        }
    } // end postData()

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }





}
