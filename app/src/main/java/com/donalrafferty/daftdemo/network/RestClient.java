package com.donalrafferty.daftdemo.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.donalrafferty.daftdemo.interfaces.RequestCallback;

import org.json.JSONObject;

/**
 * RestClient
 * Helper class for sending the HTTP Requests via Google Volley Library
 */
public class RestClient {

    /**
     * Helper function for performing a Generic HTTP Get JSON request
     * @param context
     * @param REQUEST_TAG
     */
    public static void genericGet(Context context, String url, final RequestCallback callback, String REQUEST_TAG) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Successful response so send data via the observable
                        callback.onCompleted(response, null);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Error occurred, should send error here so error handling can be implemented
                        callback.onCompleted(null, null);
                    }
                });
        jsObjRequest.setTag(REQUEST_TAG); //Set a tag for this request
        VolleySingleton.getInstance(context).addToRequestQueue(jsObjRequest); //Add the request to the Volley Queue
    }

}
