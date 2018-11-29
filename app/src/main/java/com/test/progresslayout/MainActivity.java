package com.test.progresslayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.progress.ProgressRelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void linearlayout(View view){
        startActivity(new Intent(this,LinearlayoutActivity.class));
    }
    public void framelayout(View view){
        startActivity(new Intent(this,FramelayoutActivity.class));
    }
    public void relativelayout(View view){
        startActivity(new Intent(this,RelativelayoutActivity.class));
    }
}
