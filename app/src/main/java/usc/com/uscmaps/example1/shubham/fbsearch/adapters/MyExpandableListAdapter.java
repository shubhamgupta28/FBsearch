package usc.com.uscmaps.example1.shubham.fbsearch.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.Group;
import usc.com.uscmaps.example1.shubham.fbsearch.R;
import usc.com.uscmaps.example1.shubham.fbsearch.models.Posts;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shubham on 4/10/17.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private final String TAG = getClass().getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<Posts> list_posts = new ArrayList<>();


    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public MyExpandableListAdapter(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        ImageView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.details_fragment_albums_row_details, null);
        }
        text = (ImageView) convertView.findViewById(R.id.textView1);
        text.setImageResource(R.mipmap.delete_temp);


        fetchFacebookData("usc");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, children, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.details_fragment_albums_list_row, null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    /**
     * Facebook Async method to get JSON
     *
     * @param input The Input entered by the user
     */
    private void fetchFacebookData(String input) {

        final Context cont = this.activity;
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

        SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
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
//                            Log.e(TAG, "onCompleted graphresponse2: " + response);
//                            Log.e(TAG, "onCompleted graphresponse2: " + response.getJSONObject());
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONObject("posts"));
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONObject("posts").getJSONArray("data").length());

                            int lengthJSON = response.getJSONObject().getJSONArray("data").length();
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
