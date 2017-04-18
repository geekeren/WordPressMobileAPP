package cn.wangbaiyuan.aar.common.utils;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by BrainWang on 2017-02-28.
 */

public abstract class LocalRouteUtil {
    public  static void init(Context context){
        mContext = context;
    }
    public Context getContext() {
        return mContext;
    }

    public static Context mContext;

    public LocalRouteUtil(Context context) {
        mContext = context;
    }
    public abstract void openUriWithBundle(String uri, Bundle bundle);

    //public void startActivtyBy();
}
