package cn.wangbaiyuan.blog;

import android.app.Application;
import android.content.Context;

import com.facebook.react.ReactInstanceManager;

import cn.sharesdk.framework.ShareSDK;
import cn.wangbaiyuan.aar.common.utils.LocalRouteUtil;
import cn.wangbaiyuan.aar.commonView.application.BaseApplication;
import cn.wangbaiyuan.aar.module.ReacNative.RNApplication;
import cn.wangbaiyuan.aar.module.ReacNative.ReactNativeMgr;

/**
 * Created by BrainWang on 2016/10/7.
 */

public class BlogApplication extends RNApplication {
    static {
        System.loadLibrary("config");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ReactNativeMgr.getReactInstanceManager(this);
        ShareSDK.initSDK(this);
        RouteUtil.init(this);
    }

    @Override
    public LocalRouteUtil getRouteUtil(Context context) {
        return new RouteUtil(context);

    }

    public ReactInstanceManager getReactInstanceManager() {
            return ReactNativeMgr.getReactInstanceManager(this);
    }

}
