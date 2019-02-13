package com.test.progresslayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TestFragment testFragment = new TestFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, testFragment).commitAllowingStateLoss();
    }
}
