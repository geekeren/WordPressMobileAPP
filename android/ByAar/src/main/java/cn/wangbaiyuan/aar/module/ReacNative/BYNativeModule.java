package cn.wangbaiyuan.aar.module.ReacNative;


import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;

import cn.wangbaiyuan.aar.common.utils.FileUtil;
import cn.wangbaiyuan.aar.common.utils.LocalRouteUtil;
import cn.wangbaiyuan.aar.common.utils.TimeUtil;
import cn.wangbaiyuan.aar.commonView.application.BaseApplication;

import static com.facebook.react.bridge.ReadableType.Map;

/**
 * Created by BrainWang on 2017-02-28.
 */

public class BYNativeModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;

    public BYNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "NativeModule";
    }

    @ReactMethod
    public void startActivityByClassName(String name) {

        try {
            Class cls = Class.forName(name);
            Intent intent = new Intent(mContext, cls);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @ReactMethod
    public void startActivityByClassNameWithExtras(String name, ReadableMap map) {

        try {
            Class cls = Class.forName(name);
            Intent intent = new Intent(mContext, cls);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



    @ReactMethod
    public void openUri(String Uri) {
        openUriWithExtras(Uri,null);
    }
    @ReactMethod
    public void openUriWithExtras(String Uri, ReadableMap map) {
        Bundle bundle =new Bundle();
        if(map!=null){
            ReadableMapKeySetIterator iter = map.keySetIterator();
            while (iter.hasNextKey()) {
                String key = iter.nextKey();
                ReadableType val = map.getType(key);
                if(val.equals(ReadableType.String))
                    bundle.putString(key, map.getString(key));

            }
        }

        bundle.putString("restLink",Uri);
        LocalRouteUtil routeUtil = ((BaseApplication) (getCurrentActivity().getApplication())).getRouteUtil(mContext);
        routeUtil.openUriWithBundle(Uri,bundle);

    }

        @ReactMethod
        public void timeToString (String time, String format, Callback callback){
            callback.invoke(TimeUtil.TimeToString(time, format));
        }

        @ReactMethod
        public String readStringFromAssert (String fileName){
            return FileUtil.readFromAssert(getReactApplicationContext(), fileName);
        }

    }
