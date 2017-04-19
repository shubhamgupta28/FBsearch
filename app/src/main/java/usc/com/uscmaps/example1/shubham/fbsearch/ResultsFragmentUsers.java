package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.ResultFragmentUsersAdapter;
import usc.com.uscmaps.example1.shubham.fbsearch.util.AsyncResponse;
import usc.com.uscmaps.example1.shubham.fbsearch.util.HttpConnectionMy;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shubham on 4/9/17.
 * <p>
 * Good example with Async and load more http://www.androidhive.info/2012/03/android-listview-with-load-more-button/
 * <p>
 * ListView working with pagination buttons https://rakhi577.wordpress.com/2013/05/20/listview-pagination-ex-1/
 */

public class ResultsFragmentUsers extends Fragment {
    private ListView listView;
    private final String TAG = getClass().getSimpleName();

    private Button btn_prev;
    private Button btn_next;

    private ArrayList<String> data;
    ArrayAdapter<String> sd;

    private int pageCount;
    private int increment = 0;


    public int TOTAL_LIST_ITEMS = 25;
    public int NUM_ITEMS_PAGE = 10;
    String userInput;


    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static String active_tab = null;

    static final String[] MOBILE_OS_array =
            new String[]{"Android", "iOS", "WindowsMobile", "Blackberry"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        userInput = prefs.getString("input", "No name defined");
        active_tab = prefs.getString("active_tab", "user");
//        Log.e(TAG, "onCreate: " + active_tab);

//        Map<String,?> keys = prefs.getAll();
//
//        for(Map.Entry<String,?> entry : keys.entrySet()){
//            Log.e("map values",entry.getKey() + ": " +
//                    entry.getValue().toString());
//        }

        fetchFacebookData(userInput);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.results_fragment_users, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView_users);
        btn_prev = (Button) rootView.findViewById(R.id.bt_prev1);
        btn_next = (Button) rootView.findViewById(R.id.bt_next1);

        btn_prev.setEnabled(false);
        data = new ArrayList<>();

        /**
         * this block is for checking the number of pages
         */
        int val = TOTAL_LIST_ITEMS % 2;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;


//        /**
//         * The ArrayList data contains all the list items
//         */
//        for (int i = 0; i < TOTAL_LIST_ITEMS; i++) {
//            data.add("This is Item " + (i + 1));
//        }
//
////        loadList(0);

        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e(TAG, "clicked Next Button");
                increment++;
                loadList(increment);
                CheckEnable();
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment--;
                loadList(increment);
                CheckEnable();
            }
        });

        /**
         * The custom adapter, right now being set after Facebook calls
         */
//        final ResultFragmentUsersAdapter adapter = new ResultFragmentUsersAdapter(this.getActivity(), MOBILE_OS_array);
//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e(TAG, "onItemClick: "+parent.getItemAtPosition(position) + " : "+view +" : "+id);
                ArrayList<String> arr_temp = (ArrayList<String>) parent.getItemAtPosition(position);
                addToSharedPref(arr_temp.get(1));
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void addToSharedPref(String input) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        Log.e(TAG, "addToSharedPref: " + input);
        editor.putString("selected_listView_item", input);
        editor.apply();
    }

    /**
     * Facebook Async method to get JSON
     *
     * @param input The Input entered by the user
     */
    private void fetchFacebookData(String input) {

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

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        final ArrayList<ArrayList<String>> resultsList = new ArrayList<ArrayList<String>>();


//        http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?queryString=usc&type=user
        String url = String.format("http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?queryString=%s&type=user", userInput);
        HttpConnectionMy httpConn = new HttpConnectionMy(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject response) {
                try {
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data"));
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").length());
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").get(0));
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").get(1));

                    int lengthJSON = response.getJSONArray("data").length();
                    for (int i = 0; i < lengthJSON; i++) {
                        ArrayList<String> temp = new ArrayList<>();

                        JSONObject data = response.getJSONArray("data").getJSONObject(i);
                        temp.add(data.get("name").toString());
                        temp.add(data.get("id").toString());

                        JSONObject picture = data.getJSONObject("picture");
                        JSONObject data1 = picture.getJSONObject("data");
//                                Log.e(TAG, "onCompleted: " + data1.get("height"));
                        temp.add(data1.get("url").toString());

                        resultsList.add(temp);
                    }
                    ResultFragmentUsersAdapter adapter = new ResultFragmentUsersAdapter(cont, resultsList);
                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    Log.e(TAG, "onCompleted: Catch");
                    e.printStackTrace();
                }
            }
        });
        httpConn.execute(url);


//        GraphRequest request = GraphRequest.newGraphPathRequest(
//                accessToken, "/search", new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        Log.e(TAG, "onCompleted: " + response);
//
////                        ArrayList<String> listJSON = new ArrayList<String>();
//
//
////                        Log.e(TAG, "onCompleted: " + 1);
////                        loadList(0);
//                    }
//                });
//
////        search?q=USC&type=user&fields=id,name,picture.width(700).height(700)
//        Bundle parameters1 = new Bundle();
//        parameters1.putString("q", input);
//        parameters1.putString("type", "user");
//        parameters1.putString("fields", "id,name,picture.width(700).height(700)");
//        Log.e(TAG, "fetchFacebookData: " + parameters1);
//        request.setParameters(parameters1);
//        GraphRequest.executeBatchAsync(request);

    }

    /**
     * Method for enabling and disabling Buttons
     */
    private void CheckEnable() {
        if (increment + 1 == pageCount) {
            btn_next.setEnabled(false);
        } else if (increment == 0) {
            btn_prev.setEnabled(false);
        } else {
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
        }
    }

    /**
     * Method for loading data in listview
     *
     * @param number
     */
    private void loadList(int number) {
        ArrayList<String> sort = new ArrayList<>();

        int start = number * NUM_ITEMS_PAGE;
        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
            if (i < data.size()) {
                sort.add(data.get(i));
            } else {
                break;
            }
        }

//        sd = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, sort);
//        listView.setAdapter(sd);

//        final ResultFragmentUsersAdapter adapter = new ResultFragmentUsersAdapter(this.getActivity(), sort);
//        listView.setAdapter(adapter);
    }
}
