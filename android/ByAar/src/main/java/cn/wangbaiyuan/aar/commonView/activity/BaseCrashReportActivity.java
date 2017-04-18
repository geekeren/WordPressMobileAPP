package cn.wangbaiyuan.aar.commonView.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.wangbaiyuan.androidlib.R;

public class BaseCrashReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_crash_report);
    }
}
