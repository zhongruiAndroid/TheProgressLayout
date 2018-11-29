package com.test.progresslayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.progress.ProgressRelativeLayout;

public class MainActivity extends AppCompatActivity {
    ProgressRelativeLayout prl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prl = findViewById(R.id.prl);

        prl.setEmptyOnClickListener(new ProgressRelativeLayout.EmptyOnClickListener() {
            @Override
            public void emptyOnClick() {
                Log.i("===", "===EmptyOnClickListener");
            }
        });
        prl.setErrorOnClickListener(new ProgressRelativeLayout.ErrorOnClickListener() {
            @Override
            public void errorOnClick() {
                Log.i("===", "===ErrorOnClickListener");
            }
        });
        prl.setProgressOnClickListener(new ProgressRelativeLayout.ProgressOnClickListener() {
            @Override
            public void progressOnClick() {
                Log.i("===", "===ProgressOnClickListener");
            }
        });


        prl.showError( );
        prl.showEmpty( );
        prl.showProgress( );


        prl.setErrorView(getView(R.layout.progress_error));
//        prl.setProgressView(getView(R.layout.progress_progress));
        prl.setEmptyView(getView(R.layout.progress_empty));

        prl.showError();

    }

    public void empty(View view) {
        prl.showEmpty( );
    }

    public void error(View view) {
        prl.showError( );
    }

    public void progress(View view) {
        prl.showProgress( );
    }

    public void content(View view) {
        prl.showContent();
    }

    public View getView(int resId) {
        return getLayoutInflater().inflate(resId, null);
    }
}
