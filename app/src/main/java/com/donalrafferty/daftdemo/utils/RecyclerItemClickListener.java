package com.donalrafferty.daftdemo.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerItemClickListener
 * A custom class used to detect the clicks on the items in the RecyclerView
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener; //Reference for OnItemClickListener

    //Interface for the item clicks
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector; //Need a gesture detector to registers clicks

    /**
     * RecyclerItemClickListener
     * Constructor that inits the listener and the gesture detector
     * @param context
     * @param listener
     */
    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) { //Listen for a single tap
                return true;
            }
        });
    }

    /**
     * onInterceptTouchEvent
     * Method that intercepts the touch event so we can detect the click on a property item in
     * the Recycler view
     * @param view
     * @param e
     * @return
     */
    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY()); //Reference the childView of the touch event
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView)); //Send the data via the listener interface
            return true;
        }
        return false;
    }

    //Not needed
    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    //Not needed
    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}