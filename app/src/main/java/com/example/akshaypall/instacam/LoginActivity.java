package com.example.akshaypall.instacam;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public class LoginActivity extends ActionBarActivity {
    private UiLifecycleHelper mUiHelper;
    private static final String SESSION_TAG = "SESSION_STATE_CHANGE";
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        generateAndLogKeyHash();

        LoginButton fbLoginButton= (LoginButton)findViewById(R.id.fb_loginbutton);
        fbLoginButton.setReadPermissions("user_birthday");

        mUiHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState sessionState, Exception e) {
                onSessionStateChanged(session, sessionState, e);
            }
        });

        mUiHelper.onCreate(savedInstanceState);
    }

    private void generateAndLogKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.akshaypall.instacam",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void onSessionStateChanged (final Session session, SessionState sessionState, Exception e){
        if (sessionState.isOpened()) {
            List<String> permissions = Session.getActiveSession().getPermissions();
            boolean hasPermission = false;
            for (String indivPermission:permissions){
                if (indivPermission.equals("user_birthday")){
                    hasPermission = true;
                }
            }
            if (!hasPermission){
                Session.NewPermissionsRequest permissionsRequest = new Session.NewPermissionsRequest(this, "user_birthday");
                session.requestNewReadPermissions(permissionsRequest);
                return;
            }
            Bundle parameters = new Bundle();
            parameters.putString("fields", "picture,first_name,last_name,birthday");
            Request request = new Request(session, "/me", parameters, HttpMethod.GET, new Request.Callback() {
                @Override
                public void onCompleted(Response response) {
                    if (session == Session.getActiveSession()){
                        if (response.getGraphObject() != null) {
                            User.setCurrentUser(response.getGraphObject());
                            Log.d(TAG, User.getCurrentUser().getFirstName());
                            Log.d(TAG, User.getCurrentUser().getLastName());
                            Log.d(TAG, User.getCurrentUser().getBirthday());
                            Log.d(TAG, User.getCurrentUser().getImageURL());
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                    if (response.getError() != null){
                        Log.d(TAG, "Error is: "+response.getError());
                    }
                }
            });
            request.executeAsync();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUiHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUiHelper.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUiHelper.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUiHelper.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
