package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.MyExpandableListAdapter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shubham on 4/10/17.
 * This Fragment has ExpandableListView
 */

public class DetailsFragmentAlbums extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ExpandableListView listView;
    TextView txtViewNoAlbumFound;
    SparseArray<Group> groups = new SparseArray<Group>();


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.details_fragment_albums, container, false);
//        createData();
        fetchFacebookData();

        listView = (ExpandableListView) rootview.findViewById(R.id.expandableListView_details);

        txtViewNoAlbumFound = (TextView) rootview.findViewById(R.id.txtView_no_albums_found);
        return rootview;
    }

//    String[] groupName = {"Timeline Photos","Cover Photos","Mobile Uploads","Profile Pictures"
//            ,"CRS-2 Mission Photo Gallery" };
//
//    private void createData() {
//        for (int j = 0; j < 5; j++) {
//            Group group = new Group(groupName[j]);
//            for (int i = 0; i <= 1; i++) {
//                group.imageUrl.add("group: " + j+ "subGroup: "+i);
//            }
//            groups.append(j, group);
//        }
//    }

    /**
     * Facebook Async method to get JSON
     *
     */
    private void fetchFacebookData() {

        final Context mContext = this.getActivity();
//
//        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        Log.e(TAG, "fetchFacebookData: "+ accessToken);
//
//        GraphRequest request = GraphRequest.newMeRequest(
//            accessToken, new GraphRequest.GraphJSONObjectCallback() {
//                @Override
//                public void onCompleted(JSONObject me, GraphResponse response) {
//                    Log.e(TAG, "onCompleted: "+me );
//                    Log.e(TAG, "onCompleted graphresponse: "+response );
//                }
//            });
//        Bundle parameters = new Bundle();
//        parameters.putString("q", "usc");
//        parameters.putString("type", "user");
//        request.setParameters(parameters);
//        GraphRequest.executeBatchAsync(request);

        SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String userID = prefs.getString("selected_listView_item", "No name defined");
        Log.e(TAG, "fetchFacebookData: "+userID );

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        Log.e(TAG, "fetchFacebookData: " + accessToken + " ,input: " + input);

        final ArrayList<ArrayList<String>> resultsList = new ArrayList<ArrayList<String>>();

//        userID = "353851465130";
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken, "/"+userID, new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {

                        ArrayList<String> listJSON = new ArrayList<String>();

                        try {
//                            Log.e(TAG, "onCompleted graphresponse2: " + response);
//                            Log.e(TAG, "onCompleted graphresponse2: " + response.getJSONObject());
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONObject("albums"));
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONObject("albums").getJSONArray("data").length());

                            if(!response.getJSONObject().has("albums")){
//                                Log.e(TAG, "NO albums: " );
                                listView.setVisibility(View.GONE);
                                txtViewNoAlbumFound.setVisibility(View.VISIBLE);

                            }else {
                                int lengthJSON = response.getJSONObject().getJSONObject("albums").getJSONArray("data").length();
                                for (int i = 0; i < lengthJSON; i++) {
                                    JSONObject data = response.getJSONObject().getJSONObject("albums").getJSONArray("data").getJSONObject(i);

                                    Group group = new Group(data.get("name").toString());

                                    JSONArray inDataAlbums = data.getJSONObject("photos").getJSONArray("data");
                                    int subGroupLength = data.getJSONObject("photos").getJSONArray("data").length();
                                    for (int j = 0; j < subGroupLength; j++) {
                                        JSONObject currImage = inDataAlbums.getJSONObject(j);
                                        group.imageUrl.add(currImage.get("picture").toString());
                                    }
                                    groups.append(i, group);
                                }

                                MyExpandableListAdapter adapter = new MyExpandableListAdapter(getActivity(), groups);
                                listView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "onCompleted: Catch");
                            Log.e(TAG, "exception" , e);
                        }
                    }
                });

//        search?q=USC&type=user&fields=id,name,picture.width(700).height(700)
        Bundle parameters1 = new Bundle();
//        parameters1.putString("q", input);
//        parameters1.putString("type", "user");
        parameters1.putString("fields", "id,name,picture.width(700).height(700)," +
                "albums.limit(5){name,photos.limit(2){name, picture}},posts.limit(5)");
        request.setParameters(parameters1);
        GraphRequest.executeBatchAsync(request);

    }
}
