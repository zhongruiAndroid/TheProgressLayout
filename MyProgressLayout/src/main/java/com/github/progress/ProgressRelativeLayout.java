package com.github.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/***
 *   created by zhongrui on 2018/9/21
 */
public class ProgressRelativeLayout extends RelativeLayout {
    private View errorView;
    private View emptyView;
    private View progressView;

    private List<View> contentView = new ArrayList<View>();
    private List<Integer> ignoreViewId =new ArrayList<>();

    private final int status_error=-1;
    private final int status_empty=0;
    private final int status_content=1;
    private final int status_progress=2;

    private int currentStatus=status_content;


    public interface ErrorOnClickListener{
        void errorOnClick();
    }
    public interface EmptyOnClickListener{
        void emptyOnClick();
    }
    public interface ProgressOnClickListener{
        void progressOnClick();
    }

    public ErrorOnClickListener errorOnClickListener ;
    public EmptyOnClickListener emptyOnClickListener ;
    public ProgressOnClickListener progressOnClickListener ;

    public final int defAttr=R.attr.ProgressLayoutDefStyle;
    public ProgressRelativeLayout(Context context) {
        super(context);
        initAttr(null,defAttr);
    }

    public ProgressRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs,defAttr);
    }

    public ProgressRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs,defStyleAttr);
    }

    private void initAttr(AttributeSet attrs,int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressRelativeLayout,defStyleAttr,R.style.DefaultProgressStyle);
        int status = typedArray.getInt(R.styleable.ProgressRelativeLayout_status, status_content);
        currentStatus=status;
        int progressViewId=typedArray.getResourceId(R.styleable.ProgressRelativeLayout_progressView,-1);
        int errorViewId=typedArray.getResourceId(R.styleable.ProgressRelativeLayout_errorView,-1);
        int emptyViewId=typedArray.getResourceId(R.styleable.ProgressRelativeLayout_emptyView,-1);

        getAllView(status, progressViewId, errorViewId, emptyViewId);

        typedArray.recycle();

    }

    private void getAllView(int status, final int progressViewId, int errorViewId, int emptyViewId) {

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

        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        emptyView.setLayoutParams(layoutParams);
        emptyView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                emptyOnClickListener.emptyOnClick();
            }
        });


        addView(emptyView);

    }

    private void errorViewConfig() {

        errorView.setTag(status_error+"");

        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        errorView.setLayoutParams(layoutParams);
        errorView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                errorOnClickListener.errorOnClick();
            }
        });

        addView(errorView);
    }

    private void progressViewConfig() {

        progressView.setTag(status_progress+"");

        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        progressView.setLayoutParams(layoutParams);
        progressView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                progressOnClickListener.progressOnClick();
            }
        });

        addView(progressView);
    }


    public void showError(){
        showError(null);
    }
    public void showError(final ErrorOnClickListener errorOnClickListener){
        if(errorOnClickListener!=null){
            if (errorView != null) {
                errorView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        errorOnClickListener.errorOnClick();
                    }
                });
            }else{
                throw new IllegalStateException("errorView is null");
            }
        }

        changeStatus(status_error);
    }
    public void showEmpty() {
        showEmpty(null);
    }
    public void showEmpty(final EmptyOnClickListener emptyOnClickListener) {
        if(emptyOnClickListener!=null){
            if(emptyView!=null){
                emptyView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        emptyOnClickListener.emptyOnClick();
                    }
                });
            }else{
                throw new IllegalStateException("emptyView is null");
            }
        }
        changeStatus(status_empty);
    }
    public void showProgress(){
        showProgress(null);
    }
    public void showProgress(final ProgressOnClickListener progressOnClickListener){
        if(progressOnClickListener!=null){
            if(progressView!=null){
                progressView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        progressOnClickListener.progressOnClick();
                    }
                });
            }else{
                throw new IllegalStateException("progressView is null");
            }
        }
        changeStatus(status_progress);
    }

    public void showContent(){
        changeStatus(status_content);
    }


    public View getErrorView() {
        return errorView;
    }

    public void setErrorView(View errorView) {
        if(this.errorView!=null){
            removeView(this.errorView);
        }
        this.errorView = errorView;

        errorViewConfig();

        if(currentStatus==status_error){
            errorView.setVisibility(VISIBLE);
        }else{
            errorView.setVisibility(GONE);
        }

    }

    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(View emptyView) {
        if (this.emptyView == null) {
            removeView(this.emptyView);
        }
        this.emptyView = emptyView;
        emptyViewConfig();

        if(currentStatus==status_empty){
            emptyView.setVisibility(VISIBLE);
        }else{
            emptyView.setVisibility(GONE);
        }
    }

    public View getProgressView() {
        return progressView;
    }

    public void setProgressView(View progressView) {
        if (this.progressView == null) {
            removeView(this.progressView);
        }
        this.progressView = progressView;
        progressViewConfig();

        if(currentStatus==status_progress){
            progressView.setVisibility(VISIBLE);
        }else{
            progressView.setVisibility(GONE);
        }
    }

    public List<Integer> getIgnoreViewId() {
        return ignoreViewId;
    }

    public void setIgnoreViewId(List<Integer> ignoreViewId) {
        this.ignoreViewId = ignoreViewId;
    }
    public void addIgnoreViewId(List<Integer> ignoreViewId) {
        this.ignoreViewId.addAll(ignoreViewId);
    }
    public void addIgnoreViewId(Integer ignoreViewId) {
        this.ignoreViewId.add(ignoreViewId);
    }

    public void setErrorOnClickListener(ErrorOnClickListener errorOnClickListener) {
        this.errorOnClickListener = errorOnClickListener;
    }

    public void setEmptyOnClickListener(EmptyOnClickListener emptyOnClickListener) {
        this.emptyOnClickListener = emptyOnClickListener;
    }

    public void setProgressOnClickListener(ProgressOnClickListener progressOnClickListener) {
        this.progressOnClickListener = progressOnClickListener;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if(child.getTag()==null||(!child.getTag().equals(status_empty+"")&&!child.getTag().equals(status_error+"")&&!child.getTag().equals(status_progress+""))){
            contentView.add(child);
        }
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
