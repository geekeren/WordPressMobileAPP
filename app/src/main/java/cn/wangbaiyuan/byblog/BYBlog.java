package cn.wangbaiyuan.byblog;

import android.app.Application;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;

import cn.wangbaiyuan.byblog.jni.BYJNI;

/**
 * Created by BrainWang on 2016/1/1.
 */
public class BYBlog extends Application {
    public  static   String HOMEURL=new BYJNI().getHOMEURL();
    public  static   String APIURL =new BYJNI().getAPIURL();
    public  static   String APPLINK=new BYJNI().getAPPLINK();
    public  static   String BAIDU_PUSH_API_KEY=new BYJNI().getBAIDU_PUSH_API_KEY();
    public static TextToSpeech speaker;
    public  static boolean isLogin=false;

    static {
        System.loadLibrary("byblog");
    }

    public  HashMap<Integer,Integer> nav_category=new HashMap<Integer,Integer>();
    public  HashMap<Integer,Integer> getNavToCategory(){
        return nav_category;
    }


    public BYBlog(){
            nav_category.put(R.id.nav_home,0);
            nav_category.put(R.id.nav_itindustry,2);
            nav_category.put(R.id.nav_technology,117);
            nav_category.put(R.id.nav_geek_views,12);
     }
    public Integer getCategoryIdByNav(Integer nav){

        return nav_category.get(nav);
    }



}
