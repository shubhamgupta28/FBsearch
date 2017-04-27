package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Shubham on 4/19/17.
 */

public class FavoritesActivity extends AppCompatActivity {
    private Context mContext = this;
    private String TAG = getClass().getSimpleName();
    private FavoritesActivitySectionsPagerAdapter mResultActivitySectionsPagerAdapter;
    private ViewPager viewPager;
    SharedPreferences sPref;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String  last_active_tab_favorites;
    TabLayout tabLayout;

    private ArrayList<String> userTabList = new ArrayList<>();
    private ArrayList<String> placesTabList = new ArrayList<>();
    private ArrayList<String> pagesTabList = new ArrayList<>();
    private ArrayList<String> groupsTabList = new ArrayList<>();
    private ArrayList<String> eventsTabList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity_main);

        /**
         * To set the default tab as User tab
         */
        sPref = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        addToSharedPref("0");
        last_active_tab_favorites = sPref.getString("last_active_tab_favorites", "0");


        mResultActivitySectionsPagerAdapter = new FavoritesActivitySectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.pager_favorites_activity);
        viewPager.setAdapter(mResultActivitySectionsPagerAdapter);
        viewPager.setCurrentItem(Integer.parseInt(last_active_tab_favorites));
        viewPager.setOffscreenPageLimit(0);

         tabLayout = (TabLayout) findViewById(R.id.tabLayoutContainerFavorites);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mResultActivitySectionsPagerAdapter.getTabView(i));
        }

        HashMap<String, ArrayList<String>> IDmap = loadMap();

        for (String a : IDmap.keySet()) {
            switch (a) {
                case "0":
                    userTabList = IDmap.get("0");
                    Log.e(TAG, "onCreate: userTabList"+userTabList );
                    break;
                case "1":
                    pagesTabList = IDmap.get("1");
                    break;
                case "2":
                    eventsTabList = IDmap.get("2");
                    break;
                case "3":
                    placesTabList = IDmap.get("3");
                    break;
                case "4":
                    groupsTabList = IDmap.get("4");
                    break;
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                addToSharedPref("" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                addToSharedPref("" + tab.getPosition());
            }
        });
    }



    private void addToSharedPref(String active_tab) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("active_tab", active_tab);
        editor.commit();
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
    public class FavoritesActivitySectionsPagerAdapter extends FragmentStatePagerAdapter {
        public FavoritesActivitySectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void notifyDataSetChanged() {
            Log.e(TAG, "notifyDataSetChanged: ");
            super.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment resultsFragmentUsers1;
                    if (userTabList != null || userTabList.size() != 0) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArray("userIDlist", userTabList.toArray(new String[userTabList.size()]));
                        resultsFragmentUsers1 = new FavoritesFragmentUsers();
                        resultsFragmentUsers1.setArguments(bundle1);
                    } else {
                        resultsFragmentUsers1 = new FavoritesFragmentUsers();
                    }
                    return resultsFragmentUsers1;

                case 1:
                    Fragment resultsFragmentUsers2;
                    if (pagesTabList != null || pagesTabList.size() != 0) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArray("userIDlist", pagesTabList.toArray(new String[pagesTabList.size()]));
                        resultsFragmentUsers2 = new FavoritesFragementPages();
                        resultsFragmentUsers2.setArguments(bundle1);
                    } else {
                        resultsFragmentUsers2 = new FavoritesFragementPages();
                    }
                    return resultsFragmentUsers2;

                case 2:
                    Fragment resultsFragmentUsers3;
                    if (eventsTabList != null || eventsTabList.size() != 0) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArray("userIDlist", eventsTabList.toArray(new String[eventsTabList.size()]));
                        resultsFragmentUsers3 = new FavoritesFragementEvents();
                        resultsFragmentUsers3.setArguments(bundle1);
                    } else {
                        resultsFragmentUsers3 = new FavoritesFragementEvents();
                    }
                    return resultsFragmentUsers3;

                case 3:
                    Fragment resultsFragmentUsers4;
                    if (placesTabList != null || placesTabList.size() != 0) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArray("userIDlist", placesTabList.toArray(new String[placesTabList.size()]));
                        resultsFragmentUsers4 = new FavoritesFragementPlaces();
                        resultsFragmentUsers4.setArguments(bundle1);
                    } else {
                        resultsFragmentUsers4 = new FavoritesFragementPlaces();
                    }
                    return resultsFragmentUsers4;

                case 4:
                    Fragment resultsFragmentUsers5;
                    if (groupsTabList != null || groupsTabList.size() != 0) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArray("userIDlist", groupsTabList.toArray(new String[groupsTabList.size()]));
                        resultsFragmentUsers5 = new FavoritesFragementGroups();
                        resultsFragmentUsers5.setArguments(bundle1);
                    } else {
                        resultsFragmentUsers5 = new FavoritesFragementGroups();
                    }
                    return resultsFragmentUsers5;

                default:
                    return new FavoritesFragmentUsers();
            }
        }

        /**
         * Show 5 total pages.
         *
         * @return The number of pages you want to display
         */
        @Override
        public int getCount() {
            return 5;
        }


        private String tabTitles[] = new String[]{"Users", "Pages", "Events", "Places", "Groups"};
        private int[] imageResId = {R.drawable.users, R.drawable.pages, R.drawable.events,
                R.drawable.places, R.drawable.groups};

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

//    @Override
//    protected void onRestart() {
//        Log.e(TAG, "onRestart: " );
//        super.onRestart();
//    }

}
