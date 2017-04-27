package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Shubham on 4/10/17.
 */

public class DetailsActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    private Context mContext = this;
    private DetailsActivitySectionsPagerAdapter mDetailsActivitySectionsPagerAdapter;
    private ViewPager viewPager;
    String selected_item_name;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences sPref;
    HashMap<String, ArrayList<String>> hMap = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity_main);


        sPref = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        selected_item_name = sPref.getString("clicked_user_name", "No Name");

        mDetailsActivitySectionsPagerAdapter = new DetailsActivitySectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager_details_activity);
        viewPager.setAdapter(mDetailsActivitySectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutContainerDetailsActivity);
        tabLayout.setupWithViewPager(viewPager);


        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mDetailsActivitySectionsPagerAdapter.getTabView(i));
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }



    //
//
//        @Override
//    public void onBackPressed() {
//
////        super.onBackPressed();
//        finish();
////        getFragmentManager().popBackStack();
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_add_to_fav) {

//            Map<String, ?> keys = sPref.getAll();
//
//            for (Map.Entry<String, ?> entry : keys.entrySet()) {
//                Log.e("map values", entry.getKey() + ": " +
//                        entry.getValue().toString());
//            }

            String active_tab = sPref.getString("active_tab", "1");
            String clicked_user_ID = sPref.getString("selected_listView_item", null);
//            Log.e(TAG, "active_tab: "+active_tab );

            hMap = new HashMap<>();
            hMap = loadMap();

//            Log.e("detailsActivity", "onOptionsItemSelected: " + hMap );
            if (hMap.containsKey(active_tab)) {
//                Log.e(TAG, "containsKey: " );
                ArrayList<String> list_of_UserID_temp = hMap.get(active_tab);
                if(!list_of_UserID_temp.contains(clicked_user_ID))
                    list_of_UserID_temp.add(clicked_user_ID);
                hMap.put(active_tab, list_of_UserID_temp);
            } else {
//                Log.e(TAG, "Not containsKey: " );
                ArrayList<String> list_of_UserID = new ArrayList<>();
                list_of_UserID.add(clicked_user_ID);
                hMap.put(active_tab, list_of_UserID);
            }
//            Log.e("detailsActivity", "onOptionsItemSelected: " + hMap );
//            Log.e(TAG, "-------------");

            saveMap(hMap);


            addActiveTabToPrefList(active_tab);
            Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();


            return true;
        } else if (id == R.id.action_share_on_facebook) {
            Toast.makeText(getApplicationContext(), "Sharing " + selected_item_name + "!", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(this, SharingActivity.class);
            startActivity(intent1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addActiveTabToPrefList(String active_tab) {
        ArrayList<String> list_of_active_tabs = readListOfTabs();
        if(list_of_active_tabs == null){
            list_of_active_tabs = new ArrayList<String>();
            list_of_active_tabs.add(active_tab);
        }else{
            list_of_active_tabs.add(active_tab);
        }

        addToListOfTabs(list_of_active_tabs);
    }

    private ArrayList<String> readListOfTabs() {
        Gson gson = new Gson();
        String json = sPref.getString("list_of_active_tabs", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> arrayList = gson.fromJson(json, type);
        return arrayList;
    }

    private void addToListOfTabs(ArrayList<String> list_of_active_tabs) {
        SharedPreferences.Editor editor = sPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list_of_active_tabs);
        editor.putString("list_of_active_tabs", json);
        editor.commit();
    }





    /**
     * Saves the clicked item ID to a Hashmap to SharedPref
     *
     * @param inputMap
     */
    private void saveMap(Map<String, ArrayList<String>> inputMap) {

        if (sPref != null) {
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = sPref.edit();
            editor.remove("My_map").commit();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
    }

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
     * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class DetailsActivitySectionsPagerAdapter extends FragmentStatePagerAdapter {

        public DetailsActivitySectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DetailsFragmentAlbums();
                case 1:
                    return new DetailsFragmentPosts();
                default:
                    return null;
            }
        }

        /**
         * Show 2 total pages.
         *
         * @return The number of pages you want to display
         */
        @Override
        public int getCount() {
            return 2;
        }

        private String tabTitles[] = new String[]{"Albums", "Posts"};
        private int[] imageResId = {R.drawable.albums, R.drawable.posts};

        /**
         * To set the tabs with custom images and text, (SectionPagerAdapter)
         *
         * @param position refers to the position of the item.
         * @return view
         */
        public View getTabView(int position) {
//            https://guides.codepath.com/android/Google-Play-Style-Tabs-using-TabLayout
            View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.custom_tab_textview);
            tv.setText(tabTitles[position]);
            ImageView img = (ImageView) v.findViewById(R.id.custom_tab_image);
            img.setImageResource(imageResId[position]);
            return v;
        }
    }
}
