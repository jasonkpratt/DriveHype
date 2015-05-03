package com.drivehype.www.drivehype.util;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.facebook.AccessToken;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.drivehype.www.drivehype.NavDrawerFragments.HomeFragment;



/**
 * Created by JasonPratt on 1/17/15.
 */
public class FB_Data_Pull {

    public static FB_Data_Pull FB_Data;
    public static String [] albumList;
    private static int index=0;
    private static JSONObject oneAlbum;
    public static String [] albumImageUri;
    public static String [] albumImageSource;
    public static String [] selectedAlbumPhotos;
    public static List<String> permissions=new ArrayList();
    public static String albumID;
    private static int counter=0;
    public static GraphUser user=null;
    static Session session=null;
    static HomeFragment myFrag;
    public static String albumTitle;


    private FB_Data_Pull(){


        session=Session.getActiveSession();
        session.open((AccessToken.createFromExistingAccessToken(session.getAccessToken(),session.getExpirationDate(),null,null,session.getPermissions())),null);
       permissions=session.getPermissions();
        Log.d("zerror","session:"+session.toString());
        this.makeMeRequest();
        //this.makeAlbumRequest();
    }

    public static FB_Data_Pull getInstance(HomeFragment incomingFrag )
    {
        myFrag=incomingFrag;
        if(FB_Data == null){
            FB_Data = new FB_Data_Pull();

        }
        return FB_Data;
    }

    public  static void makeMeRequest() {
        // Make an API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user2, Response response) {
                        // If the response is successful

                        Log.d("myuser", "Response user" + response.toString());
                        if (session == Session.getActiveSession()) {
                            if (user2 != null) {
                                user=user2;
                                myFrag.setPicture(user);


                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }



    public static void makePhotoRequest(String userAlbum){
        new Request(
                session,
                "/"+userAlbum+"/photos",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
            /* handle the result */
                        try {

                            JSONObject innerJson = response.getGraphObject().getInnerJSONObject();
                            JSONArray data = innerJson.getJSONArray("data");
                            selectedAlbumPhotos = new String[data.length()];
                            Log.d("urpics", "album pics" + data.toString());


                                    for(int i=0;i<data.length();i++)
                                        selectedAlbumPhotos[i]=data.getJSONObject(i).getString("picture");

                        }
                         catch (JSONException e) { }

                    }
                }
        ).executeAsync();
    }



    public static GraphUser getUser(){
        return user;
    }

    public static void setAlbum(String id){
        albumID=id;


    }





}
