package com.test.progresslayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.progress.ProgressFrameLayout;
import com.github.progress.ProgressInter;
import com.github.progress.ProgressListener;
import com.github.progress.ProgressRelativeLayout;

public class FramelayoutActivity extends AppCompatActivity {
    ProgressFrameLayout prl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        initView();
    }

    private void initView() {
        prl = findViewById(R.id.prl);

        prl.setEmptyOnClickListener(new ProgressListener.EmptyOnClickListener() {
            @Override
            public void emptyOnClick() {
                Log.i("===", "===EmptyOnClickListener");
            }
        });
        prl.setErrorOnClickListener(new ProgressListener.ErrorOnClickListener() {
            @Override
            public void errorOnClick() {
                Log.i("===", "===ErrorOnClickListener");
                prl.showProgress( );
                prl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        prl.showError();
                    }
                },1000);
            }
        });
        prl.setProgressOnClickListener(new ProgressListener.ProgressOnClickListener() {
            @Override
            public void progressOnClick() {
                Log.i("===", "===ProgressOnClickListener");
            }
        }); prl.setNoNetworkOnClickListener(new ProgressListener.NoNetworkOnClickListener() {
            @Override
            public void noNetworkOnClick() {
                Log.i("===", "===NoNetworkOnClickListener");
            }
        });




//        prl.setErrorView(getView(R.layout.progress_error));
//        prl.setProgressView(getView(R.layout.progress_progress));
//        prl.setEmptyView(getView(R.layout.progress_empty));


//        prl.showError();
//        prl.showEmpty();
        prl.showProgress( );
        prl.postDelayed(new Runnable() {
            @Override
            public void run() {
                prl.showError();
            }
        },1000);

    }

    public void empty(View view) {
        prl.showEmpty();
    }

    public void error(View view) {
        prl.showError();
    }

    public void progress(View view) {
        prl.showProgress();
        prl.postDelayed(new Runnable() {
            @Override
            public void run() {
                prl.showError();
            }
        },1000);
    }

    public void content(View view) {
        prl.showContent();
    }
    public void noNetwork(View view) {
        prl.showNoNetwork();
    }

    public View getView(int resId) {
        return getLayoutInflater().inflate(resId, null);
    }
}
