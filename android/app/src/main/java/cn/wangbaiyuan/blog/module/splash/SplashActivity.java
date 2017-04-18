package cn.wangbaiyuan.blog.module.splash;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;


import cn.wangbaiyuan.aar.module.ReacNative.ReactNativeMgr;
import cn.wangbaiyuan.aar.commonView.activity.BaseSplashActivity;
import cn.wangbaiyuan.blog.R;
import cn.wangbaiyuan.blog.module.main.MainActivity;

public class SplashActivity extends BaseSplashActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/artfont.ttf");
        welcomeTextView.setTypeface(typeface);
        Animation animation = new AlphaAnimation(0.1f, 1);
        animation.setDuration(2000);
        welcomeTextView.setAnimation(animation);
        ReactNativeMgr.getReactInstanceManager(getApplication());
    }

    @Override
    public int getDelayTime() {
        return 3000;
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    public Activity getNextActivity() {
        return new MainActivity();
    }

}
