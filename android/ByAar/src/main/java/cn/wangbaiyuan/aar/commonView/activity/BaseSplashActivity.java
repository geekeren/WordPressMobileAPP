package cn.wangbaiyuan.aar.commonView.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.wangbaiyuan.androidlib.R;

public abstract class BaseSplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(BaseSplashActivity.this,getNextActivity().getClass()));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        },getDelayTime());
    }
    public abstract int getDelayTime();
    public abstract int getContentViewResId();
    public abstract Activity getNextActivity();
}
