package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.DetailsFargmentPostsAdapter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shubham on 4/10/17.
 */

public class DetailsFragmentPosts extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    ListView listViewPosts;
    Context cont;

    // Defined Array values to show in ListView
    String[] values = new String[]{"Android List View",
            "Adapter implementation",
            "Simple List View In Android",
            "Create List View Android",
            "Android Example",
            "List View Source Code",
            "List View Array Adapter",
            "Android Example List View"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        cont = this.getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment_posts, container, false);

        listViewPosts = (ListView) rootView.findViewById(R.id.listView_details_posts);
//        ArrayAdapter<String> tempAdapter = new ArrayAdapter<String>(this.getContext(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, values);



        DetailsFargmentPostsAdapter adapter = new DetailsFargmentPostsAdapter(cont, new ArrayList( Arrays.asList( new String[]{"Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"} ) ));
        listViewPosts.setAdapter(adapter);


        listViewPosts.setAdapter(adapter);

        return rootView;

    }


    /**
     * Facebook Async method to get JSON
     *
     *
     */
    private void fetchFacebookData() {

        final Context cont = this.getActivity();
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

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String userID = prefs.getString("selected_listView_item", "No name defined");
        Log.e(TAG, "fetchFacebookData: "+userID );

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        Log.e(TAG, "fetchFacebookData: " + accessToken + " ,input: " + input);

        final ArrayList<ArrayList<String>> resultsList = new ArrayList<ArrayList<String>>();

        userID = "124984464200434";
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken, "/"+userID, new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {

                        ArrayList<String> listJSON = new ArrayList<String>();

                        try {
                            Log.e(TAG, "onCompleted graphresponse2: " + response);
                            Log.e(TAG, "onCompleted graphresponse2: " + response.getJSONObject());
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").length());
                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONObject("posts"));
                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONObject("posts").getJSONArray("data").length());

//                            int lengthJSON = response.getJSONObject().getJSONArray("data").length();
//                            for (int i = 0; i < lengthJSON; i++) {
//                                ArrayList<String> temp = new ArrayList<String>();
//
//                                JSONObject data = response.getJSONObject().getJSONArray("data").getJSONObject(i);
////                                Log.e(TAG, "onCompleted: " + data.get("name"));
//
//                                temp.add(data.get("name").toString());
//                                temp.add(data.get("id").toString());
//
//
//                                JSONObject picture = data.getJSONObject("picture");
//                                JSONObject data1 = picture.getJSONObject("data");
////                                Log.e(TAG, "onCompleted: " + data1.get("height"));
//                                temp.add(data1.get("url").toString());
//
//                                resultsList.add(temp);
//                            }

////                            Log.e(TAG, "onCompleted: " + resultsList);
//                            ResultFragmentUsersAdapter adapter = new ResultFragmentUsersAdapter(cont, resultsList);
//                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.e(TAG, "onCompleted: Catch");
                            Log.e(TAG, "exception" , e);
                        }
//                        Log.e(TAG, "onCompleted: " + 1);
//                        loadList(0);
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
