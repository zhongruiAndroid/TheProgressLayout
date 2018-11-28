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
        int progressViewId=typedArray.getInt(R.styleable.ProgressRelativeLayout_progressView,-1);
        int errorViewId=typedArray.getInt(R.styleable.ProgressRelativeLayout_errorView,-1);
        int emptyViewId=typedArray.getInt(R.styleable.ProgressRelativeLayout_emptyView,-1);

        getAllView(status, progressViewId, errorViewId, emptyViewId);

        typedArray.recycle();

    }

    private void getAllView(int status, int progressViewId, int errorViewId, int emptyViewId) {
        if(progressViewId==-1){
            TextView textView = new TextView(getContext());
            textView.setText("loading");
            progressView=textView;
        }else{
            progressView= LayoutInflater.from(getContext()).inflate(progressViewId,null);
        }
        setOnclick(progressView);
        progressView.setTag(status_progress+"");


        if (errorViewId == -1) {
            TextView textView = new TextView(getContext());
            textView.setText("error");
            errorView=textView;
        }else{
            errorView=LayoutInflater.from(getContext()).inflate(errorViewId,null);
        }
        setOnclick(errorView);
        errorView.setTag(status_error+"");


        if(emptyViewId==-1){
            TextView textView = new TextView(getContext());
            textView.setText("empty");
            emptyView=textView;
        }else{
            emptyView= LayoutInflater.from(getContext()).inflate(emptyViewId,null);
        }
        setOnclick(emptyView);
        emptyView.setTag(status_empty+"");

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

        addView(errorView);
        addView(emptyView);
        addView(progressView);
    }

    private void setOnclick(View view) {
        if(progressView==view){
            if(progressView!=null&&progressOnClickListener!=null){
                progressView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        progressOnClickListener.progressOnClick();
                    }
                });
            }
        }else if(errorView==view){
            if(errorView!=null&&errorOnClickListener!=null){
                errorView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        errorOnClickListener.errorOnClick();
                    }
                });
            }
        }else if(emptyView==view){
            if(emptyView!=null&&emptyOnClickListener!=null){
                emptyView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        emptyOnClickListener.emptyOnClick();
                    }
                });
            }
        }
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
