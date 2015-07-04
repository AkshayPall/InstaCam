package com.example.akshaypall.instacam;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by Akshay Pall on 03/07/2015.
 */
public class ContractFragment<T> extends Fragment {

    private T mContract;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mContract = (T)getActivity();
        } catch (ClassCastException e) {
            throw new IllegalStateException("Activity does not implement interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContract = null;
    }

    public T getmContract() {return mContract;}
}
