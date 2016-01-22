package cn.wangbaiyuan.byblog.models;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.wangbaiyuan.byblog.BYBlog;
import cn.wangbaiyuan.byblog.BrowserActivity;
import cn.wangbaiyuan.byblog.MainActivity;
import cn.wangbaiyuan.byblog.R;
import cn.wangbaiyuan.byblog.SettingsActivity;
import cn.wangbaiyuan.byblog.SinglePostActivity;
import cn.wangbaiyuan.byblog.SinglePostFragment;

/**
 * Created by BrainWang on 2016/1/2.
 */
public class BYBlogWebViewClient extends WebViewClient {
    private Context context;
    public BYBlogWebViewClient(Context icontext){
        context=icontext;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO Auto-generated method stub
        url=url.toLowerCase();
        String applink=BYBlog.APPLINK.toLowerCase();
        String homeurl=BYBlog.HOMEURL.toLowerCase();
        if (url.startsWith(applink)) {
            String link=url.substring(url.lastIndexOf(applink)+applink.length(),url.length());
            if(link.startsWith("home")){
                context.startActivity(new Intent(context, MainActivity.class));
            }else if(link.startsWith("post")){
                context.startActivity(new Intent(context, SettingsActivity.class));
            }


        } else if(url.startsWith(homeurl)) {
            if(url.contains(".html")){
                int eindex=url.lastIndexOf(".html");

                int sindex=url.indexOf(homeurl)+homeurl.length();
                String postName=url.substring(sindex,eindex);
                Intent intent = new Intent(context, SinglePostActivity.class);
                intent.putExtra(SinglePostFragment.ARG_ITEM_ID, postName);
                intent.putExtra(SinglePostFragment.ARG_POST_TITLE, context.getString(R.string.list_title_loading));
                context.startActivity(intent);
            }else{
                Intent intent =new Intent(context,BrowserActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }


        } else{
            Intent intent =new Intent(context,BrowserActivity.class);
            intent.putExtra("url", url);
            context.startActivity(intent);
        }


        return true;
    }

    @Override
    public void onPageFinished(WebView webView, String url) {


    }
}
