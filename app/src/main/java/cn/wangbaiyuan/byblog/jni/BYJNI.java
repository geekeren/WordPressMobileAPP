package cn.wangbaiyuan.byblog.jni;

/**
 * Created by BrainWang on 2016/1/9.
 */
public class BYJNI {
    static {
        System.loadLibrary("byblog");
    }
    public native String getHOMEURL();
    public native String getAPPLINK();
    public native String getAPIURL();
    public native String getBAIDU_PUSH_API_KEY();

}

