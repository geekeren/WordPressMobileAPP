package cn.wangbaiyuan.aar.commonView.activity;



import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import cn.wangbaiyuan.aar.commonView.widget.BaseActionBar;
import cn.wangbaiyuan.aar.commonView.widget.CommonActionBar;
import cn.wangbaiyuan.androidlib.R;

/**
 * Created by BrainWang on 2016/11/4.
 */

public class CommonActionBarActivity extends BaseActivity {
    private boolean mbOverLay = false;
    private int mFixOrientation = 0;
    public interface FixOrientation {
        public static int NONE = 0;
        public static int LANDSCAPE = 1;
        public static int PORTRAIT = 2;
    }


    public CommonActionBar getCommonActionBar() {
        return mActionBar;
    }

    private CommonActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mbOverLay) {
            supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        }
        setOverLay(true);
        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        if (mFixOrientation == FixOrientation.NONE) {
            setFixOrientation(FixOrientation.PORTRAIT);
        }

        if (mFixOrientation == FixOrientation.LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (mFixOrientation == FixOrientation.PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mActionBar = new CommonActionBar(this);
        mActionBar.setTitle(getTitle().toString());
        setActionBar(mActionBar);

    }
    public void setActionBar(CommonActionBar actionBar) {
        mActionBar = actionBar;

        ActionBar bar = getSupportActionBar();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        bar.setCustomView(actionBar.getContentView(), params);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        bar.setDisplayShowCustomEnabled(true);
        bar.setElevation(0); //去除actionbar的阴影效果
        bar.setIcon(null);
        bar.show();
        bar.getCustomView().setPadding(0,0,0,0);

        if (mbOverLay) {

            bar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bar.setSplitBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        //如果没设置默认行为 就设置一下
        if (mActionBar instanceof CommonActionBar){
            CommonActionBar commonActionBar = ((CommonActionBar) mActionBar);
            if (commonActionBar.getLeftViewContainer().isClickable() == false) {
                commonActionBar.setLeftImageView(R.drawable.commonactionbar_back);
                commonActionBar.setLeftClickedListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }

    }


    public void setTitle(String title) {
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    /*
     * 设置actionbar是否覆盖在activity上面还是透明背景
     * 请在继承类的onCreate时，在super.onCreate前调用。
     */
    protected void setOverLay(boolean bOverlay) {
        mbOverLay = bOverlay;
    }

    protected void setFixOrientation(int fixOrientation) {
        mFixOrientation = fixOrientation;
    }

    public BaseActionBar getBaseActionBar() {
        return mActionBar;
    }

}
