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

/**
 * Created by Shubham on 4/8/17.
 */

public class ResultsActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();
    private ResultActivitySectionsPagerAdapter mResultActivitySectionsPagerAdapter;
    private ViewPager viewPager;
    private Context mContext = this;
    private String newString;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences sPref;
    private String active_tab;
    private TabLayout tabLayout;
    //    boolean loadCurrentTab = false;
    private String last_active_tab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity_main);

        sPref = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        active_tab = sPref.getString("active_tab", "None");
        last_active_tab = sPref.getString("last_active_tab", "0");

//        Log.e(TAG, "onCreate: ResultActivity last_active_tab: " + Integer.parseInt(last_active_tab));

//        Map<String, ?> keys = sPref.getAll();
//        for (Map.Entry<String, ?> entry : keys.entrySet()) {
//            Log.e(TAG, "map values: "+ entry.getKey() + ": " +
//                    entry.getValue().toString());
//        }


        mResultActivitySectionsPagerAdapter = new ResultActivitySectionsPagerAdapter(getSupportFragmentManager());
//        mResultActivitySectionsPagerAdapter.notifyDataSetChanged();

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.pager_result_activity);
        viewPager.setAdapter(mResultActivitySectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(Integer.parseInt(last_active_tab));

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutContainer);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mResultActivitySectionsPagerAdapter.getTabView(i));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.e(TAG, "onTabSelected: ");
                addToSharedPref("" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Log.e(TAG, "onTabUnselected: ");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Log.e(TAG, "onTabReselected: " );
                addToSharedPref("" + tab.getPosition());
            }
        });

    }

    private void addToSharedPref(String active_tab) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("active_tab", active_tab);
        editor.apply();
    }


    /**
     * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class ResultActivitySectionsPagerAdapter extends FragmentStatePagerAdapter {
        public ResultActivitySectionsPagerAdapter(FragmentManager fm) {
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
                    Fragment resultsFragmentUsers1 = new ResultsFragmentUsers();
//                    resultsFragmentUsers1.setRetainInstance(true);
//                    Log.e(TAG, "getItem: 1" );
//                    addToSharedPref("user");
//                    resultsFragmentUsers1.setArguments(bundleForFragment);

                    return resultsFragmentUsers1;

                case 1:
                    Fragment resultsFragmentUsers2 = new ResultsFragmentPages();
//                    Log.e(TAG, "getItem: 2" );


//                    resultsFragmentUsers2.setArguments(bundleForFragment);
                    return resultsFragmentUsers2;

                case 2:
                    Fragment resultsFragmentUsers3 = new ResultsFragmentEvents();
//                    Log.e(TAG, "getItem: 3" );


//                    resultsFragmentUsers3.setArguments(bundleForFragment);
                    return resultsFragmentUsers3;

                case 3:
                    Fragment resultsFragmentUsers4 = new ResultsFragmentPlaces();
//                    Log.e(TAG, "getItem: 4" );


//                    resultsFragmentUsers4.setArguments(bundleForFragment);
                    return resultsFragmentUsers4;

                case 4:
                    Fragment resultsFragmentUsers5 = new ResultsFragmentGroups();
//                    resultsFragmentUsers5.setRetainInstance(true);
//                    Log.e(TAG, "getItem: 5" );


//                    resultsFragmentUsers5.setArguments(bundleForFragment);
                    return resultsFragmentUsers5;

                default:
                    return new ResultsFragmentUsers();
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
}
