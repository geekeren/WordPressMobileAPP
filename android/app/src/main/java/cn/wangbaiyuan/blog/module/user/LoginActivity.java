package cn.wangbaiyuan.blog.module.user;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.wangbaiyuan.aar.commonView.activity.CommonActionBarActivity;
import cn.wangbaiyuan.aar.module.ReacNative.ReactNativeMgr;
import cn.wangbaiyuan.blog.R;
import cn.wangbaiyuan.blog.module.post.ShuoShuoActivity;

/** 登录，实现用户的登录
 * Created by BrainWang on 2017-03-06.
 */

public class LoginActivity extends CommonActionBarActivity {
    private ReactRootView mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReactInstanceManager.Builder mReactInstanceBuilder = ReactNativeMgr.getReactInstanceBuilder(getApplication());
        mReactInstanceBuilder.addPackage(new ReactPackage() {
            public LoginActivity.LoginModule loginModule;

            @Override
            public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
                List<NativeModule> modules = new ArrayList<>();
                loginModule = new LoginActivity.LoginModule(reactContext);
                modules.add(loginModule);
                return modules;
            }

            @Override
            public List<Class<? extends JavaScriptModule>> createJSModules() {
                return Collections.emptyList();
            }

            @Override
            public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
                return Collections.emptyList();
            }
        });
        ReactInstanceManager mReactInstanceManager = mReactInstanceBuilder.build();
        mContentView = new ReactRootView(getApplication().getApplicationContext());
        mContentView.startReactApplication(mReactInstanceManager, "Login", getIntent().getExtras());
        getCommonActionBar().setLeftTitle("登录");
        FrameLayout postContent = new FrameLayout(this);

        postContent.setFitsSystemWindows(true);
        FrameLayout loginForm = new FrameLayout(this);
        loginForm.addView(mContentView);
        postContent.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        loginForm.setBackgroundColor(Color.WHITE);
        postContent.addView(loginForm);
        setContentView(postContent);

    }

    public class LoginModule extends ReactContextBaseJavaModule {
        public LoginModule(ReactApplicationContext reactContext) {
            super(reactContext);
        }

        @Override
        public String getName() {
            return "Login";
        }
    }
}
