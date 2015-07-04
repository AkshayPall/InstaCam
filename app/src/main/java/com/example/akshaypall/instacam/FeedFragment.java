package com.example.akshaypall.instacam;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {


    private List<Photo> mPhotosList;
    private FeedAdapter mAdapter;

    public FeedFragment() {
        mPhotosList = new ArrayList<Photo>();
        mAdapter = new FeedAdapter(getActivity(), mPhotosList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView feedRecyclerView = (RecyclerView)v.findViewById(R.id.feed_recycler_view);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        feedRecyclerView.setAdapter(mAdapter);


        return v;
    }

    public void addPhoto(Photo photo) {
        //Pre: take the new photo taken from NewPhotoActivity (which was sent to MainActivity)
        //Post: add it to the list of photos and update the adapter, so that the photo shows up with its caption
        mPhotosList.add(0, photo);
        mAdapter.notifyDataSetChanged();
    }

}
