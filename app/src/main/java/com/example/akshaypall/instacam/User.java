package com.example.akshaypall.instacam;

import com.facebook.model.GraphObject;

import java.io.Serializable;

/**
 * Created by Akshay Pall on 04/07/2015.
 */
public class User implements Serializable{
    private String mBirthday;
    private String mFirstName;
    private String mLastName;
    private String mImageURL;

    private static User sCurrentUser;

    public static User getCurrentUser() {
        return sCurrentUser;
    }

    public static void setCurrentUser(GraphObject user) {
        if (sCurrentUser == null) {
            sCurrentUser = new User(user);
        }
    }

    User(GraphObject user){
        mFirstName= user.getProperty("first_name").toString();
        mLastName= user.getProperty("last_name").toString();
        mImageURL = user.getPropertyAs("picture", GraphObject.class)
                .getPropertyAs("data", GraphObject.class)
                .getProperty("url").toString();
        mBirthday = user.getProperty("birthday").toString();
    }

    public String getBirthday() {
        return mBirthday;
        //FORMAT: is MM/DD/YYYY
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getImageURL() {
        return mImageURL;
    }
}
