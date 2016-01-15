package com.donalrafferty.daftdemo;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import com.donalrafferty.daftdemo.adapters.PropertyItemAdapter;
import com.donalrafferty.daftdemo.interfaces.RequestCallback;
import com.donalrafferty.daftdemo.network.RequestError;
import com.donalrafferty.daftdemo.network.RestClient;
import com.donalrafferty.daftdemo.objects.Ad;
import com.donalrafferty.daftdemo.utils.DaftConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> implements RequestCallback {

    private MainActivity mMainActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Ad> mAds;
    //..

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
        mRecyclerView =
                (RecyclerView) mMainActivity
                        .findViewById(R.id.daft_recycler_view);
        mLayoutManager = new LinearLayoutManager(mMainActivity.getApplicationContext());

        mAds = new ArrayList<>();

        mAdapter = new PropertyItemAdapter(mAds, mMainActivity.getApplicationContext());
    }

    public void testPreconditions() {
        assertNotNull("mMainActivity is null", mMainActivity);
        assertNotNull("mRecyclerView is null", mRecyclerView);
        assertNotNull("mLayoutManager is null", mLayoutManager);
        assertNotNull("mAds is null", mAds);
        assertNotNull("mAdapter is null", mAdapter);
        //...
    }


    public void testRecyclerView(){
        int expectedCount = 0;
        int actualCount = mRecyclerView.getAdapter().getItemCount();
        assertEquals(expectedCount, actualCount);

        try {
            RestClient.genericGet(mMainActivity.getApplicationContext(), mMainActivity.getApplicationContext().getString(R.string.url_host)
                    + URLEncoder.encode(mMainActivity.getApplicationContext().getString(R.string.url_host_encoded), DaftConstants.UTF8_ENCODE), this, DaftConstants.REST_GET_ADS_TAG);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCompleted(JSONObject response, RequestError error) {
        assertNotNull("response is null", response);
        try {
            assertNotNull("result is null", response.getJSONObject("result"));
            assertNotNull("results is null", response.getJSONObject("result").getJSONObject("results"));
            assertNotNull("ads is null", response.getJSONObject("result").getJSONObject("results").getJSONObject("ads"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //...
}
