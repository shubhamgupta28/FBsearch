package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity_main);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("userInput");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("userInput");
        }

//        bundleForFragment = new Bundle();
//        bundleForFragment.putString("userInput", newString);


        mResultActivitySectionsPagerAdapter = new ResultActivitySectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.pager_result_activity);
        viewPager.setAdapter(mResultActivitySectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
//        viewPager.setCurrentItem(0);

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        Log.e(TAG, "onPageSelected: 1" );
//                        addToSharedPref("user");
//                        break;
//                    case 1:
//                        Log.e(TAG, "onPageSelected: 2" );
//                        addToSharedPref("page");
//                        break;
//                    case 2:
//                        Log.e(TAG, "onPageSelected: 3" );
//                        addToSharedPref("event");
//                        break;
//                    case 3:
//                        Log.e(TAG, "onPageSelected: 4" );
//                        addToSharedPref("place");
//                        break;
//                    case 4:
//                        Log.e(TAG, "onPageSelected: 5" );
//                        addToSharedPref("group");
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutContainer);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mResultActivitySectionsPagerAdapter.getTabView(i));
        }
    }

    private void addToSharedPref(String active_tab) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("active_tab", active_tab);
        editor.commit();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class ResultActivitySectionsPagerAdapter extends FragmentPagerAdapter {
        public ResultActivitySectionsPagerAdapter(FragmentManager fm) {
            super(fm);
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
                    Fragment resultsFragmentUsers4 = new ResultsFragmentUsers();
//                    Log.e(TAG, "getItem: 4" );


//                    resultsFragmentUsers4.setArguments(bundleForFragment);
                    return resultsFragmentUsers4;

                case 4:
                    Fragment resultsFragmentUsers5 = new ResultsFragmentUsers();
//                    resultsFragmentUsers5.setRetainInstance(true);
//                    Log.e(TAG, "getItem: 5" );


//                    resultsFragmentUsers5.setArguments(bundleForFragment);
                    return resultsFragmentUsers5;

                default:
                    return null;
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
