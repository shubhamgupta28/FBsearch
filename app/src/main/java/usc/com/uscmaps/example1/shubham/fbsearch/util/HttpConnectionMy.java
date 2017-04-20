package usc.com.uscmaps.example1.shubham.fbsearch.util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shubham on 4/10/17.
 */

public class HttpConnectionMy extends AsyncTask<String, Object, JSONObject> {
    private final String TAG = getClass().getSimpleName();
    public AsyncResponse delegate = null;


    public HttpConnectionMy(AsyncResponse async){
        this.delegate = async;

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject topLevel = null;
        try {
            URL url = new URL((String) strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }
//            Log.e(TAG, "builder: " + builder);

            topLevel = new JSONObject(builder.toString());
//            JSONObject main = topLevel.getJSONObject("main");
//            weather = String.valueOf(main.getDouble("temp"));

            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            Log.e("Async", "Catch doInBackground");
            e.printStackTrace();
        }
        return topLevel;
    }

    @Override
    protected void onPostExecute(JSONObject temp) {
//        Log.e(TAG, "onPostExecute: " );
        delegate.processFinish(temp);
    }
}
