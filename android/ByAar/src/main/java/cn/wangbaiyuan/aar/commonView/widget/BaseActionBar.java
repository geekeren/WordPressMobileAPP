package cn.wangbaiyuan.aar.commonView.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by BrainWang on 2017-02-28.
 */

public abstract class BaseActionBar extends FrameLayout {
    public View getContentView() {
        return mContentView;
    }

    public View mContentView;
    public Context mContext;

    public BaseActionBar(Context context) {
        super(context);
        mContext = context;
    }

    public BaseActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void setCustomView(View view);


    public void setCustomViewById(int resId) {
        View view = LayoutInflater.from(mContext).inflate(resId, null);
        if (view != null) {
            mContentView = view;
        }
    }
}
