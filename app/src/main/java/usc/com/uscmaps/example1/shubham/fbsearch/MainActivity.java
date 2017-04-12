package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import usc.com.uscmaps.example1.shubham.fbsearch.aboutMe.AboutMe;
import usc.com.uscmaps.example1.shubham.fbsearch.util.GetAsyncTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = getClass().getSimpleName();
//    private String TAG = "MainActivity";
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText editTextFBsearch = (EditText) findViewById(R.id.editBoxSearchQuery);
        Button btClear = (Button) findViewById(R.id.btClear);
        Button btSearch = (Button) findViewById(R.id.btSearch);

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFBsearch.setText("");
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input  = editTextFBsearch.getText().toString();
//                Log.e(TAG, input);

                if(input.length() == 0 || input == null){
                    Toast.makeText(getApplicationContext(), "Please enter a keyword!", Toast.LENGTH_LONG).show();
                }
                else{
                    //Call AWS for data

                    Intent intent = new Intent(mContext, ResultsActivity.class);
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
        new GetAsyncTask(textView).execute(url);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
