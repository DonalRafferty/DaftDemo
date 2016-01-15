package com.donalrafferty.daftdemo.interfaces;

import com.donalrafferty.daftdemo.network.RequestError;

import org.json.JSONObject;

/**
 * RequestCallback
 * Basic interface for creating an observable for when data is retrieved via REST calls
 * @param <T>
 */
public interface RequestCallback<T> {
    public void onCompleted(JSONObject response, RequestError error);
}
