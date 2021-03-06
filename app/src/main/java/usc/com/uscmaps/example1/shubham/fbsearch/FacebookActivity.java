//package usc.com.uscmaps.example1.shubham.fbsearch;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by Shubham on 4/13/17.
// */
//
//public class FacebookActivity extends AppCompatActivity {
//    private String TAG = getClass().getSimpleName();
//
//    private CallbackManager callbackManager;
//    private LoginManager manager;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Log.e(TAG, "onCreate: Facebook Acitvity" );
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        LoginManager.getInstance().logOut();
//
//
//        callbackManager = CallbackManager.Factory.create();
//
//        List<String> permissionNeeds = Arrays.asList("publish_actions");
////        List<String> permissionNeeds = Arrays.asList("publish_actions", "user_posts", "user_photos");
//
//
//
//
//
//
//        //this loginManager helps you eliminate adding a LoginButton to your UI
//        manager = LoginManager.getInstance();
//
//        manager.logInWithPublishPermissions(this, permissionNeeds);
//        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        Log.e(TAG, "SharingActivity access token: "+ accessToken);
//
//        manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                final AccessToken accessToken = loginResult.getAccessToken();
//                Log.e(TAG, "SharingActivity access token: "+ accessToken);
//                Toast.makeText(getApplicationContext(), "Facebook Logged In!", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//            @Override
//            public void onCancel() {
//                Log.e(TAG, "onCancel: " );
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                Log.e(TAG, "onError: " );
//            }
//        });
//    }
//
//    /**
//     * Final callback that posts the shareDialog in Facebook
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
//        Log.d(TAG, "onActivityResult: "+requestCode+ " : "+ responseCode + " :  "+ data);
//
//        super.onActivityResult(requestCode, responseCode, data);
//        callbackManager.onActivityResult(requestCode, responseCode, data);
//    }
//}
