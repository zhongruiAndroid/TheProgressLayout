package com.github.progress;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public interface ProgressInter {
    public void showError();

    public void showEmpty();

    public void showProgress();

    public void showContent();
    public void showNoNetwork();

    public View getNoNetworkView() ;

    public void setNoNetworkView(View noNetworkView);

    public View getErrorView();

    public void setErrorView(View errorView);

    public View getEmptyView();

    public void setEmptyView(View emptyView) ;

    public View getProgressView();

    public void setProgressView(View progressView);

    public List<Integer> getIgnoreViewId();

    public void setIgnoreViewId(List<Integer> ignoreViewId);

    public void addIgnoreViewId(List<Integer> ignoreViewId) ;

    public void addIgnoreViewId(Integer ignoreViewId);

    public void setErrorOnClickListener(ProgressListener.ErrorOnClickListener errorOnClickListener) ;

    public void setEmptyOnClickListener(ProgressListener.EmptyOnClickListener emptyOnClickListener);

    public void setProgressOnClickListener(ProgressListener.ProgressOnClickListener progressOnClickListener);
    public void setNoNetworkOnClickListener(ProgressListener.NoNetworkOnClickListener noNetworkOnClickListener);

    public void addView(View child, int index, ViewGroup.LayoutParams params);

}
