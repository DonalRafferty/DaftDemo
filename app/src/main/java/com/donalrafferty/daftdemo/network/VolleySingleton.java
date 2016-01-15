package com.donalrafferty.daftdemo.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * This is the template Singleton taken from the Android Developer tutorial for creating a
 * custom version of the Google Volley implementation, this uses the default template for
 * the LrubitmapCache also taken from the same tutorial. Depending on usage of Google
 * Volley we may wish to customise this class for more specific uses but for standard
 * cases this Google Volley implementation will work efficiently
 */
public class VolleySingleton {
    private static volatile VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;
    private static final int SOCKET_TIMEOUT = 30000;

    private VolleySingleton(){}

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(
                LruBitmapCache.getCacheSize(mCtx)));
    }

    public static VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VolleySingleton.class){
                if(mInstance == null){
                    mInstance = new VolleySingleton(context);
                }
            }
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {

        RetryPolicy policy = new DefaultRetryPolicy(SOCKET_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) {

            @Override
            public void retry(VolleyError error) throws VolleyError {
                if (error.networkResponse != null &&  error.networkResponse.statusCode == 401)
                {
                    throw error;
                }
                else{
                    super.retry(error);
                }
            }
        };
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}