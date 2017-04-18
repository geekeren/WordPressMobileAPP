package cn.wangbaiyuan.blog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.wangbaiyuan.aar.common.utils.LocalRouteUtil;
import cn.wangbaiyuan.blog.module.post.SinglePostActivity;
import cn.wangbaiyuan.blog.module.user.LoginActivity;

/**
 * Created by BrainWang on 2017-03-01.
 */

public class RouteUtil extends LocalRouteUtil {


    public RouteUtil(Context context) {
        super(context);
    }

    @Override
    public  void openUriWithBundle(String uri,Bundle bundle) {
        if(uri.startsWith("http://wangbaiyuan.cn/wp-json/wp/v2/posts/")){
            Intent intent = new Intent(mContext, SinglePostActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(bundle!=null) intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }


    public static void openUriWithBundles(String uri,Bundle bundle) {
        if(uri.startsWith("http://wangbaiyuan.cn/wp-json/wp/v2/posts/")){
            Intent intent = new Intent(mContext, SinglePostActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(bundle!=null) intent.putExtras(bundle);
            mContext.startActivity(intent);
        }else if(uri.startsWith("wbyblog://wangbaiyuan.cn/login")){
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(bundle!=null) intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }
}
