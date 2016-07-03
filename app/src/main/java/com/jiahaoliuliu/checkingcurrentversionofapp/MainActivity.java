package com.jiahaoliuliu.checkingcurrentversionofapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String APP_ID = "com.worldspotlightapp.android";
    private static final String APP_URL = "https://play.google.com/store/apps/details?id=" + APP_ID + "&hl=en";

    // The current version to check. This should be lower than the production
    // version so the app will notify about update
    private static final String CURRENT_VERSION = "1.10";

    // Views
    private TextView mSimpleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSimpleTextView = (TextView) findViewById(R.id.simple_text_view);

        // Check the current version
        checkCurrentVersion();
    }

    private void checkCurrentVersion() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, APP_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.v(TAG, "The response is " + response);

                    String newVersion =
                        Jsoup.parse(response).select("div[itemprop=softwareVersion]")
                        .first().ownText();
                    Log.v(TAG, "The new version is " + newVersion);

                    if (Float.valueOf(CURRENT_VERSION) < Float.valueOf(newVersion)) {
                        //show dialog
                        mSimpleTextView.setText("The online version is " + newVersion + ". You got to update the current version");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error requesting the web page");
                    mSimpleTextView.setText("Error on getting the version number");
                }
            }
        );

        queue.add(stringRequest);
    }
}

