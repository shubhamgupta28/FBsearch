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
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.ResultFragmentsAdapter;
import usc.com.uscmaps.example1.shubham.fbsearch.util.AsyncResponse;
import usc.com.uscmaps.example1.shubham.fbsearch.util.HttpConnectionMy;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shubham on 4/20/17.
 */

public class FavoritesFragementGroups extends Fragment {

    private ListView listView;
    private final String TAG = getClass().getSimpleName();
    private Button btn_prev;
    private Button btn_next;
    private int pageCount;
    private int increment = 0;
    public int TOTAL_LIST_ITEMS ;
    public int NUM_ITEMS_PAGE = 10;
    private String tabNumber = "4";
    private ResultFragmentsAdapter adapter;

    private String userInput;
    private ArrayList<ArrayList<String>> resultsList = null;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private static int sizeOfListIDs = 0;
    private int check = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] userIDlist = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userIDlist = bundle.getStringArray("userIDlist");
        }

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        userInput = prefs.getString("input", "No name defined");

//        Fav Acti{0=[1043337069143908,1043337069143908], 1=[136225076403414], 3=[222253244491192]}

//        ArrayList<String> dummy = new ArrayList<>();
//        dummy.add("1043337069143908");
//        dummy.add("1043337069143908");
//        Log.e(TAG, "onCreate: 12312312312" );
//        Log.e(TAG, "userIDlist: " + Arrays.toString(userIDlist));
        sizeOfListIDs = userIDlist.length;
        TOTAL_LIST_ITEMS = sizeOfListIDs;

        for(String currID : userIDlist)
            fetchFacebookData(currID);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.results_fragment_users, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView_users);
        btn_prev = (Button) rootView.findViewById(R.id.bt_prev1);
        btn_next = (Button) rootView.findViewById(R.id.bt_next1);

        btn_prev.setEnabled(false);
        if( TOTAL_LIST_ITEMS < 10){
            btn_next.setEnabled(false);
        }

        /**
         * this block is for checking the number of pages
         */
        int val = TOTAL_LIST_ITEMS % 10;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;

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

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Log.e(TAG, "onItemClick: "+parent+ " : " + parent.getItemAtPosition(position) + " : "+view +" : "+id + " : "+position);
//
//                addLastActiveTabSharedPref("4");
//
//                ArrayList<String> arr_temp = (ArrayList<String>) parent.getItemAtPosition(position);
//                addToSharedPref(arr_temp.get(1), arr_temp.get(0), arr_temp.get(2));
//                Intent intent = new Intent(getActivity(), FavDetailsActivity.class);
//                startActivity(intent);
//            }
//        });

        return rootView;
    }

    private void addLastActiveTabSharedPref(String last_active_tab_favorites) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("last_active_tab_favorites", last_active_tab_favorites);
        editor.commit();
    }

    private void addToSharedPref(String userID, String name, String imageUrl) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("selected_listView_item", userID);
        editor.putString("clicked_user_name", name);
        editor.putString("clicked_user_picture", imageUrl);
        editor.apply();
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
        final Context cont = this.getActivity();
        ArrayList<ArrayList<String>> sort = new ArrayList<>();

        int start = number * NUM_ITEMS_PAGE;
        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
            if (i < resultsList.size()) {
                sort.add(resultsList.get(i));
            } else {
                break;
            }
        }

        adapter = new ResultFragmentsAdapter(cont, sort , tabNumber);
        listView.setAdapter(adapter);
    }


    /**
     * Facebook Async method to get JSON
     *
     * @param userID The ID saved in SharedPref
     */
    private void fetchFacebookData(String userID) {

        check++;
        final Context cont = this.getActivity();
        resultsList = new ArrayList<>();

//        http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?queryString=usc&type=user
//        String url = String.format("http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?queryString=%s&type=user", userInput);
        String url = String.format("http://fbsearch-env.us-west-2.elasticbeanstalk.com/index.php/index.php?id=%s", userID);

        HttpConnectionMy httpConn = new HttpConnectionMy(new AsyncResponse() {
            @Override
            public void processFinish(JSONObject response) {
                try {

//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data"));
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").length());
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").get(0));
//                            Log.e(TAG, "onCompleted graphresponse1: " + response.getJSONObject().getJSONArray("data").get(1));

//                    int lengthJSON = response.getJSONObject("data").length();
//                    for (int i = 0; i < lengthJSON; i++) {
                    ArrayList<String> temp = new ArrayList<>();

//                        JSONObject data = response.getJSONArray("data").getJSONObject(i);
                    temp.add(response.get("name").toString());
                    temp.add(response.get("id").toString());

                    JSONObject picture = response.getJSONObject("picture");
                    JSONObject data1 = picture.getJSONObject("data");
                    temp.add(data1.get("url").toString());

                    resultsList.add(temp);

//                    }

                    if(check == sizeOfListIDs)
                        loadList(0);
//                    Log.e(TAG, "processFinish: "+resultsList.size()+1 );
//                    ResultFragmentsAdapter adapter = new ResultFragmentsAdapter(cont, resultsList);
//                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    Log.e(TAG, "onCompleted: Catch");
                    e.printStackTrace();
                }
            }
        });
        httpConn.execute(url);

    }

    @Override
    public void onResume() {
//        Log.e(TAG, "onResume: " );
        super.onResume();

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
