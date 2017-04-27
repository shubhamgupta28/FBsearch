package usc.com.uscmaps.example1.shubham.fbsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import usc.com.uscmaps.example1.shubham.fbsearch.DetailsActivity;
import usc.com.uscmaps.example1.shubham.fbsearch.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Shubham on 4/9/17.
 */

public class ResultFragmentsAdapter extends ArrayAdapter<ArrayList<String>> {

    private final String TAG = getClass().getSimpleName();
    private final Context mContext;
    private final ArrayList<ArrayList<String>> values;
    private SharedPreferences sPref;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String curr_tab = null;
    private ArrayList<String> curr;
    private String tabNumber;
//    private String isStarred;
//    private boolean mShowStarredOnly = true;

    public ResultFragmentsAdapter(Context context, ArrayList<ArrayList<String>> values, String tabNumber) {
        super(context, R.layout.custom_view_listview_main, values);
        this.mContext = context;
        this.values = values;
        this.sPref = mContext.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        this.tabNumber = tabNumber;
    }

    public void updateList(ArrayList<ArrayList<String>> resultsList) {
//        Log.e(TAG, "updateList: resultlist" + resultsList );
        ArrayList<ArrayList<String>> finalFavList = new ArrayList<>();
        values.clear();

        for (ArrayList<String> insideResult : resultsList) {
            if (chechStarForFav(insideResult)) {
                values.add(insideResult);
            }
        }

//        if(mShowStarredOnly) {
//            for (ArrayList<String> result : resultsList) {
//                if(result.get())
//                values.add(result);
//            }
//        } else {
//            values.addAll(resultsList);
//        }

//        Log.e(TAG, "updateList: values" + values);

        this.notifyDataSetChanged();

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        Log.e(TAG, "getView: "+ position + " convertView: " + convertView + " parent: " + parent);
        try {
            LayoutInflater inflater = null;
            if (mContext != null || mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) != null) {
                inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            curr_tab = sPref.getString("active_tab", "None");
//        Log.e(TAG, "getView: curr_tab" + curr_tab);

            View rowView = null;
            if (inflater != null) {
                rowView = inflater.inflate(R.layout.custom_view_listview_main, parent, false);

                TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
                ImageView imgViewUser = (ImageView) rowView.findViewById(R.id.icon);
                ImageView imgViewMoreDetails = (ImageView) rowView.findViewById(R.id.imgView_more_details);
                ImageView imgViewStarred = (ImageView) rowView.findViewById(R.id.imgView_starred);

                imgViewMoreDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        addLastActiveTabSharedPref(tabNumber);

                        ArrayList<String> currList = getItem(position);
                        addToSharedPref(currList.get(1), currList.get(0), currList.get(2));

                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("isStarred", "" + checkStarred(currList));
                        mContext.startActivity(intent);
                    }
                });


                curr = values.get(position);
                textView.setText(curr.get(0));
                Picasso.with(this.getContext()).load(curr.get(2)).into(imgViewUser);

                if (checkStarred()) {
                    imgViewStarred.setImageResource(R.mipmap.favorites_on);
                    notifyDataSetChanged();
                } else {
                    imgViewStarred.setImageResource(R.drawable.favorites_off);
                }

            }
            return rowView;
        }
        catch (Exception e){
            Log.e(TAG, "getView: "+ e );
        }

        return null;

    }

    private void addLastActiveTabSharedPref(String last_active_tab) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("last_active_tab", last_active_tab);
        editor.commit();

    }

    private void addToSharedPref(String userID, String name, String imageUrl) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("selected_listView_item", userID);
        editor.putString("clicked_user_name", name);
        editor.putString("clicked_user_picture", imageUrl);
        editor.commit();
    }


    @Nullable
    @Override
    public ArrayList<String> getItem(int position) {

        return super.getItem(position);
    }


    private boolean chechStarForFav(ArrayList<String> insideResult) {
        HashMap<String, ArrayList<String>> hMap = loadMap();

        ArrayList<String> listOfTabs = readListOfTabs();
//        Log.e(TAG, "checkStarred: listOfActiveTabs" + listOfTabs);

        if (listOfTabs != null) {
            for (String currtabfromPref : listOfTabs) {
                if (hMap.containsKey(currtabfromPref)) {
                    ArrayList<String> curr_fav_list = hMap.get(currtabfromPref);

                    for (String a : curr_fav_list) {
                        if (a.equals(insideResult.get(1))) {
                            return true;
                        }
                    }

                }
            }
        }

        return false;
    }

    /**
     * This function has a List and a Map. The list contains all tabs on which Add to Fav has been
     * clicked. So, for each item in this list, each HashMap key value is checked.
     * <p>
     * This was done coz the setOffPageLimit is by default 1, and the right and left tab of the
     * current tab couldn't be set to active_tab, leading to the right left tabs to have un-starred
     * images
     *
     * @return true if the row show have starred image.
     */
    private boolean checkStarred() {
        HashMap<String, ArrayList<String>> hMap = loadMap();
//        Log.e(TAG, "checkStarred: HashMap" + hMap);

        ArrayList<String> listOfTabs = readListOfTabs();
//        Log.e(TAG, "checkStarred: listOfActiveTabs" + listOfTabs);

        if (listOfTabs != null) {
            for (String currtabfromPref : listOfTabs) {
                if (hMap.containsKey(currtabfromPref)) {
                    ArrayList<String> curr_fav_list = hMap.get(currtabfromPref);

                    for (String a : curr_fav_list) {
                        if (a.equals(curr.get(1))) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }


    /**
     * special check star for checking of currrenlty clicked item is starred or not
     *
     * @param star
     * @return
     */
    private boolean checkStarred(ArrayList<String> star) {
        HashMap<String, ArrayList<String>> hMap = loadMap();
//        Log.e(TAG, "checkStarred: HashMap"+ hMap );

        ArrayList<String> listOfTabs = readListOfTabs();
//        Log.e(TAG, "checkStarred: listOfActiveTabs" + listOfTabs);

        if (listOfTabs != null) {
            for (String currtabfromPref : listOfTabs) {
                if (hMap.containsKey(currtabfromPref)) {
                    ArrayList<String> curr_fav_list = hMap.get(currtabfromPref);

                    for (String a : curr_fav_list) {
                        if (a.equals(star.get(1))) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * Loads Map from SharedPref
     *
     * @return
     */
    private HashMap<String, ArrayList<String>> loadMap() {

        HashMap<String, ArrayList<String>> currMap = new HashMap<>();
        try {
            if (sPref != null) {
                String jsonString = sPref.getString("My_map", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String key = keysItr.next();
                    JSONArray value = jsonObject.getJSONArray(key);
                    ArrayList<String> listdata = new ArrayList<>();
                    if (value != null) {
                        for (int i = 0; i < value.length(); i++) {
                            listdata.add(value.getString(i));
                        }
                    }
                    currMap.put(key, listdata);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currMap;
    }

    /**
     * Loads List from SharedPref
     *
     * @return
     */
    private ArrayList<String> readListOfTabs() {
        ArrayList<String> arrayList = new ArrayList<>();

        Gson gson = new Gson();
        String json = sPref.getString("list_of_active_tabs", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
        return arrayList;
    }
}
