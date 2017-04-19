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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import usc.com.uscmaps.example1.shubham.fbsearch.aboutMe.AboutMe;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = getClass().getSimpleName();
    //    private String TAG = "MainActivity";
    Context mContext = this;
    EditText editBox_user_input;
    Button btClear;
    Button btSearch;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    private CallbackManager callbackManager;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        DONE
        //TODO set tab a little uplifted
        //TODO PUt NUll check for albums like in Posts



        //TODO change FB SDK to AWS PHP call
        //TODO Set ShareFB page
        //TODO Set Favorites
        //TODO Set Places me location
        //TODO set paging in listView Results
        //TODO issue of data disappearing when tabs changed multiple times


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
                    //Call AWS for data
                    Toast.makeText(getApplicationContext(), "Entered: " + input, Toast.LENGTH_LONG).show();

//                    fetchFacebookData(input);

                    Intent intent = new Intent(mContext, ResultsActivity.class);
                    addToSharedPref(input, "user");

//                    intent.putExtra("userInput", input);
                    startActivity(intent);
                }
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        double lat = 40.712774, lon = -74.006091;
        String units = "imperial";
        String APP_ID = "212a21c6bfd9566936db301b5b629460";
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
                lat, lon, units, APP_ID);

        TextView textView = (TextView) findViewById(R.id.textView);
        startActivity(new Intent(this, FacebookActivity.class));


        Log.e(TAG, "onCreate: "+url );
//        new HttpConnectionMy(textView).execute(url);
    }


    private void addToSharedPref(String input, String currTab) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("input", input);
        editor.putString("active_tab", currTab);
        editor.apply();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
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
            Intent in = new Intent(this, AboutMe.class);
            startActivity(in);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
