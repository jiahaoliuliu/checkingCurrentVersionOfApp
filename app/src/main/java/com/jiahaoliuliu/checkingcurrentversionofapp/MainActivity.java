package com.jiahaoliuliu.checkingcurrentversionofapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String APP_ID = "com.worldspotlightapp.android";

    // The current version to check. This should be lower than the production
    // version so the app will notify about update
    private static final String CURRENT_VERSION = "1.10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
