package cn.wangbaiyuan.aar.commonView.application;

import android.app.Application;
import android.content.Context;

//import com.squareup.leakcanary.LeakCanary;

import cn.wangbaiyuan.aar.BuildDef;
import cn.wangbaiyuan.aar.common.manager.CrashHandler;
import cn.wangbaiyuan.aar.common.utils.LocalRouteUtil;
import cn.wangbaiyuan.aar.common.utils.LogUtil;

/**
 * Created by BrainWang on 2016/10/7.
 */

public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildDef.DEBUG){
//            LeakCanary.install(this);

            LogUtil.logLifeCycle(this);//生命周期日志记录
//            CrashHandler.getReactInstanceManager().init(this);

        }

    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }
    private static LocalRouteUtil mRouteUtil = null;

    public  abstract  LocalRouteUtil getRouteUtil(Context context);
}
