package cn.wangbaiyuan.aar.module.ReacNative;

import android.app.Application;

import com.facebook.react.BuildConfig;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;

import cn.wangbaiyuan.aar.BuildDef;
import cn.wangbaiyuan.aar.common.manager.BaseMgr;

/**
 * Created by BrainWang on 2016/10/8.
 */

public class ReactNativeMgr extends BaseMgr {
    private static ReactInstanceManager reacNativeMgr = null;

    public static ReactInstanceManager getReactInstanceManager(Application application) {
        if (reacNativeMgr == null) {
            reacNativeMgr = getReactInstanceBuilder(application).build();

            // 注意这里的HelloWorld必须对应“index.android.js”中的
            // “AppRegistry.registerComponent()”的第一个参数
        }
        return reacNativeMgr;
    }

    public static ReactInstanceManagerBuilder getReactInstanceBuilder(Application application) {


        ReactInstanceManagerBuilder reacNativeMgrBuilder = ReactInstanceManager.builder()
                    .setApplication(application)
                    .setBundleAssetName("index.android.js")
                    .setJSMainModuleName("index.android")
                    .addPackage(new MainReactPackage())
                    .addPackage(((RNApplication)application).getMyReactPackage())
                    .setUseDeveloperSupport(true)
                    .setInitialLifecycleState(LifecycleState.RESUMED);
                    //.build();

            // 注意这里的HelloWorld必须对应“index.android.js”中的
            // “AppRegistry.registerComponent()”的第一个参数
        return reacNativeMgrBuilder;
    }

    public static ReactRootView getReactRootViewByModuleName(String moduleName, Application application) {
        ReactInstanceManager mReactInstanceManager = getReactInstanceManager(application);
        ReactRootView mReactRootView = new ReactRootView(application.getApplicationContext());
        mReactRootView.startReactApplication(mReactInstanceManager, moduleName, null);
        return mReactRootView;
    }


}
