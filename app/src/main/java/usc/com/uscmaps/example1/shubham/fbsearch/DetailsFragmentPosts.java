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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.DetailsFargmentPostsAdapter;
import usc.com.uscmaps.example1.shubham.fbsearch.models.Posts;
import usc.com.uscmaps.example1.shubham.fbsearch.util.AsyncResponse;
import usc.com.uscmaps.example1.shubham.fbsearch.util.HttpConnectionMy;

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
        final ArrayList<ArrayList<String>> resultsList = new ArrayList<ArrayList<String>>();
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String userID = prefs.getString("selected_listView_item", "No name defined");
//        Log.e(TAG, "fetchFacebookData Posts: " + userID);

//        http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?queryString=usc&type=user
        String url = String.format("http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?id=%s", userID);
        HttpConnectionMy httpConn = new HttpConnectionMy(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject response) {
                ArrayList<Posts> postslistJSON = new ArrayList<>();


                try {
//                    Log.e(TAG, "processFinish: Posts: " + response);
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data"));
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").length());
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").get(0));
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").get(1));

                    JSONObject imageJSON = response.getJSONObject("picture").getJSONObject("data");
//
                    if (!response.has("posts")) {
                        txtViewNoneFound.setVisibility(View.VISIBLE);
                        listViewPosts.setVisibility(View.GONE);
                    } else {
                        JSONArray data = response.getJSONObject("posts").getJSONArray("data");
                        int lengthJSON = response.getJSONObject("posts").getJSONArray("data").length();

                        for (int i = 0; i < lengthJSON; i++) {
                            Posts currPostObject = new Posts();
//
                            JSONObject currPostJSON = data.getJSONObject(i);

                            currPostObject.setCreated_time(currPostJSON.getString("created_time"));
                            currPostObject.setHeader(response.getString("name"));
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
                    e.printStackTrace();
                }
            }
        });
        httpConn.execute(url);
    }
}
