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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.MyExpandableListAdapter;
import usc.com.uscmaps.example1.shubham.fbsearch.models.Group;
import usc.com.uscmaps.example1.shubham.fbsearch.util.AsyncResponse;
import usc.com.uscmaps.example1.shubham.fbsearch.util.HttpConnectionMy;

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
    private int lastExpandedPosition = -1;



    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.details_fragment_albums, container, false);
        fetchFacebookData();

        listView = (ExpandableListView) rootview.findViewById(R.id.expandableListView_details);
        txtViewNoAlbumFound = (TextView) rootview.findViewById(R.id.txtView_no_albums_found);

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    listView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        return rootview;
    }


    /**
     * Facebook Async method to get JSON
     */
    private void fetchFacebookData() {


        final Context mContext = this.getActivity();
        final ArrayList<ArrayList<String>> resultsList = new ArrayList<ArrayList<String>>();

        SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String userID = prefs.getString("selected_listView_item", "No name defined");
//        Log.e(TAG, "fetchFacebookData Albums: " + userID);


//        http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?queryString=usc&type=user
        String url = String.format("http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?id=%s", userID);
        HttpConnectionMy httpConn = new HttpConnectionMy(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject response) {
                ArrayList<String> listJSON = new ArrayList<String>();
                try {
                    if (response == null || !response.has("albums")) {
                        listView.setVisibility(View.GONE);
                        txtViewNoAlbumFound.setVisibility(View.VISIBLE);
                    } else {
                        int lengthJSON = response.getJSONObject("albums").getJSONArray("data").length();
                        for (int i = 0; i < lengthJSON; i++) {
                            JSONObject data = response.getJSONObject("albums").getJSONArray("data").getJSONObject(i);

                            Group group = new Group(data.get("name").toString());

                            if (data.has("photos")) {
                                JSONArray inDataAlbums = data.getJSONObject("photos").getJSONArray("data");
                                int subGroupLength = data.getJSONObject("photos").getJSONArray("data").length();
                                for (int j = 0; j < subGroupLength; j++) {
                                    JSONObject currImage = inDataAlbums.getJSONObject(j);
                                    group.imageUrl.add(currImage.get("picture").toString());
                                }
                            }
                            groups.append(i, group);
                        }
                        MyExpandableListAdapter adapter = new MyExpandableListAdapter(getActivity(), groups);
                        listView.setAdapter(adapter);
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
