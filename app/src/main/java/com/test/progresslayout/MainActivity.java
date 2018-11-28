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



        prl.showError(new ProgressRelativeLayout.ErrorOnClickListener() {
            @Override
            public void errorOnClick() {
                Log.i("===", "===errorOnClick22222");
            }
        });
        prl.showEmpty(new ProgressRelativeLayout.EmptyOnClickListener() {
            @Override
            public void emptyOnClick() {
                Log.i("===", "===emptyOnClick22222");
            }
        });
        prl.showProgress(new ProgressRelativeLayout.ProgressOnClickListener() {
            @Override
            public void progressOnClick() {
                Log.i("===", "===progressOnClick22222");
            }
        });



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

        prl.setErrorView(getView(R.layout.progress_error));
        prl.setProgressView(getView(R.layout.progress_progress));
        prl.setEmptyView(getView(R.layout.progress_empty));

        prl.showError();
    }

    public void empty(View view) {
        prl.showEmpty(new ProgressRelativeLayout.EmptyOnClickListener() {
            @Override
            public void emptyOnClick() {
                Log.i("===", "===emptyOnClick");
            }
        });
    }

    public void error(View view) {
        prl.showError(new ProgressRelativeLayout.ErrorOnClickListener() {
            @Override
            public void errorOnClick() {
                Log.i("===", "===errorOnClick");
            }
        });
    }

    public void progress(View view) {
        prl.showProgress(new ProgressRelativeLayout.ProgressOnClickListener() {
            @Override
            public void progressOnClick() {
                Log.i("===", "===progressOnClick");
            }
        });
    }

    public void content(View view) {
        prl.showContent();
    }

    public View getView(int resId) {
        return getLayoutInflater().inflate(resId, null);
    }
}
