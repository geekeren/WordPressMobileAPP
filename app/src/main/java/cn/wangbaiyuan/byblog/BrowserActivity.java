package cn.wangbaiyuan.byblog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends AppCompatActivity {
    private WebView webview;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if( webview.canGoBack()){
                webview.goBack();
                return true;
            }

            else
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final SwipeRefreshLayout swipe_web_container=(SwipeRefreshLayout)findViewById(R.id.swipe_web_container);
        swipe_web_container.setAddStatesFromChildren(false);
        //设置刷新时动画的颜色，可以设置4个
        swipe_web_container.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        setSupportActionBar(toolbar);
        webview = (WebView) findViewById(R.id.BrowserActivitywebview);
        final Intent intent = getIntent();
        String Url=intent.getStringExtra("url");
        swipe_web_container.post(new Runnable() {

            @Override
            public void run() {
                swipe_web_container.setRefreshing(true);
            }
        });

        swipe_web_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webview.reload();
            }
        });
        webview.loadUrl(Url);
        WebSettings Settings= webview.getSettings();
        Settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        Settings.setJavaScriptEnabled(true);
        Settings.setAllowFileAccess(true);
        webview.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String iurl) {
                                         String url = iurl.toLowerCase();
                                         String applink = BYBlog.APPLINK.toLowerCase();
                                         String homeurl = BYBlog.HOMEURL.toLowerCase();
                                         if (url.startsWith(applink)) {
                                             String link = url.substring(url.lastIndexOf(applink) + applink.length(), url.length());
                                             if (link.startsWith("home")) {
                                                 startActivity(new Intent(BrowserActivity.this, MainActivity.class));
                                             } else if (link.startsWith("post")) {
                                                 startActivity(new Intent(BrowserActivity.this, SettingsActivity.class));
                                             }


                                         } else if (url.startsWith(homeurl)) {
                                             if (url.contains(".html")) {
                                                 int eindex = url.lastIndexOf(".html");

                                                 int sindex = url.indexOf(homeurl) + homeurl.length();
                                                 String postName = url.substring(sindex, eindex);
                                                 Intent intent = new Intent(BrowserActivity.this, SinglePostActivity.class);
                                                 intent.putExtra(SinglePostFragment.ARG_ITEM_ID, postName);
                                                 intent.putExtra(SinglePostFragment.ARG_POST_TITLE, BrowserActivity.this.getString(R.string.list_title_loading));
                                                 startActivity(intent);
                                             } else if (url.equals(homeurl)) {
                                                 startActivity(new Intent(BrowserActivity.this, MainActivity.class));
                                             } else {
                                                 view.loadUrl(iurl);
                                                 swipe_web_container.post(new Runnable() {

                                                     @Override
                                                     public void run() {
                                                         swipe_web_container.setRefreshing(true);
                                                     }
                                                 });
                                             }


                                         } else {
                                             view.loadUrl(iurl);
                                             swipe_web_container.post(new Runnable() {

                                                 @Override
                                                 public void run() {
                                                     swipe_web_container.setRefreshing(true);
                                                 }
                                             });
                                         }
                                         return true;

                                     }

                                     @Override
                                     public void onPageFinished(WebView webView, String url) {

                                         toolbar.setTitle(webView.getTitle());
                                         swipe_web_container.post(new Runnable() {

                                             @Override
                                             public void run() {
                                                 swipe_web_container.setRefreshing(false);
                                             }
                                         });

                                     }
                                 }

        );

            toolbar.setTitle(webview.getTitle());

        }

    }
