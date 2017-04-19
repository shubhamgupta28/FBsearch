package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shubham on 4/11/17.
 * <p>
 * Change package name in the Key Hash try blocks
 * http://stackoverflow.com/questions/23674131/android-facebook-integration-invalid-key-hash
 * Tutorial : http://simpledeveloper.com/how-to-share-an-image-on-facebook-in-android/
 */

public class SharingActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    private CallbackManager callbackManager;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

//            Try Catch to determine the Key Hash for input in Facebook App IDs
//            try {
//                PackageInfo info = getPackageManager().getPackageInfo(
//                        "usc.com.uscmaps.example1.shubham.fbsearch",
//                        PackageManager.GET_SIGNATURES);
//                for (Signature signature : info.signatures) {
//                    MessageDigest md = MessageDigest.getInstance("SHA");
//                    md.update(signature.toByteArray());
//                    Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                }
//            } catch (PackageManager.NameNotFoundException e) {
//                Log.e("Catch", "Catch0");
//
//
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("Catch", "Catch1");
//            }


            FacebookSdk.sdkInitialize(getApplicationContext());

            if (BuildConfig.DEBUG) {
                FacebookSdk.setIsDebugEnabled(true);
                FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
            }

            callbackManager = CallbackManager.Factory.create();

            List<String> permissionNeeds = Arrays.asList("publish_actions");

            //this loginManager helps you eliminate adding a LoginButton to your UI
            manager = LoginManager.getInstance();

            manager.logInWithPublishPermissions(this, permissionNeeds);

            manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    final AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    Log.e(TAG, "SharingActivity access token: "+ accessToken);

                    sharePhotoToFacebook();
                    Toast.makeText(getApplicationContext(), "You shared this post.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onCancel() {
                    System.out.println("onCancel");
                }

                @Override
                public void onError(FacebookException exception) {
                    System.out.println("onError");
                }
            });
        }
    }

    private void sharePhotoToFacebook() {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);
    }

    /**
     * Final callback that posts the shareDialog in Facebook
     */
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        Log.d(TAG, "onActivityResult: "+requestCode+ " : "+ responseCode + " :  "+ data);

        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
