package cn.wangbaiyuan.aar.common.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import cn.wangbaiyuan.aar.BuildDef;
import cn.wangbaiyuan.aar.commonView.application.BaseApplication;

/**
 * Created by BrainWang on 2016/10/7.
 */

public class LogUtil {
    public static void i(String tag,String msg){
        if(BuildDef.DEBUG)
            Log.i(tag,msg);
    }
    public static void d(String tag,String msg){
        if(BuildDef.DEBUG)
            Log.d(tag,msg);
    }
    public static void v(String tag,String msg){
        if(BuildDef.DEBUG)
            Log.v(tag,msg);
    }
    public static void e(String tag,String msg){
        if(BuildDef.DEBUG)
        Log.e(tag,msg);
    }


    public static String getCurrentMethod(){
        StackTraceElement stack= new Exception().getStackTrace()[2];
        return stack.getMethodName();
    }
    public static void logLifeCycle(String msg){
       Log.i("LifeCycle",msg);
    };

    public static void logLifeCycle(BaseApplication baseApplication) {
        LogUtil.logLifeCycle(baseApplication.getClass().getName()+" @onApplicationCreated");
        baseApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtil.logLifeCycle(activity.getClass().getName()+" @onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogUtil.logLifeCycle(activity.getClass().getName()+" @onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                LogUtil.logLifeCycle(activity.getClass().getName()+" @onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                LogUtil.logLifeCycle(activity.getClass().getName()+" @onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                LogUtil.logLifeCycle(activity.getClass().getName()+" @onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                LogUtil.logLifeCycle(activity.getClass().getName()+" @onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtil.logLifeCycle(activity.getClass().getName()+" @onActivityDestroyed");
                System.gc();
            }
        });
    }

}
