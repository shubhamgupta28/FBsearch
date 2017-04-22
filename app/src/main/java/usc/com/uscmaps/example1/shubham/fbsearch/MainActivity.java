package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = getClass().getSimpleName();
    //    private String TAG = "MainActivity";
    Context mContext = this;
    EditText editBox_user_input;
    Button btClear;
    Button btSearch;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    protected DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        DONE
        //TODO set tab a little uplifted
        //TODO PUt NUll check for albums like in Posts
        //TODO change FB SDK to AWS PHP call
        //TODO Set ShareFB page
        //TODO set paging in listView Results
        //TODO Set Favorites
        //TODO Set Favorites With remove
        //TODO collapse other albums
        //TODO Star shine golden on FAv
        //TODO check if a item is added multiple times in My_map
        //TODO issue of data disappearing when tabs changed multiple times




        //TODO only the current selected tabs show starred icon
        //TODO switch off next button if list <= 10
        //TODO Set Places me location
        //TODO the Fav list ID of Events not working
        //TODO Fb shared post displaying wierd, show You shared this post after sharing
        //TODO back button, onBackPressed come back to the same tab
        //TODO remove this line so that Fav stay after app close -- editor.remove("My_map").commit();
        //TODO Add Hamburger to Favorties activity, but not to Results





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btClear = (Button) findViewById(R.id.btClear);
        btSearch = (Button) findViewById(R.id.btSearch);
        editBox_user_input = (EditText) findViewById(R.id.editBoxSearchQuery);

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBox_user_input.setText("");
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editBox_user_input.getText().toString();
//                Log.e(TAG, input);

                if (input.length() == 0 || input == null) {
                    Toast.makeText(getApplicationContext(), "Please enter a keyword!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(mContext, ResultsActivity.class);
                    addToSharedPref(input, "0");

                    startActivity(intent);
                }
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        startActivity(new Intent(this, FacebookActivity.class));

    }


    private void addToSharedPref(String input, String currTab) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("input", input);
        editor.putString("active_tab", currTab);
//        editor.remove("My_map").commit();
//        editor.remove("list_of_active_tabs").commit();
        editor.apply();
    }


//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

//    @Overridew
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about_me) {
            Intent in = new Intent(this, AboutMeActivity.class);
            startActivity(in);
        }
        if (id == R.id.home_me) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }
        if (id == R.id.favorites_me) {
            Intent in = new Intent(this, FavoritesActivity.class);
            startActivity(in);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
