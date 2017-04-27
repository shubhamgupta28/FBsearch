package usc.com.uscmaps.example1.shubham.fbsearch.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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

import usc.com.uscmaps.example1.shubham.fbsearch.R;


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

    public ResultFragmentsAdapter(Context context, ArrayList<ArrayList<String>> values) {
        super(context, R.layout.custom_view_listview_main, values);
        this.mContext = context;
        this.values = values;
        this.sPref = mContext.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        curr_tab = sPref.getString("active_tab", "None");
//        Log.e(TAG, "getView: curr_tab" + curr_tab);

        View rowView = inflater.inflate(R.layout.custom_view_listview_main, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        ImageView imgViewUser = (ImageView) rowView.findViewById(R.id.icon);
        ImageView imgViewMoreDetails = (ImageView) rowView.findViewById(R.id.imgView_more_details);
        ImageView imgViewStarred = (ImageView) rowView.findViewById(R.id.imgView_starred);


        curr = values.get(position);
        textView.setText(curr.get(0));
        Picasso.with(this.getContext()).load(curr.get(2)).into(imgViewUser);

        if (checkStarred()) {
            imgViewStarred.setImageResource(R.mipmap.favorites_on);
            notifyDataSetChanged();
        } else {
            imgViewStarred.setImageResource(R.drawable.favorites_off);
        }


        return rowView;
    }


    /**
     * This function has a List and a Map. The list contains all tabs on which Add to Fav has been
     * clicked. So, for each item in this list, each HashMap key value is checked.
     *
     * This was done coz the setOffPageLimit is by default 1, and the right and left tab of the
     * current tab couldn't be set to active_tab, leading to the right left tabs to have un-starred
     * images
     *
     * @return true if the row show have starred image.
     *
     */
    private boolean checkStarred() {
        HashMap<String, ArrayList<String>> hMap = loadMap();
//        Log.e(TAG, "checkStarred: HashMap"+ hMap );

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
     * Loads Map from SharedPref
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
