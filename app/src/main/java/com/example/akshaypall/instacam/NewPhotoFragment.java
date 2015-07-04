package com.example.akshaypall.instacam;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPhotoFragment extends ContractFragment<NewPhotoFragment.Contract> {
    private static final int CAMERA_REQUEST = 10;
    public static final String NEW_PHOTO = "NEW_PHOTO";
    private static final String PHOTO_BUNDLE_EXTRA = "PHOTO";
    private Photo mPhotoTaken;
    private static final String TAG = "TAG";
    private ImageView mNewPhotoPreview;

    public NewPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_photo, container, false);
        setRetainInstance(true); //in order to allow for rotations without visibly affecting the lifecycle of the fragment
        final EditText newPhotoCaption= (EditText)v.findViewById(R.id.new_photo_caption_edittext);
        Button saveButton = (Button)v.findViewById(R.id.new_photo_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoTaken != null) {
                    mPhotoTaken.setCaption(newPhotoCaption.getText().toString());
                    mPhotoTaken.setUser(User.getCurrentUser());
                    getmContract().finishedPhoto(mPhotoTaken);
                }
            }
        });
        mNewPhotoPreview = (ImageView)v.findViewById(R.id.new_photo_imageview);

        if (mPhotoTaken == null) {
            getmContract().launchCamera();
        } else {
            loadThumbnail(mPhotoTaken);
        }
        return v;
    }

    private void loadThumbnail(Photo photo){
        //Pre: take new photo taken
        //Post: change photo preview
        Picasso.with(getActivity()).load(photo.getFile()).into(mNewPhotoPreview);

    }

    public void updatePhoto (Photo photo) {
        mPhotoTaken = photo;
        loadThumbnail(mPhotoTaken);
    }

    public interface Contract{
        public void launchCamera();
        public void finishedPhoto(Photo photo);
    }


}
