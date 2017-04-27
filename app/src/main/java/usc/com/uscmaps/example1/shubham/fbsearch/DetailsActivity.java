package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
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

    String clicked_name;
    String clicked_user_picture;
    ShareDialog shareDialog;
    CallbackManager callbackManager;
    private String isStarred;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbInit();
        setContentView(R.layout.details_activity_main);

        Intent iin = getIntent();
        Bundle bundle = iin.getExtras();

        if (bundle != null) {
            isStarred = bundle.getString("isStarred", "true");
//            Log.e(TAG, "onCreate: isStarred" +isStarred );
        }


        sPref = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        selected_item_name = sPref.getString("clicked_user_name", "No Name");
        clicked_name = sPref.getString("clicked_user_name", "No Name");
        clicked_user_picture = sPref.getString("clicked_user_picture", "No Name");

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

    private void fbInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
//
        MenuItem addToFav = menu.findItem(R.id.action_add_to_fav);
        MenuItem removeFromFav = menu.findItem(R.id.action_remove_from_fav);

        if (isStarred.equals("true")) {
            Log.e("favorite", "on");
            removeFromFav.setVisible(true);
            addToFav.setVisible(false);
            invalidateOptionsMenu();
        } else {
            Log.e("favorite", "off");
            removeFromFav.setVisible(false);
            addToFav.setVisible(true);
            invalidateOptionsMenu();
        }


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

            String active_tab = sPref.getString("active_tab", "1");
            String clicked_user_ID = sPref.getString("selected_listView_item", null);

            hMap = new HashMap<>();
            hMap = loadMap();

            if (hMap.containsKey(active_tab)) {
                ArrayList<String> list_of_UserID_temp = hMap.get(active_tab);
                if (!list_of_UserID_temp.contains(clicked_user_ID))
                    list_of_UserID_temp.add(clicked_user_ID);
                hMap.put(active_tab, list_of_UserID_temp);
            } else {
                ArrayList<String> list_of_UserID = new ArrayList<>();
                list_of_UserID.add(clicked_user_ID);
                hMap.put(active_tab, list_of_UserID);
            }
            saveMap(hMap);

            addActiveTabToPrefList(active_tab);
            Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_remove_from_fav) {

//            Map<String, ?> keys = sPref.getAll();
//
//            for (Map.Entry<String, ?> entry : keys.entrySet()) {
//                Log.e("map values", entry.getKey() + ": " +
//                        entry.getValue().toString());
//            }

            String active_tab = sPref.getString("active_tab", "0");
            String clicked_user_ID = sPref.getString("selected_listView_item", null);
            Log.e(TAG, "clicked_user_ID: " + clicked_user_ID + "active_tab: " + active_tab);

            hMap = new HashMap<>();
            hMap = loadMap();

            Log.e("detailsActivity", "onOptionsItemSelected1: " + hMap);
            if (hMap.containsKey(active_tab)) {
                Log.e(TAG, "containsKey: ");
                ArrayList<String> list_of_UserID_temp = hMap.get(active_tab);
//                int index = list_of_UserID_temp.indexOf(clicked_user_ID);
//                Log.e(TAG, "list_of_UserID_temp: "+list_of_UserID_temp );
//                list_of_UserID_temp.remove(clicked_user_ID);
//                Log.e(TAG, "onOptionsItemSelected: " + list_of_UserID_temp.remove(clicked_user_ID));
//                Log.e(TAG, "list_of_UserID_temp:2 "+list_of_UserID_temp );

                ArrayList<String> new_list_of_id = new ArrayList<>();
                for (String a : list_of_UserID_temp) {
                    if (!a.equals(clicked_user_ID)) {
                        new_list_of_id.add(a);
                    }
                }

                if (new_list_of_id.size() == 0) {
                    Log.e(TAG, "onOptionsItemSelected: here");
                    hMap.remove(active_tab);
                } else {
                    Log.e(TAG, "onOptionsItemSelected: here2");

                    hMap.put(active_tab, new_list_of_id);
                }
            }
            Log.e("detailsActivity", "onOptionsItemSelected2: " + hMap);
            Log.e(TAG, "-------------");

            saveMap(hMap);


            removeActiveTabFromPrefList(active_tab);

            Toast.makeText(getApplicationContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();

            return true;
        } else if (id == R.id.action_share_on_facebook) {

            Toast.makeText(this, "Sharing ", Toast.LENGTH_SHORT).show();
            callbackManager = CallbackManager.Factory.create();
            shareDialog = new ShareDialog(this);
            if (shareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(clicked_name)
                        .setContentUrl(Uri.parse("https://developers.facebook.com/"))
                        .setImageUrl(Uri.parse(clicked_user_picture))
                        .setContentDescription("FB SEARCH FROM USC CSCI571...")
                        .build();
                shareDialog.show(linkContent);
            }

            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Toast.makeText(getApplicationContext(), "You shared this post", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onSuccess: ");
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "Post sharing cancelled", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onCancel: ");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.e(TAG, "onError: ");

                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeActiveTabFromPrefList(String active_tab) {
        ArrayList<String> list_of_active_tabs = readListOfTabs();
//        ArrayList<String> new_list_of_tabs = new ArrayList<>();

        if(list_of_active_tabs.contains(active_tab)){
            list_of_active_tabs.remove(active_tab);
//            for (String a : list_of_active_tabs) {
//                if (!a.equals(active_tab)) {
//                    new_list_of_tabs.add(a);
//                }
//            }
        }
        addToListOfTabs(list_of_active_tabs);
    }



    /**
     * Final callback that posts the shareDialog in Facebook
     */
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        Log.d(TAG, "onActivityResult: " + requestCode + " : " + responseCode + " :  " + data);

        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

    private void addActiveTabToPrefList(String active_tab) {
        ArrayList<String> list_of_active_tabs = readListOfTabs();
        if (list_of_active_tabs == null) {
            list_of_active_tabs = new ArrayList<String>();
            list_of_active_tabs.add(active_tab);
        } else {
            list_of_active_tabs.add(active_tab);
        }

        addToListOfTabs(list_of_active_tabs);
    }

    private ArrayList<String> readListOfTabs() {
        Gson gson = new Gson();
        String json = sPref.getString("list_of_active_tabs", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
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
