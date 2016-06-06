package br.com.adocaobrasil.adocaobrasil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize sdk FB
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        //AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);

        // Permission for email user
        loginButton.setReadPermissions(Arrays.asList("email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println("sucess: User ID: " + loginResult.getAccessToken().getUserId() + " --- " + "Auth Token: " + loginResult.getAccessToken().getToken());

                //final Profile profile = Profile.getCurrentProfile();
                //System.out.println("*sucess: Profile: " + profile);


                boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
                Profile profile = Profile.getCurrentProfile();
                if (profile == null) {
                    Log.e("Profile___", "null");
                }
                if (enableButtons && profile != null) {
                    Log.e("Access Token", AccessToken.getCurrentAccessToken().toString());
                    Log.e("TabSocial", profile.getName());
                }
            }

            @Override
            public void onCancel() {
                // App code
                System.out.println("cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                System.out.println("error");
            }

        });
/*
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();
        if (profile == null) {
            Log.e("Profile", "null");
        }
        if (enableButtons && profile != null) {
            Log.e("Access Token", AccessToken.getCurrentAccessToken().toString());
            Log.e("TabSocial", profile.getName());
        }
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

