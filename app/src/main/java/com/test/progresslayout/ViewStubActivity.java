package com.test.progresslayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

public class ViewStubActivity extends AppCompatActivity {
    Button btShow;
    Button btHidden;
    ViewStub viewStub;
    TextView framelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        btShow = findViewById(R.id.btShow);
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStub.setVisibility(View.VISIBLE);
            }
        });

        btHidden = findViewById(R.id.btHidden);
        btHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStub.setVisibility(View.GONE);
            }
        });

        viewStub = findViewById(R.id.viewStub);
//        View inflate = viewStub.inflate();
        viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                Log.i("=========","=========onInflate");
//                framelayout = findViewById(R.id.framelayout);
                Log.i("==========","===2========"+(framelayout!=null));
            }
        });

        framelayout = findViewById(R.id.framelayout);
        Log.i("==========","===1========"+(framelayout!=null));
    }
}
