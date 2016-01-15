package com.donalrafferty.daftdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.toolbox.NetworkImageView;
import com.donalrafferty.daftdemo.MainActivity;
import com.donalrafferty.daftdemo.R;
import com.donalrafferty.daftdemo.activities.DetailActivity;
import com.donalrafferty.daftdemo.adapters.PropertyItemAdapter;
import com.donalrafferty.daftdemo.objects.Ad;

import java.util.ArrayList;

/**
 * DaftMainUIHelper
 * This is a helper class for setting up and controlling the UI on the MainActivity
 */
public class DaftMainUIHelper {

    //Reference all required classes
    private RecyclerView mRecyclerView; //For showing the propeties in a List
    private RecyclerView.Adapter mAdapter; //For controlling the data in the List
    private RecyclerView.LayoutManager mLayoutManager; //Layout manager for the List
    private ArrayList<Ad> adList; //Array List for holding the list of properties
    private Context mContext; //Context for referencing elements
    private ProgressBar progressBar; //Simple progress bar for loading

    /**
     * DaftMainUIHelper
     * Simple constructor which inits the Contect
     * and an empty Array List for the properties
     * @param context
     */
    public DaftMainUIHelper(Context context){

        mContext = context;
        adList = new ArrayList<>();

    }

    /**
     * setupViews
     * Method that sets up the views for the Main Activity UI
     * @param activity
     */
    public void setupViews(MainActivity activity){
        mRecyclerView = (RecyclerView) activity.findViewById(R.id.daft_recycler_view); //Ref the Recycler view from XML
        progressBar = (ProgressBar) activity.findViewById(R.id.progressBar); //Ref the progressbar view from XML
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext); //Create a linear layout manager for the list view
        mRecyclerView.setLayoutManager(mLayoutManager); //Set the layout manager

        //Need to add a custom click listner here to be able to detect clicks on items on the RecyclerView
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) { //User has click on a property in the list
                        Ad ad = adList.get(position); //Grab the Ad from the Array based on the position
                        Intent detailIntent = new Intent(mContext, DetailActivity.class); //Create an intent to open the detail acitvity
                        detailIntent.putExtra(DaftConstants.PARCELABLE_AD_KEY, ad); //Pass the detail activity the select Property
                        detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Need this flag to open from non Activity class
                        startActivityWithAnimation(view, detailIntent); //Call the method to start the detail activity with an animation
                    }
                })
        );
        mAdapter = new PropertyItemAdapter(adList, mContext); //Init the Adapter for controlling the data in the list, will have empty data here
        mRecyclerView.setAdapter(mAdapter); //Set the dapter for the RectclerView
    }

    /**
     * populateAdapter
     * Method that takes in an updated Array of properties and notfies the adapter that the data has changed
     * @param daftAds
     */
    public void populateAdapter(ArrayList<Ad> daftAds){
        adList.clear(); //Empty the storing Array list
        adList.addAll(daftAds); //Add the new data to the storing Array list
        mAdapter.notifyDataSetChanged(); //Let the adapter know the data has changed and to reload the list
    }

    /**
     * startStopRefresh
     * A Helper method for hiding and showing the loading spinner
     * @param refreshOn
     */
    public void startStopRefresh(boolean refreshOn){
        if(refreshOn) //Show the spinner
            progressBar.setVisibility(View.VISIBLE);
        else //Hide the spinner
            progressBar.setVisibility(View.GONE);
    }

    /**
     * startActivityWithAnimation
     * A Method to start the detail activity using an animation between
     * the property image in the list and the same image that exists in
     * the UI of the Detail Activity
     * @param view
     * @param intent
     */
    private void startActivityWithAnimation(View view, Intent intent){

        NetworkImageView animImage = (NetworkImageView) view.findViewById(R.id.card_thumbnail_image); //Reference the Image where the animation starts
        String transitionName = mContext.getString(R.string.activity_transition_daft); //Grab the name of the transitinos from the Strings
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)mContext,
                        animImage,   // The view which starts the transition
                        transitionName    // The transitionName of the view we’re transitioning to
                );
        ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle()); //Start the activity
    }

}
