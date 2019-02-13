package com.test.progresslayout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.progress.ProgressRelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class TestFragment extends Fragment {
    public TestFragment() {
    }
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressRelativeLayout rlLoad=view.findViewById(R.id.rlLoad);

        View inflate = getLayoutInflater().inflate(R.layout.app_loading_view, null);
        rlLoad.setProgressView(inflate);

        rlLoad.showProgress();


        final long time=System.currentTimeMillis();
        Log.i("=======","=======");
        List<String> list=new ArrayList<>();

        for (int i = 0; i < 400000; i++) {
            list.add(i+"");
        }
        long time2=System.currentTimeMillis();
        Log.i("=====","====="+(time-time2));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
