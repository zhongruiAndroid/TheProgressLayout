package com.github.progress;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/***
 *   created by zhongrui on 2018/9/21
 */
public class ProgressFrameLayout extends FrameLayout implements ProgressInter {

    private ProgressLayoutHelper layoutHelper;

    public ProgressFrameLayout(Context context) {
        super(context);
        layoutHelper = new ProgressLayoutHelper(getContext(), this);
        initAttr(null, layoutHelper.defAttr);
    }

    public ProgressFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        layoutHelper = new ProgressLayoutHelper(getContext(), this);
        initAttr(attrs, layoutHelper.defAttr);
    }

    public ProgressFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutHelper = new ProgressLayoutHelper(getContext(), this);
        initAttr(attrs, defStyleAttr);
    }

    private void initAttr(AttributeSet attrs, int defStyleAttr) {
        layoutHelper.initAttr(attrs,defStyleAttr);
        /*TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressRelativeLayout, defStyleAttr, R.style.DefaultProgressStyle);
        int status = typedArray.getInt(R.styleable.ProgressRelativeLayout_status, layoutHelper.status_content);

        layoutHelper.currentStatus = status;

        int progressViewId = typedArray.getResourceId(R.styleable.ProgressRelativeLayout_progressView, -1);
        int errorViewId = typedArray.getResourceId(R.styleable.ProgressRelativeLayout_errorView, -1);
        int emptyViewId = typedArray.getResourceId(R.styleable.ProgressRelativeLayout_emptyView, -1);

        getAllView(status, progressViewId, errorViewId, emptyViewId);

        typedArray.recycle();*/

    }




    @Override
    public void showError() {
        layoutHelper.showError();
    }

    @Override
    public void showEmpty() {
        layoutHelper.showEmpty();
    }

    @Override
    public void showProgress() {
        layoutHelper.showProgress();
    }

    @Override
    public void showContent() {
        layoutHelper.showContent();
    }

    @Override
    public void showNoNetwork() {
        layoutHelper.showNoNetwork();
    }

    @Override
    public View getNoNetworkView() {
        return layoutHelper.getNoNetworkView();
    }

    @Override
    public void setNoNetworkView(View noNetworkView) {
        layoutHelper.setNoNetworkView(noNetworkView);
    }

    @Override
    public View getErrorView() {
        return layoutHelper.getErrorView();
    }

    @Override
    public void setErrorView(View errorView) {
        layoutHelper.setErrorView(errorView);
    }

    @Override
    public View getEmptyView() {
        return layoutHelper.getEmptyView();
    }

    @Override
    public void setEmptyView(View emptyView) {
        layoutHelper.setEmptyView(emptyView);
    }

    @Override
    public View getProgressView() {
        return layoutHelper.getProgressView();
    }

    @Override
    public void setProgressView(View progressView) {
        layoutHelper.setProgressView(progressView);
    }

    @Override
    public List<Integer> getIgnoreViewId() {
        return layoutHelper.getIgnoreViewId();
    }

    @Override
    public void setIgnoreViewId(List<Integer> ignoreViewId) {
        layoutHelper.setIgnoreViewId(ignoreViewId);
    }

    @Override
    public void addIgnoreViewId(List<Integer> ignoreViewId) {
        layoutHelper.addIgnoreViewId(ignoreViewId);
    }

    @Override
    public void addIgnoreViewId(Integer ignoreViewId) {
        layoutHelper.addIgnoreViewId(ignoreViewId);
    }

    @Override
    public void setErrorOnClickListener(ErrorOnClickListener errorOnClickListener) {
        layoutHelper.setErrorOnClickListener(errorOnClickListener);
    }

    @Override
    public void setEmptyOnClickListener(EmptyOnClickListener emptyOnClickListener) {
        layoutHelper.setEmptyOnClickListener(emptyOnClickListener);
    }

    @Override
    public void setProgressOnClickListener(ProgressOnClickListener progressOnClickListener) {
        layoutHelper.setProgressOnClickListener(progressOnClickListener);
    }

    @Override
    public void setNoNetworkOnClickListener(NoNetworkOnClickListener noNetworkOnClickListener) {
        layoutHelper.setNoNetworkOnClickListener(noNetworkOnClickListener);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        layoutHelper.addView(child,index,params);
    }
}
