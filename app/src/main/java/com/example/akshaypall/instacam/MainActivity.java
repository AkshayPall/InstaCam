package com.example.akshaypall.instacam;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener{

    private static final int NEW_PHOTO_REQUEST = 10;
    private static final String TAG = "TAG";
    private FeedFragment mFeedFragment;
    private ProfileFragment mProfileFragment;
    private MaterialTabHost mTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabBar = (MaterialTabHost)findViewById(R.id.tab_bar);
        //Image Feed tab!
        mTabBar.addTab(mTabBar.newTab()
                        .setIcon(getResources().getDrawable(R.drawable.ic_home_feed))
                        .setTabListener(this)
        );
        //Profile tab!
        mTabBar.addTab(mTabBar.newTab()
                        .setIcon(getResources().getDrawable(R.drawable.ic_profile))
                        .setTabListener(this)
        );

        mFeedFragment = (FeedFragment)getFragmentManager().findFragmentById(R.id.feed_container);
        if (mFeedFragment == null) {
            mFeedFragment = new FeedFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.feed_container, mFeedFragment)
                    .commit();
        }

        //Getting the Camera FAB to take a photo
        ImageButton fabCamera= (ImageButton)findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Explicit intent to go to NewPhotoActivity
                Intent i = new Intent(MainActivity.this, NewPhotoActivity.class);
                startActivityForResult(i, NEW_PHOTO_REQUEST);
            }
        });
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        //TODO: Change current fragment shown to new fragment based on what tab was pressed
        int position = materialTab.getPosition();
        mTabBar.setSelectedNavigationItem(position);
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFeedFragment;
                break;
            case 1:
                if (mProfileFragment == null) mProfileFragment = new ProfileFragment();
                fragment = mProfileFragment;
                break;
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.feed_container, fragment)
                .commit();
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {
        //NOTHING TO DO!
    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {
        //NOTHING TO DO!
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                //receive photo from new photo activity
                Photo newPhoto = (Photo)data.getSerializableExtra(NewPhotoActivity.NEW_PHOTO);
                //send this photo to the feed fragment, which will add it to its photo ArrayList and update/notify the adapter
                mFeedFragment.addPhoto(newPhoto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
