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

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class ProgressLayoutHelper implements ProgressInter{

    private View errorView;
    private View emptyView;
    private View progressView;

    public List<View> contentView = new ArrayList<View>();
    private List<Integer> ignoreViewId =new ArrayList<>();

    public final int status_error=-1;
    public final int status_empty=0;
    public final int status_content=1;
    public final int status_progress=2;

    public int currentStatus=status_content;


    public ProgressInter.ErrorOnClickListener errorOnClickListener ;
    public ProgressInter.EmptyOnClickListener emptyOnClickListener ;
    public ProgressInter.ProgressOnClickListener progressOnClickListener ;

    public final int defAttr=R.attr.ProgressLayoutDefStyle;

    private Context context;
    private ViewGroup rootView;
    public ProgressLayoutHelper(Context context,ViewGroup rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    public void initAttr(AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressLayout, defStyleAttr, R.style.DefaultProgressStyle);

        int status = typedArray.getInt(R.styleable.ProgressLayout_status, status_content);
        int progressViewId = typedArray.getResourceId(R.styleable.ProgressLayout_progressView, -1);
        int errorViewId = typedArray.getResourceId(R.styleable.ProgressLayout_errorView, -1);
        int emptyViewId = typedArray.getResourceId(R.styleable.ProgressLayout_emptyView, -1);


        currentStatus = status;

        getAllView(status, progressViewId, errorViewId, emptyViewId);

        typedArray.recycle();
    }

    @Override
    public void getAllView(int status, int progressViewId, int errorViewId, int emptyViewId) {
        if(progressViewId==-1){
            TextView textView = new TextView(getContext());
            textView.setText("loading");
            progressView=textView;
        }else{
            progressView= LayoutInflater.from(getContext()).inflate(progressViewId,null);
        }
        progressViewConfig();



        if (errorViewId == -1) {
            TextView textView = new TextView(getContext());
            textView.setText("error");
            errorView=textView;
        }else{
            errorView=LayoutInflater.from(getContext()).inflate(errorViewId,null);
        }
        errorViewConfig();



        if(emptyViewId==-1){
            TextView textView = new TextView(getContext());
            textView.setText("empty");
            emptyView=textView;
        }else{
            emptyView= LayoutInflater.from(getContext()).inflate(emptyViewId,null);
        }
        emptyViewConfig();


        switch (status){
            case status_error:
                progressView.setVisibility(GONE);
                errorView.setVisibility(VISIBLE);
                emptyView.setVisibility(GONE);
                break;
            case status_empty:
                progressView.setVisibility(GONE);
                errorView.setVisibility(GONE);
                emptyView.setVisibility(VISIBLE);
                break;
            case status_content:
                progressView.setVisibility(GONE);
                errorView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                break;
            case status_progress:
                progressView.setVisibility(VISIBLE);
                errorView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                break;
        }

    }

    private void emptyViewConfig() {

        emptyView.setTag(status_empty+"");

        if(this.rootView instanceof RelativeLayout){
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            emptyView.setLayoutParams(layoutParams);
        }else if(this.rootView instanceof LinearLayout){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity=Gravity.CENTER;
            emptyView.setLayoutParams(layoutParams);
        }else if(this.rootView instanceof FrameLayout){
            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity=Gravity.CENTER;
            emptyView.setLayoutParams(layoutParams);
        }

        emptyView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                emptyOnClickListener.emptyOnClick();
            }
        });


        this.rootView.addView(emptyView);

    }

    private void errorViewConfig() {

        errorView.setTag(status_error+"");

        if(this.rootView instanceof RelativeLayout){
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            errorView.setLayoutParams(layoutParams);
        }else if(this.rootView instanceof LinearLayout){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity=Gravity.CENTER;
            errorView.setLayoutParams(layoutParams);
        }else if(this.rootView instanceof FrameLayout){
            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity=Gravity.CENTER;
            errorView.setLayoutParams(layoutParams);
        }


        errorView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                errorOnClickListener.errorOnClick();
            }
        });

        this.rootView.addView(errorView);
    }

    private void progressViewConfig() {

        progressView.setTag(status_progress+"");

        if(this.rootView instanceof RelativeLayout){
            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);
            progressView.setLayoutParams(layoutParams);
        }else if(this.rootView instanceof LinearLayout){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity=Gravity.CENTER;
            progressView.setLayoutParams(layoutParams);
        }else if(this.rootView instanceof FrameLayout){
            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity=Gravity.CENTER;
            progressView.setLayoutParams(layoutParams);
        }


        progressView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                progressOnClickListener.progressOnClick();
            }
        });

        this.rootView.addView(progressView);
    }

    private void changeStatus(int status){
        currentStatus=status;
        switch (currentStatus){
            case status_error:
                progressView.setVisibility(GONE);
                errorView.setVisibility(VISIBLE);
                emptyView.setVisibility(GONE);
                setContentViewVisibility(false);
                break;
            case status_empty:
                progressView.setVisibility(GONE);
                errorView.setVisibility(GONE);
                emptyView.setVisibility(VISIBLE);
                setContentViewVisibility(false);
                break;
            case status_content:
                progressView.setVisibility(GONE);
                errorView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                setContentViewVisibility(true);
                break;
            case status_progress:
                progressView.setVisibility(VISIBLE);
                errorView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                setContentViewVisibility(false);
                break;
        }
    }
    private void setContentViewVisibility(boolean isShow) {
        for (View view:contentView){
            //忽略指定view
            if(!ignoreViewId.contains(view.getId())){
                view.setVisibility(isShow?VISIBLE:GONE);
            }
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
    public void showContent() {
        changeStatus(status_content);
    }

    @Override
    public View getErrorView() {
        return errorView;
    }

    @Override
    public void setErrorView(View errorView) {
        if(errorView==null){
            return;
        }
        if(this.errorView!=null){
            this.rootView.removeView(this.errorView);
        }
        this.errorView = errorView;

        errorViewConfig();

        if(currentStatus==status_error){
            errorView.setVisibility(VISIBLE);
        }else{
            errorView.setVisibility(GONE);
        }
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

        if(currentStatus==status_empty){
            emptyView.setVisibility(VISIBLE);
        }else{
            emptyView.setVisibility(GONE);
        }
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

        if(currentStatus==status_progress){
            progressView.setVisibility(VISIBLE);
        }else{
            progressView.setVisibility(GONE);
        }
    }

    @Override
    public List<Integer> getIgnoreViewId() {
        return ignoreViewId;
    }

    @Override
    public void setIgnoreViewId(List<Integer> ignoreViewId) {
        this.ignoreViewId = ignoreViewId;

    }

    @Override
    public void addIgnoreViewId(List<Integer> ignoreViewId) {

        this.ignoreViewId.addAll(ignoreViewId);
    }

    @Override
    public void addIgnoreViewId(Integer ignoreViewId) {

        this.ignoreViewId.add(ignoreViewId);
    }

    @Override
    public void setErrorOnClickListener(ErrorOnClickListener errorOnClickListener) {
        this.errorOnClickListener = errorOnClickListener;
    }

    @Override
    public void setEmptyOnClickListener(EmptyOnClickListener emptyOnClickListener) {
        this.emptyOnClickListener=emptyOnClickListener;
    }

    @Override
    public void setProgressOnClickListener(ProgressOnClickListener progressOnClickListener) {
        this.progressOnClickListener=progressOnClickListener;
    }
    private Context getContext(){
        return context;
    }

    private abstract class MyOnClickListener implements View.OnClickListener{
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
