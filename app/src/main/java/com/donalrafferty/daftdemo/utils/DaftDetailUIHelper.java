package com.donalrafferty.daftdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.donalrafferty.daftdemo.R;
import com.donalrafferty.daftdemo.network.VolleySingleton;
import com.donalrafferty.daftdemo.objects.Ad;

/**
 * DaftDetailUIHelper
 * A helper class for creating and manipulating the UI of the Detail Activity
 */
public class DaftDetailUIHelper implements View.OnClickListener{

    private Context mContext; //Reference for context
    private NetworkImageView propertyAdvertImage; //Top image
    private ImageLoader mImageLoader; //Loader for lazy loading the image
    private TextView mTvPrice, mTvAddress, mTvBeds, mTvBaths, mTvPropertyType, mTvDescription, mTvName; //Textviews
    private Button mBtPhone, mBtEmail; //Button views
    private ImageView berImage; //BER image
    private DaftBERRatingHelper daftBERRatingHelper; //Reference for a helper class for selecting the BER rating image
    private Ad mDaftAdvert; //Reference for holding the property data

    /**
     * DaftDetailUIHelper
     * Basic constructor that sets up context, image loader and BER image class
     * @param context
     */
    public DaftDetailUIHelper(Context context){
        mContext = context;
        mImageLoader = VolleySingleton.getInstance(mContext).getImageLoader();
        daftBERRatingHelper = new DaftBERRatingHelper(mContext);
    }

    /**
     * setupViews
     * Helper method that references all the UI elements for the Detail Activity
     * @param activity
     */
    public void setupViews(Activity activity){
        propertyAdvertImage = (NetworkImageView) activity.findViewById(R.id.image_advert);
        mTvPrice = (TextView) activity.findViewById(R.id.text_price_detail);
        mTvAddress = (TextView) activity.findViewById(R.id.text_address_detail);
        mTvBeds = (TextView) activity.findViewById(R.id.text_bed_detail);
        mTvBaths = (TextView) activity.findViewById(R.id.text_bath_detail);
        berImage = (ImageView) activity.findViewById(R.id.image_ber_detail);
        mTvPropertyType = (TextView) activity.findViewById(R.id.text_type_detail);
        mTvDescription = (TextView) activity.findViewById(R.id.text_desc_detail);
        mTvName = (TextView) activity.findViewById(R.id.text_name_detail);
        mBtPhone = (Button) activity.findViewById(R.id.button_phone_detail);
        mBtEmail = (Button) activity.findViewById(R.id.button_email_detail);
    }

    /**
     * populateViews
     * Helper method for populating the UI elements for the Detail Activity
     * @param daftAdvert
     */
    public void populateViews(Ad daftAdvert){

        mDaftAdvert = daftAdvert; //Get the property data

        if(mDaftAdvert.getLarge_thumbnail_url() != null) //If the property has an Image URL then lazy load it
            propertyAdvertImage.setImageUrl(mDaftAdvert.getLarge_thumbnail_url(), mImageLoader);
        else //Property has no image so use the default image
            propertyAdvertImage.setDefaultImageResId(R.drawable.house_placeholder);

        mTvPrice.setText(mContext.getString(R.string.euro) + String.format("%1$,.0f", mDaftAdvert.getPrice())); //Set the price with a euro symbol
        mTvAddress.setText(mDaftAdvert.getFull_address()); //Set the address
        mTvBeds.setText(String.valueOf(mDaftAdvert.getBedrooms())); //Set number of bedrooms
        mTvBaths.setText(String.valueOf(mDaftAdvert.getBathrooms())); //Set number of bathrooms
        berImage.setImageDrawable(daftBERRatingHelper.selectBerRating(mDaftAdvert.getBer_rating())); //Use the helper class to select the BER rating image
        mTvPropertyType.setText(mContext.getString(R.string.prop_type) + mDaftAdvert.getProperty_type()); //Set the property type
        mTvDescription.setText(mDaftAdvert.getDescription()); //Set the description
        mTvName.setText(mDaftAdvert.getContact_name()); //Set the contact name
        mBtPhone.setOnClickListener(this); //Set up click listener for the phone button
        mBtEmail.setOnClickListener(this); //Set up click listener for the email button
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_phone_detail: //Phone button has been clicked
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL); //Intent to start the dialer
                phoneIntent.setData(Uri.parse(DaftConstants.TEL_URI + mDaftAdvert.getPhone1())); //Grab the contact phone number
                phoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Need this as calling from non activity
                mContext.startActivity(phoneIntent); //Start the dialer with the selected number
                break;
            case R.id.button_email_detail: //Email button has been clicked
                Intent emailIntent = new Intent(Intent.ACTION_SEND); //Intent to create email components
                emailIntent.setData(Uri.parse(DaftConstants.MAILTO));
                emailIntent.setType(DaftConstants.TEXTPLAIN);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mDaftAdvert.getMain_email()}); //Grab the contacts email
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mDaftAdvert.getFull_address()); //Use the property address as the subjects
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mContext.getString(R.string.dear_greeting) + mDaftAdvert.getContact_name()); //Create a basic email body
                Intent chooser = Intent.createChooser(emailIntent, mContext.getString(R.string.send_email)); //Intent to create chooser
                chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //Need this as calling from non activity
                mContext.startActivity(chooser); //Start the chooser so user can choose email client
                break;
        }
    }
}
