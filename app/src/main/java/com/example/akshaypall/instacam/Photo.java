package com.example.akshaypall.instacam;

import android.os.Environment;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Akshay Pall on 30/06/2015.
 */
public class Photo implements Serializable {
    private UUID mID;
    private static final File sDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private String mCaption;
    private User mUser;

    Photo () {
        mID = UUID.randomUUID();
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public UUID getID() {
        return mID;
    }

    public File getFile() {
        return new File(sDirectory, mID.toString()+".jpeg");
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }
}
