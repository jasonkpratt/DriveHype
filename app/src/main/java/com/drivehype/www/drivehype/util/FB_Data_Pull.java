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

    public static void makeAlbumRequest( ) {

        /* make the API call */
        new Request(
                session,
                "/me/albums",
                null,
                HttpMethod.GET,
                new Request.Callback() {


                    public void onCompleted(Response response) {
                        Log.d("myuser", "Response albums" + response.toString());
                        try {

                            counter=0;
                            JSONObject innerJson = response.getGraphObject().getInnerJSONObject();
                            JSONArray data = innerJson.getJSONArray("data");
                            Log.d("myindex", "index count" + data.length());
                            albumList=new String [data.length()];
                            albumImageUri=new String [data.length()];
                            albumImageSource=new String [data.length()];
                            for ( index=0; index<data.length();index++) {

                                oneAlbum = data.getJSONObject(index);
                                albumList[index]=oneAlbum.getString("name"); // this will return you the album's name.
                                final String coverPhoto=oneAlbum.getString("cover_photo");

                                //setting default album
                                if(index==0) {
                                    setAlbum(oneAlbum.getString("id"));
                                    myFrag.setAlbumTitle(albumList[0]);
                                    albumTitle=albumList[0];
                                }
                                    makePhotoRequest(albumID);


                                Log.d("albums Response", "albums response innerjson"+coverPhoto);
                                Log.d("array", "albums response jsonArray" + oneAlbum.toString());

                                        /* make the API call */

                                new Request(
                                        session,
                                        "/"+ coverPhoto,
                                        null,
                                        HttpMethod.GET,
                                        new Request.Callback() {
                                            public void onCompleted(Response response2) {
                                                Log.d("myindex", "index count pictures" + counter);

                                                JSONObject innerJson2 = response2.getGraphObject().getInnerJSONObject();
                                                try{
                                                    albumImageUri[counter]=innerJson2.getString("picture") ;
                                                    if(counter==0){
                                                        Log.d("mypic", "image url" + albumImageUri[0]);
                                                        myFrag.setAlbumPicture(albumImageUri[0]);
                                                    }



                                                }

                                                catch (JSONException e) { }
                                                //needed a separate counter for each async methods

                                                try{
                                                    albumImageSource[counter]=innerJson2.getString("source") ;

                                                }
                                                catch (JSONException e) { }

                                                counter++;

                                            }
                                        }
                                ).executeAsync();



                            }

                            //mListener.onFragmentInteraction(albumList, albumImageUri,albumImageSource);


                        }
                        catch (JSONException e) { }


            /* handle the result */

                    }
                }
        ).executeAsync();

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

    public  static String [] getAlbumList(){
        return albumList;

    }

    public  static String [] getAlbumCover(){
        return albumImageUri;

    }


    public static String [] getAlbumImageSource(){
        return albumImageSource;

    }

    public static GraphUser getUser(){
        return user;
    }

    public static void setAlbum(String id){
        albumID=id;


    }





}
