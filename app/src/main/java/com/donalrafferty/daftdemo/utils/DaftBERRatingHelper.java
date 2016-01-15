package com.donalrafferty.daftdemo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.donalrafferty.daftdemo.R;

/**
 * DaftBERRatingHelper
 * A simple helper class for selecting a BER image based on value retrieved
 */
public class DaftBERRatingHelper {

    private Context mContext;

    /**
     * DaftBERRatingHelper
     * Basic constructor that just references a context
     * @param context
     */
    public DaftBERRatingHelper(Context context){
        mContext = context;
    }

    /**
     * selectBerRating
     * Helper method to pick a BER rating image based on a String value retrieved from the JSON
     * @param berRating
     * @return
     */
    public Drawable selectBerRating(String berRating){

        Drawable berDrawable = null; //Start with null

        switch(berRating){ //Switch on the String and based on that grab the correct drawable
            case "A1":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.a1);
                break;
            case "A2":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.a2);
                break;
            case "A3":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.a3);
                break;
            case "B1":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.b1);
                break;
            case "B2":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.b2);
                break;
            case "B3":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.b3);
                break;
            case "C1":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.c1);
                break;
            case "C2":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.c2);
                break;
            case "C3":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.c3);
                break;
            case "E1":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.e1);
                break;
            case "E2":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.e2);
                break;
            case "F":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.f);
                break;
            case "G":
                berDrawable = ContextCompat.getDrawable(mContext, R.drawable.g);
                break;
        }
        return berDrawable; //Return the drawable, can be NULL
    }

}
