package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.widget.ShareButton;

/**
 * Created by Shubham on 4/10/17.
 */

public class DetailsActivity extends AppCompatActivity {
    private Context mContext = this;
    private DetailsActivitySectionsPagerAdapter mDetailsActivitySectionsPagerAdapter;
    private ViewPager viewPager;


    // share button
    private ShareButton shareButton;
    //image
    private Bitmap image;
    //counter
    private int counter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity_main);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        shareButton = (ShareButton) findViewById(R.id.share_btn1);
//        shareButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "ShareOnFB", Toast.LENGTH_SHORT).show();
//                postPicture();
//            }
//        });

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
            Toast.makeText(getApplicationContext(), "Fav, nothing hooked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_share_on_facebook) {
            Toast.makeText(getApplicationContext(), "Share + SEARCHEDQUERY", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(this, SharingActivity.class);
            startActivity(intent1);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class DetailsActivitySectionsPagerAdapter extends FragmentPagerAdapter {

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
