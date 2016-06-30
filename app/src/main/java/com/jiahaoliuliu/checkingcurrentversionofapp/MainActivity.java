package com.jiahaoliuliu.checkingcurrentversionofapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String APP_ID = "com.worldspotlightapp.android";

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
        GetVersionCode getVersionCode = new GetVersionCode();
        getVersionCode.execute();
    }

    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="
                    + APP_ID
                    + "&hl=en")
                    .timeout(30000)
                    .userAgent(
                        "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            Log.v(TAG, "The current online version is " + onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(CURRENT_VERSION) < Float.valueOf(onlineVersion)) {
                    //show dialog
                    mSimpleTextView.setText("The online version is " + onlineVersion + ". You got to update the current version");
                }
            }
            Log.d(TAG, "Current version " + CURRENT_VERSION + "playstore version " + onlineVersion);
        }
    }
}

