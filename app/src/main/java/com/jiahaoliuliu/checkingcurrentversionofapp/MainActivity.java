package com.jiahaoliuliu.checkingcurrentversionofapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.model.Market;

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

        // Link the views
        mSimpleTextView = (TextView) findViewById(R.id.simple_text_view);

        // Check the latest installed versions
        checkLatestVersion();
    }

    private void checkLatestVersion() {
        final MarketSession marketSession = new MarketSession();
        marketSession.getContext().setAndroidId(APP_ID);
        String query = "maps";
        Market.AppsRequest appsRequest = Market.AppsRequest.newBuilder().setQuery(query)
            .setStartIndex(0).setEntriesCount(10).setWithExtendedInfo(true).build();
        marketSession.append(appsRequest, new MarketSession.Callback<Market.AppsResponse>() {
            @Override public void onResult(Market.ResponseContext responseContext,
                Market.AppsResponse appsResponse) {
                    // Log
                Log.v(TAG, "The response context is " + responseContext + ", and the app response is " +
                    appsResponse);
                //marketSession.flush();
            }
        });
    }
}
