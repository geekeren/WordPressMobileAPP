package cn.wangbaiyuan.aar.module.ReacNative;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;
import java.util.List;

import cn.wangbaiyuan.aar.BuildDef;
import cn.wangbaiyuan.aar.commonView.application.BaseApplication;

/**
 * Created by BrainWang on 2016/10/7.
 */

public abstract class RNApplication extends BaseApplication implements ReactApplication {
    private static final MyReactPackage myReactPackage=new MyReactPackage();

    public static MyReactPackage getMyReactPackage() {
        return myReactPackage;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
    }
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildDef.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    myReactPackage
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}
