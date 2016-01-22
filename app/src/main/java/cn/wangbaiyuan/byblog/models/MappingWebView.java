package cn.wangbaiyuan.byblog.models;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Created by BrainWang on 2016/1/1.
 */
public class MappingWebView {
    private WebView view;
  public MappingWebView(WebView iview){
      view=iview;
  }
    @JavascriptInterface
    public void callJsFunc(String JSfunc){
        view.loadUrl("javascript:"+JSfunc);
    }
    @JavascriptInterface
    public void setContent(String content){
        callJsFunc("setContent('"+content+"')");
    }
}
