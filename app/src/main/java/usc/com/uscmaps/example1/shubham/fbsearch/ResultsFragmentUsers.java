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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.ResultFragmentsAdapter;
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
    LinearLayout buttonNextPrev;

    private int pageCount;
    private String tabNumber = "0";
    private int increment = 0;
    public int TOTAL_LIST_ITEMS = 25;
    public int NUM_ITEMS_PAGE = 10;
    private String userInput;
    private ArrayList<ArrayList<String>> resultsList = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        userInput = prefs.getString("input", "No name defined");

//        Map<String, ?> keys = prefs.getAll();
//        for (Map.Entry<String, ?> entry : keys.entrySet()) {
//            Log.e(TAG, "map values: "+ entry.getKey() + ": " +
//                    entry.getValue().toString());
//        }

//        active_tab = prefs.getString("active_tab", "0");
//        active_tab = prefs.getString("active_tab", "user");
//        Log.e(TAG, "onCreate: " + active_tab);

//        fetchFacebookData(userInput);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.e(TAG, "onAttach: " );
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.results_fragment_users, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView_users);
        btn_prev = (Button) rootView.findViewById(R.id.bt_prev1);
        btn_next = (Button) rootView.findViewById(R.id.bt_next1);
        buttonNextPrev = (LinearLayout) rootView.findViewById((R.id.buttonNextPrev));

        if(rootView != null){
            fetchFacebookData(userInput);
        }

        btn_prev.setEnabled(false);

        /**
         * this block is for checking the number of pages
         */
        int val = TOTAL_LIST_ITEMS % 10;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;

        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Log.e(TAG, "clicked Next Button");
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
         * The custom adapter, right now being set after Facebook calls, now being set up in AWS call
         */
//        final ResultFragmentsAdapter adapter = new ResultFragmentsAdapter(this.getActivity(), MOBILE_OS_array);
//        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e(TAG, "onItemClick: "+parent+ " : " + parent.getItemAtPosition(position) + " : "+view +" : "+id + " : "+position);
//
//                Log.e(TAG, "onItemClick: " );
////                addLastActiveTabSharedPref("0");
//
//
////                ArrayList<String> arr_temp = (ArrayList<String>) parent.getItemAtPosition(position);
////                addToSharedPref(arr_temp.get(1), arr_temp.get(0), arr_temp.get(2));
////                Intent intent = new Intent(getActivity(), DetailsActivity.class);
////                startActivity(intent);
//            }
//        });
        return rootView;
    }

//    private void addLastActiveTabSharedPref(String last_active_tab) {
//        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putString("last_active_tab", last_active_tab);
//        editor.commit();
//
//    }
//    private void addToSharedPref(String userID, String name, String imageUrl) {
//        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putString("selected_listView_item", userID);
//        editor.putString("clicked_user_name", name);
//        editor.putString("clicked_user_picture", imageUrl);
//        editor.commit();
//    }

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

        resultsList = new ArrayList<ArrayList<String>>();
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
                    if(lengthJSON == 0){
                        buttonNextPrev.setVisibility(View.GONE);
                    }
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

                    loadList(0);
//                    Log.e(TAG, "processFinish: "+resultsList.size()+1 );
//                    ResultFragmentsAdapter adapter = new ResultFragmentsAdapter(cont, resultsList);
//                    listView.setAdapter(adapter);

                } catch (JSONException e) {
//                    Log.e(TAG, "onCompleted: Catch");
                    e.printStackTrace();
                }
            }
        });
        httpConn.execute(url);


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
        Context cont = this.getActivity();
//        Log.e(TAG, "loadList: "+resultsList.size());

        ArrayList<ArrayList<String>> sort = new ArrayList<>();

        int start = number * NUM_ITEMS_PAGE;
        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
            if (i < resultsList.size()) {
                sort.add(resultsList.get(i));
            } else {
                break;
            }
        }


//        Log.e(TAG, "processFinish: "+resultsList.size()+1 );
        if (cont != null) {
            ResultFragmentsAdapter adapter = new ResultFragmentsAdapter(cont, sort, tabNumber);
            listView.setAdapter(adapter);
        }
    }
}
