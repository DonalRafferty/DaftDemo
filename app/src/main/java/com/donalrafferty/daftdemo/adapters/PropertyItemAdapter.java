package com.donalrafferty.daftdemo.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.donalrafferty.daftdemo.R;
import com.donalrafferty.daftdemo.network.VolleySingleton;
import com.donalrafferty.daftdemo.objects.Ad;
import com.donalrafferty.daftdemo.utils.DaftBERRatingHelper;

import java.util.List;

/**
 * PropertyItemAdapter
 * Adapter class for controlling the data in the RecyclerView
 */
public class PropertyItemAdapter extends RecyclerView.Adapter<PropertyItemAdapter.ViewHolder>{
    private List<Ad> mDataset; //For holding the data
    ImageLoader mImageLoader; //For lazy loading the images
    static DaftBERRatingHelper daftBERRatingHelper; //For selecting the BER rating image
    Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item
        public CardView cardView;
        public NetworkImageView mImageView;
        public TextView mTvPrice;
        public TextView mTvAddress;
        public TextView mTvBeds;
        public TextView mTvBaths;
        public ImageView mIvBer;
        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            mImageView = (NetworkImageView) v.findViewById(R.id.card_thumbnail_image);
            mImageView.setDefaultImageResId(R.drawable.house_placeholder);
            mTvPrice = (TextView) v.findViewById(R.id.info_price);
            mTvAddress = (TextView) v.findViewById(R.id.info_address);
            mTvBeds = (TextView) v.findViewById(R.id.text_bed);
            mTvBaths = (TextView) v.findViewById(R.id.text_bath);
            mIvBer = (ImageView) v.findViewById(R.id.image_ber);
        }
    }

    // DataSet constructor (
    public PropertyItemAdapter(List<Ad> daftDataset, Context context) {
        mDataset = daftDataset; //Get the dta set
        mImageLoader = VolleySingleton.getInstance(context).getImageLoader(); //Get the image loader for lazy loading the images
        daftBERRatingHelper = new DaftBERRatingHelper(context); //Get the helper class for selecting the BER image
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PropertyItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daft_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mImageView.setImageUrl(mDataset.get(position).getLarge_thumbnail_url(), mImageLoader); //Set the image
        holder.mTvPrice.setText(mContext.getString(R.string.euro) + String.format("%1$,.0f", mDataset.get(position).getPrice())); //Set the price
        holder.mTvAddress.setText(mDataset.get(position).getFull_address()); //Set the address
        holder.mTvBeds.setText(String.valueOf(mDataset.get(position).getBedrooms())); //Set the number of bedrooms
        holder.mTvBaths.setText(String.valueOf(mDataset.get(position).getBathrooms())); //Set the number of bathrooms
        holder.mIvBer.setImageDrawable(daftBERRatingHelper.selectBerRating(mDataset.get(position).getBer_rating())); //Set the BER image

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
