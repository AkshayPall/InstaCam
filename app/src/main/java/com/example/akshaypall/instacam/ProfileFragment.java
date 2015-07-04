package com.example.akshaypall.instacam;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        CircleImageView mProfilePicture = (CircleImageView)v.findViewById(R.id.fragment_profile_picture);
        TextView mFirstName= (TextView)v.findViewById(R.id.first_name);
        TextView mLastName = (TextView)v.findViewById(R.id.last_name);
        TextView mBirthday = (TextView)v.findViewById(R.id.birthday);

        Picasso.with(getActivity()).load(User.getCurrentUser().getImageURL()).into(mProfilePicture);
        mFirstName.setText(User.getCurrentUser().getFirstName());
        mLastName.setText(User.getCurrentUser().getLastName());
        mBirthday.setText(User.getCurrentUser().getBirthday());
        return v;
    }


}
