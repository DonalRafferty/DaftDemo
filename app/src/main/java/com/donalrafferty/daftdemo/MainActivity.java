package com.donalrafferty.daftdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.donalrafferty.daftdemo.interfaces.RequestCallback;
import com.donalrafferty.daftdemo.network.RequestError;
import com.donalrafferty.daftdemo.network.RestClient;
import com.donalrafferty.daftdemo.objects.Ad;
import com.donalrafferty.daftdemo.utils.DaftConstants;
import com.donalrafferty.daftdemo.utils.DaftMainUIHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * MainActivity
 * Handles entry point to the application where the list of properties are displayed
 * It implements the RequestCallback interface for observing when data is received from the Rest Client
 */
public class MainActivity extends AppCompatActivity implements RequestCallback {

    DaftMainUIHelper daftMainUiHelper; //A Helper class for manipulating the UI
    private ArrayList<Ad> mAds; //Array List to hold the list of returned properties

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daftMainUiHelper = new DaftMainUIHelper(this); //Init the UI Helper
        daftMainUiHelper.setupViews(this); //Call the helper function to setup UI, send activity to reference views
        mAds = new ArrayList<>(); //Init an empty array list
        daftMainUiHelper.populateAdapter(mAds); //Call the helpers method to init the adapter with empty data

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            daftMainUiHelper.startStopRefresh(false); //UI Helpers method for showing/hiding the loading spinner
            mAds = savedInstanceState.getParcelableArrayList(DaftConstants.SAVE_AD_ARRAY_KEY); //Retrieve the stored list of properties
            daftMainUiHelper.populateAdapter(mAds); //Call the helpers method to init the adapter with retrieved data
        } else {
            sendDaftRequest(); //New instance so call the method to fetch the properties via the REST API
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current list of properies
        savedInstanceState.putParcelableArrayList(DaftConstants.SAVE_AD_ARRAY_KEY, mAds);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * onCompleted
     * Observable interface that gets notified when the properties have been retrieved by
     * the REST client
     * @param response
     * @param error
     */
    @Override
    public void onCompleted(JSONObject response, RequestError error) {

        daftMainUiHelper.startStopRefresh(false); //Stop the loading spinner

        if(response != null){
            try {
                Gson gson = new Gson(); //Init GSON lib for parsing JSON to Java Objects
                //Grab the ads value from the JSON return
                JSONArray adsArray = response.getJSONObject(DaftConstants.JSON_RESULT).getJSONObject(DaftConstants.JSON_RESULTS).getJSONArray(DaftConstants.JSON_ADS);
                mAds = gson.fromJson(adsArray.toString(), new TypeToken<ArrayList<Ad>>() {}.getType()); //Parse the JSON array using the Ad Object
                daftMainUiHelper.populateAdapter(mAds); //Call the helpers method to init the adapter with retrieved data

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sendDaftRequest
     * Private function for uusing the RestClient to send a GET HTTP request to retrieve the properties from the
     * Daft API.
     */
    private void sendDaftRequest(){

        daftMainUiHelper.startStopRefresh(true); //Set the loading spinner to show

        //Call the Rest Client to create a generic GET request built with the Strings required
        try {
            RestClient.genericGet(this, getString(R.string.url_host)
                    + URLEncoder.encode(getString(R.string.url_host_encoded), DaftConstants.UTF8_ENCODE), this, DaftConstants.REST_GET_ADS_TAG);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
