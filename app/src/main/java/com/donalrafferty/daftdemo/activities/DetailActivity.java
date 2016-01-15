package com.donalrafferty.daftdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.donalrafferty.daftdemo.R;
import com.donalrafferty.daftdemo.objects.Ad;
import com.donalrafferty.daftdemo.utils.DaftConstants;
import com.donalrafferty.daftdemo.utils.DaftDetailUIHelper;

/**
 * DetailActivity
 * Handles the detail screen of the application where a user can see in depth
 * details of the property they have selected and email or phone the contact for
 * that property
 */
public class DetailActivity extends AppCompatActivity{

    Ad daftAdvert; //Ref for advert
    DaftDetailUIHelper daftDetailUIHelper; //Ref for a helper class for creating and manipulating Detail UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Grab the advert (property) that is sent with the intent from the Main Activity
        daftAdvert = getIntent().getExtras().getParcelable(DaftConstants.PARCELABLE_AD_KEY);

        daftDetailUIHelper = new DaftDetailUIHelper(this.getApplicationContext()); //Init the helper class

        daftDetailUIHelper.setupViews(this); //Call the helper classes method to set up the UI

        daftDetailUIHelper.populateViews(daftAdvert); //Call the helper classes method to populate the UI views

    }

}
