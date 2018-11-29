package com.github.progress;

import android.view.View;

import java.util.List;

public interface ProgressInter {
    public interface ErrorOnClickListener{
        void errorOnClick();
    }
    public interface EmptyOnClickListener{
        void emptyOnClick();
    }
    public interface ProgressOnClickListener{
        void progressOnClick();
    }

    void getAllView(int status, final int progressViewId, int errorViewId, int emptyViewId);

    public void showError();

    public void showEmpty();

    public void showProgress();

    public void showContent();

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

    public void setErrorOnClickListener(ErrorOnClickListener errorOnClickListener) ;

    public void setEmptyOnClickListener(EmptyOnClickListener emptyOnClickListener);

    public void setProgressOnClickListener(ProgressOnClickListener progressOnClickListener);
}
