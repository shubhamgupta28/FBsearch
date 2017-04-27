//package usc.com.uscmaps.example1.shubham.fbsearch;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.Bundle;
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
//import com.facebook.share.Sharer;
//import com.facebook.share.model.ShareLinkContent;
//import com.facebook.share.widget.ShareDialog;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by Shubham on 4/11/17.
// * <p>
// * Change package name in the Key Hash try blocks
// * http://stackoverflow.com/questions/23674131/android-facebook-integration-invalid-key-hash
// * Tutorial : http://simpledeveloper.com/how-to-share-an-image-on-facebook-in-android/
// */
//
//public class SharingActivity extends AppCompatActivity {
//    public static final String MY_PREFS_NAME = "MyPrefsFile";
//    private String TAG = getClass().getSimpleName();
//    String clicked_name;
//    String clicked_user_picture;
//
//    private CallbackManager callbackManager;
//    private LoginManager manager;
//    private ShareDialog shareDialog ;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        {
//            super.onCreate(savedInstanceState);
//
//
////            setContentView(R.layout.activity_main);
////            Try Catch to determine the Key Hash for input in Facebook App IDs
////            try {
////                PackageInfo info = getPackageManager().getPackageInfo(
////                        "usc.com.uscmaps.example1.shubham.fbsearch",
////                        PackageManager.GET_SIGNATURES);
////                for (Signature signature : info.signatures) {
////                    MessageDigest md = MessageDigest.getInstance("SHA");
////                    md.update(signature.toByteArray());
////                    Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
////                }
////            } catch (PackageManager.NameNotFoundException e) {
////                Log.e("Catch", "Catch0");
////            } catch (NoSuchAlgorithmException e) {
////                Log.e("Catch", "Catch1");
////            }
//
//            SharedPreferences sPref = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//            clicked_name = sPref.getString("clicked_user_name", "No Name");
//            clicked_user_picture = sPref.getString("clicked_user_picture", "No Name");
//
//            Log.e(TAG, "onCreate: "+ clicked_name+ ": "+clicked_user_picture);
//
//
//            FacebookSdk.sdkInitialize(getApplicationContext());
//            callbackManager = CallbackManager.Factory.create();
//
////            if (BuildConfig.DEBUG) {
////                FacebookSdk.setIsDebugEnabled(true);
////                FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
////            }
//
//
//
//            List<String> permissionNeeds = Arrays.asList("publish_actions");
//
//            //this loginManager helps you eliminate adding a LoginButton to your UI
//            manager = LoginManager.getInstance();
//
//            manager.logInWithPublishPermissions(this, permissionNeeds);
//
//            shareDialog = new ShareDialog(this);
//            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//
////                @Override
////                public void onSuccess(LoginResult loginResult) {
////
////                    final AccessToken accessToken = AccessToken.getCurrentAccessToken();
////                    Log.e(TAG, "SharingActivity access token: " + accessToken);
////
////                    sharePhotoToFacebook();
////                    Toast.makeText(getApplicationContext(), "You shared this post.", Toast.LENGTH_SHORT).show();
////                    finish();
////                }
//
//                @Override
//                public void onSuccess(Sharer.Result result) {
//                    final AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                    Log.e(TAG, "SharingActivity access token: " + accessToken);
//
//                    sharePhotoToFacebook();
//                    Toast.makeText(getApplicationContext(), "You shared this post.", Toast.LENGTH_SHORT).show();
//                    finish();
//
//                }
//
//                @Override
//                public void onCancel() {
//                    final AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                    Log.e(TAG, "SharingActivity access token: " + accessToken);
//
//                    Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
//                    Log.e(TAG, "onCancel: ");
////                    finish();
//                }
//
//                @Override
//                public void onError(FacebookException exception) {
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
//
//                    Log.e(TAG, "onError: " );
////                    finish();
//                }
//            });
//        }
//    }
////    private void sharePhotoToFacebook() {
////        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
////        SharePhoto photo = new SharePhoto.Builder()
////                .setBitmap(image)
////                .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
////                .build();
////
////        SharePhotoContent content = new SharePhotoContent.Builder()
////                .addPhoto(photo)
////                .build();
////
////        ShareApi.share(content, null);
////    }
//
//    private void sharePhotoToFacebook() {
//
//        if (ShareDialog.canShow(ShareLinkContent.class)) {
//            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentTitle(clicked_name)
//                    .setImageUrl(Uri.parse(clicked_user_picture))
//                    .setContentDescription("FB SEARCH FROM USC CSCI571")
//                    .setContentUrl(Uri.parse("http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/"))
//                    .build();
//            shareDialog.show(linkContent);  // Show facebook ShareDialog
////            ShareApi.share(linkContent, null);
//        }
//
//
//
//
//
////        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
////        SharePhoto photo = new SharePhoto.Builder()
////                .setBitmap(image)
////                .build();
////
////        SharePhotoContent content = new SharePhotoContent.Builder()
////                .setContentUrl(Uri.parse("https://developers.facebook.com"))
////                .addPhoto(photo)
////                .build();
////
////        ShareApi.share(content, null);
//    }
//
//    /**
//     * Final callback that posts the shareDialog in Facebook
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
//        Log.d(TAG, "onActivityResult: " + requestCode + " : " + responseCode + " :  " + data);
//
//        super.onActivityResult(requestCode, responseCode, data);
//        callbackManager.onActivityResult(requestCode, responseCode, data);
//    }
//}
