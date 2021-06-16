package com.test.progresslayout;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

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
    public void testMethod(View view){
        startActivity(new Intent(this,ViewStubActivity.class));
    }
}
