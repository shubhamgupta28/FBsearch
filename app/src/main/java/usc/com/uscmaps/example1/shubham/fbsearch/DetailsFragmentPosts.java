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
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.DetailsFargmentPostsAdapter;
import usc.com.uscmaps.example1.shubham.fbsearch.models.Posts;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shubham on 4/10/17.
 */

public class DetailsFragmentPosts extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private ListView listViewPosts;
    private TextView txtViewNoneFound;
    private Context cont;

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
        txtViewNoneFound = (TextView) rootView.findViewById(R.id.txtView_no_posts_found);
        fetchFacebookData();
        return rootView;
    }

    /**
     * Facebook Async method to get JSON
     */
    private void fetchFacebookData() {
        final Context cont = this.getActivity();
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String userID = prefs.getString("selected_listView_item", "No name defined");
//        Log.e(TAG, "fetchFacebookData: "+userID );

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken, "/"+userID, new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
//                        Log.e(TAG, "onCompleted: "+response );

                        ArrayList<Posts> postslistJSON = new ArrayList<>();

                        /**
                         * Extract the posts
                         */
                        try {
                            JSONObject imageJSON = response.getJSONObject().getJSONObject("picture").getJSONObject("data");

                            if(!response.getJSONObject().has("posts")){
                                txtViewNoneFound.setVisibility(View.VISIBLE);
                                listViewPosts.setVisibility(View.GONE);
                            } else {
                                JSONArray data = response.getJSONObject().getJSONObject("posts").getJSONArray("data");
//                                Log.e(TAG, "onCompleted: " + data);
                                int lengthJSON = response.getJSONObject().getJSONObject("posts").getJSONArray("data").length();

                                for (int i = 0; i < lengthJSON; i++) {
                                    Posts currPostObject = new Posts();
//
                                    JSONObject currPostJSON = data.getJSONObject(i);
//                                    Log.e(TAG, "currPostJSON: " + currPostJSON);

                                    currPostObject.setCreated_time(currPostJSON.getString("created_time"));
                                    currPostObject.setHeader(response.getJSONObject().getString("name"));
                                    currPostObject.setProfile_image(imageJSON.getString("url"));
                                    if (currPostJSON.has("message")) {
                                        currPostObject.setMessage(currPostJSON.getString("message"));
                                    }
                                    postslistJSON.add(currPostObject);

                                }

                                DetailsFargmentPostsAdapter adapter = new DetailsFargmentPostsAdapter(cont, postslistJSON);
                                listViewPosts.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            Log.e(TAG, "onCompleted: Catch");
                            Log.e(TAG, "exception" , e);
                        }
                    }
                });

//        search?q=USC&type=user&fields=id,name,picture.width(700).height(700)
        Bundle parameters1 = new Bundle();
        parameters1.putString("fields", "id,name,picture.width(700).height(700)," +
                "albums.limit(5){name,photos.limit(2){name, picture}},posts.limit(5)");
        request.setParameters(parameters1);
        GraphRequest.executeBatchAsync(request);

    }
}
