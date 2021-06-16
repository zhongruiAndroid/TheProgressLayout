package com.test.progresslayout;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.progresslayout.ProgressLinearLayout;
import com.github.progresslayout.ProgressListener;

public class LinearlayoutActivity extends AppCompatActivity {


    ProgressLinearLayout prl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearlayout);
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
        });
        prl.setNoNetworkOnClickListener(new ProgressListener.NoNetworkOnClickListener() {
            @Override
            public void noNetworkOnClick() {
                Log.i("===", "===NoNetworkOnClickListener");
            }
        });




//        prl.setErrorView(getView(R.layout.progress_error));
        prl.setProgressView(getView(R.layout.app_loading_view));
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
//        prl.showContent();

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
