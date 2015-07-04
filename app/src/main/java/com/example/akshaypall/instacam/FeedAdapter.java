package com.example.akshaypall.instacam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Akshay Pall on 30/06/2015.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mUserAvatar;
        private final TextView mUserName;
        private final ImageView mPhoto;
        private final TextView mCaption;

        public ViewHolder(View itemView) {
            super(itemView);
            //Grabbing the various parts of a feed item, in the order in which they appear
            mUserAvatar = (ImageView)itemView.findViewById(R.id.feed_item_user_avatar);
            mUserName = (TextView)itemView.findViewById(R.id.feed_item_user_name);
            mPhoto = (ImageView)itemView.findViewById(R.id.feed_item_photo);
            mCaption = (TextView)itemView.findViewById(R.id.feed_item_caption);
        }
    }

    private List<Photo> mPhotoList;
    private Context mContext;

    public FeedAdapter(Context context, List<Photo> photos) {
        mPhotoList = photos;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //Pre: take new photo added
        //Post: bind it's data to the elements shown in the UI (or what's initialized in the viewholder constructor)
        Photo photo = mPhotoList.get(i);
        Picasso.with(mContext).load(photo.getFile()).into(viewHolder.mPhoto);
        Picasso.with(mContext).load(photo.getUser().getImageURL()).into(viewHolder.mUserAvatar);
        viewHolder.mUserName.setText(photo.getUser().getFirstName()+" "+photo.getUser().getLastName());
        viewHolder.mCaption.setText(photo.getCaption());
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);

        return new ViewHolder(v);
    }
}
