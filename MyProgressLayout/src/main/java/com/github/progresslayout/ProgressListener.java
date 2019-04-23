package com.github.progresslayout;

/***
 *   created by android on 2019/4/22
 */
public class ProgressListener {
    public interface ErrorOnClickListener{
        void errorOnClick();
    }
    public interface EmptyOnClickListener{
        void emptyOnClick();
    }
    public interface ProgressOnClickListener{
        void progressOnClick();
    }
    public interface NoNetworkOnClickListener{
        void noNetworkOnClick();
    }
}
