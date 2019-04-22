package com.github.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class ProgressLayoutHelper implements ProgressInter {

    private View errorView;
    private View emptyView;
    private View progressView;
    private View noNetworkView;

    public List<View> contentView = new ArrayList<View>();
    private List<Integer> ignoreViewId = new ArrayList<>();

    public final int status_error = -1;
    public final int status_empty = 0;
    public final int status_content = 1;
    public final int status_progress = 2;
    public final int status_noNetwork = 3;

    public int currentStatus = status_content;

    private int visibleFlag = GONE;


    public ProgressListener.ErrorOnClickListener errorOnClickListener;
    public ProgressListener.EmptyOnClickListener emptyOnClickListener;
    public ProgressListener.ProgressOnClickListener progressOnClickListener;
    public ProgressListener.NoNetworkOnClickListener noNetworkOnClickListener;

    public final int defAttr = R.attr.MyProgressLayoutStyle;

    private Context context;
    private ViewGroup rootView;

    public ProgressLayoutHelper(Context context, ViewGroup rootView) {
        this.context = context;
        this.rootView = rootView;
//        if (rootView instanceof LinearLayout) {
//            visibleFlag = GONE;
//        }
    }

    public void initAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressLayout, defStyleAttr, R.style.defaultProgressStyleRes);

        int status = typedArray.getInt(R.styleable.ProgressLayout_status, status_content);
        int progressViewId = typedArray.getResourceId(R.styleable.ProgressLayout_progressView, R.layout.default_progress);
        int errorViewId = typedArray.getResourceId(R.styleable.ProgressLayout_errorView, R.layout.default_error);
        int emptyViewId = typedArray.getResourceId(R.styleable.ProgressLayout_emptyView, R.layout.default_empty);
        int noNetworkViewId = typedArray.getResourceId(R.styleable.ProgressLayout_noNetworkView, R.layout.default_nonetwork);

        typedArray.recycle();

        currentStatus = status;

        LayoutInflater inflater = LayoutInflater.from(getContext());

        progressView = inflater.inflate(progressViewId, null);
        errorView = inflater.inflate(errorViewId, null);
        emptyView = inflater.inflate(emptyViewId, null);
        noNetworkView = inflater.inflate(noNetworkViewId, null);

        initAllStatusView();

    }

    public void initAllStatusView() {

        progressViewConfig();

        errorViewConfig();

        emptyViewConfig();

        noNetworkViewConfig();


    }

    private void emptyViewConfig() {

        emptyView.setTag(status_empty + "");


        setViewLayoutParams(emptyView);

        emptyView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (emptyOnClickListener != null) {
                    emptyOnClickListener.emptyOnClick();
                }
            }
        });

        if (currentStatus == status_empty) {
            emptyView.setVisibility(VISIBLE);
            attachViewToRootView(emptyView);
        } else {
            emptyView.setVisibility(visibleFlag);
        }
//        this.rootView.addView(emptyView);

    }

    private void noNetworkViewConfig() {

        noNetworkView.setTag(status_noNetwork + "");


        setViewLayoutParams(noNetworkView);

        noNetworkView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (noNetworkOnClickListener != null) {
                    noNetworkOnClickListener.noNetworkOnClick();
                }
            }
        });
        if (currentStatus == status_noNetwork) {
            noNetworkView.setVisibility(VISIBLE);
            attachViewToRootView(noNetworkView);
        } else {
            noNetworkView.setVisibility(visibleFlag);
        }

//        this.rootView.addView(noNetworkView);

    }

    private void errorViewConfig() {

        errorView.setTag(status_error + "");

        setViewLayoutParams(errorView);


        errorView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (errorOnClickListener != null) {
                    errorOnClickListener.errorOnClick();
                }
            }
        });

        if (currentStatus == status_error) {
            errorView.setVisibility(VISIBLE);
            attachViewToRootView(errorView);
        } else {
            errorView.setVisibility(visibleFlag);
        }

//        this.rootView.addView(errorView);
    }

    private void progressViewConfig() {

        progressView.setTag(status_progress + "");

        setViewLayoutParams(progressView);


        progressView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (progressOnClickListener != null) {
                    progressOnClickListener.progressOnClick();
                }
            }
        });

        if (currentStatus == status_progress) {
            progressView.setVisibility(VISIBLE);
            attachViewToRootView(progressView);
        } else {
            progressView.setVisibility(visibleFlag);
        }
//        this.rootView.addView(progressView);
    }

    private void setViewLayoutParams(View view) {
        if (this.rootView instanceof RelativeLayout) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            }
            layoutParams.addRule(CENTER_IN_PARENT);
            view.setLayoutParams(layoutParams);
        } else if (this.rootView instanceof LinearLayout) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            }
            layoutParams.gravity = Gravity.CENTER;
            view.setLayoutParams(layoutParams);
        } else if (this.rootView instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            }
            layoutParams.gravity = Gravity.CENTER;
            view.setLayoutParams(layoutParams);
        }
    }

    private void changeStatus(int status) {
        currentStatus = status;
        switch (currentStatus) {
            case status_error:
                errorView.setVisibility(VISIBLE);
                attachViewToRootView(errorView);

                progressView.setVisibility(visibleFlag);
                emptyView.setVisibility(visibleFlag);
                noNetworkView.setVisibility(visibleFlag);
                setContentViewVisibility(false);

                break;
            case status_empty:
                emptyView.setVisibility(VISIBLE);
                attachViewToRootView(emptyView);

                progressView.setVisibility(visibleFlag);
                errorView.setVisibility(visibleFlag);
                noNetworkView.setVisibility(visibleFlag);
                setContentViewVisibility(false);
                break;
            case status_content:
                progressView.setVisibility(visibleFlag);
                errorView.setVisibility(visibleFlag);
                emptyView.setVisibility(visibleFlag);
                noNetworkView.setVisibility(visibleFlag);
                setContentViewVisibility(true);
                break;
            case status_progress:
                progressView.setVisibility(VISIBLE);
                attachViewToRootView(progressView);

                errorView.setVisibility(visibleFlag);
                emptyView.setVisibility(visibleFlag);
                noNetworkView.setVisibility(visibleFlag);
                setContentViewVisibility(false);
                break;
            case status_noNetwork:
                noNetworkView.setVisibility(VISIBLE);
                attachViewToRootView(noNetworkView);

                progressView.setVisibility(visibleFlag);
                errorView.setVisibility(visibleFlag);
                emptyView.setVisibility(visibleFlag);
                setContentViewVisibility(false);
                break;
        }
    }

    private void setContentViewVisibility(boolean isShow) {
        for (View view : contentView) {
            //忽略指定view
            if (!ignoreViewId.contains(view.getId())) {
                if (rootView instanceof LinearLayout) {
                    view.setVisibility(isShow ? VISIBLE : View.GONE);
                } else {
                    view.setVisibility(isShow ? VISIBLE : visibleFlag);
                }
            }
        }
    }
    private void attachViewToRootView(View needAttachView){
        if(needAttachView==null){
            return;
        }
        if(this.rootView.indexOfChild(needAttachView)==-1){
            this.rootView.addView(needAttachView);
        }
    }
    @Override
    public void showError() {
        changeStatus(status_error);
    }

    @Override
    public void showEmpty() {
        changeStatus(status_empty);
    }

    @Override
    public void showProgress() {
        changeStatus(status_progress);
    }

    @Override
    public void showNoNetwork() {
        changeStatus(status_noNetwork);
    }

    @Override
    public void showContent() {
        changeStatus(status_content);
    }

    @Override
    public View getNoNetworkView() {
        return noNetworkView;
    }

    @Override
    public void setNoNetworkView(View noNetworkView) {
        if (noNetworkView == null) {
            return;
        }
        if (this.noNetworkView != null) {
            this.rootView.removeView(this.noNetworkView);
        }
        this.noNetworkView = noNetworkView;

        noNetworkViewConfig();


    }

    @Override
    public View getErrorView() {
        return errorView;
    }

    @Override
    public void setErrorView(View errorView) {
        if (errorView == null) {
            return;
        }
        if (this.errorView != null) {
            this.rootView.removeView(this.errorView);
        }
        this.errorView = errorView;

        errorViewConfig();


    }

    @Override
    public View getEmptyView() {
        return emptyView;
    }

    @Override
    public void setEmptyView(View emptyView) {
        if (emptyView == null) {
            return;
        }
        if (this.emptyView != null) {
            this.rootView.removeView(this.emptyView);
        }
        this.emptyView = emptyView;
        emptyViewConfig();


    }

    @Override
    public View getProgressView() {
        return progressView;
    }

    @Override
    public void setProgressView(View progressView) {
        if (progressView == null) {
            return;
        }
        if (this.progressView != null) {
            this.rootView.removeView(this.progressView);
        }
        this.progressView = progressView;
        progressViewConfig();

    }

    @Override
    public List<Integer> getIgnoreViewId() {
        return ignoreViewId;
    }

    @Override
    public void setIgnoreViewId(List<Integer> ignoreViewId) {
        if(ignoreViewId==null||ignoreViewId.size()==0){
            return;
        }
        this.ignoreViewId = ignoreViewId;

    }

    @Override
    public void addIgnoreViewId(List<Integer> ignoreViewId) {
        if(ignoreViewId==null||ignoreViewId.size()==0){
            return;
        }
        this.ignoreViewId.addAll(ignoreViewId);
    }

    @Override
    public void addIgnoreViewId(Integer ignoreViewId) {
        this.ignoreViewId.add(ignoreViewId);
    }

    @Override
    public void setErrorOnClickListener(ProgressListener.ErrorOnClickListener errorOnClickListener) {
        this.errorOnClickListener = errorOnClickListener;
    }

    @Override
    public void setEmptyOnClickListener(ProgressListener.EmptyOnClickListener emptyOnClickListener) {
        this.emptyOnClickListener = emptyOnClickListener;
    }

    @Override
    public void setProgressOnClickListener(ProgressListener.ProgressOnClickListener progressOnClickListener) {
        this.progressOnClickListener = progressOnClickListener;
    }

    @Override
    public void setNoNetworkOnClickListener(ProgressListener.NoNetworkOnClickListener noNetworkOnClickListener) {
        this.noNetworkOnClickListener = noNetworkOnClickListener;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child.getTag() == null || (!child.getTag().equals(status_empty + "") && !child.getTag().equals(status_error + "") && !child.getTag().equals(status_progress + "") && !child.getTag().equals(status_noNetwork + ""))) {
            contentView.add(child);
        }
    }

    private Context getContext() {
        return context;
    }

    private abstract class MyOnClickListener implements View.OnClickListener {
        private static final int MIN_CLICK_DELAY_TIME = 900;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }

        protected abstract void onNoDoubleClick(View v);
    }
}
