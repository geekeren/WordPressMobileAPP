package cn.wangbaiyuan.byblog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


import java.util.List;
import java.util.Locale;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.wangbaiyuan.byblog.models.BYBlogWebViewClient;
import cn.wangbaiyuan.byblog.models.BYBlog_AsyncTask;
import cn.wangbaiyuan.byblog.models.LocalFileReader;
import cn.wangbaiyuan.byblog.models.PostListItemContent;
import cn.wangbaiyuan.byblog.models.MappingWebView;


/**
 * 每一篇文章主要内容，webview在里面
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link SinglePostActivity}
 * in two-pane mode (on tablets) or a {@link SinglePostActivity}
 * on handsets.
 */
public class SinglePostFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_POST_TITLE ="post_title";
    private static final int REQ_TTS_STATUS_CHECK = 0;

    public  MappingWebView mWebView=null;
    /**
     * The models rawData this fragment is presenting.
     */
    private PostListItemContent.PostListItem mItem;
    WebView postContentView;
    private CollapsingToolbarLayout appBarLayout;
    private String basehtml;
    private String postid;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final String contentReplace="<raw id=\"raw\"[^>]*>([\\s\\S]*)</raw>";
    private final String relevantReplace="<relevantraw id=\"relevantraw\"[^>]*>([\\s\\S]*)</relevantraw>";

    private String postcontent="";
    private String posttitle="";
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SinglePostFragment() {
    }
    @SuppressLint("JavascriptInterface")
    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Activity activity = this.getActivity();
        appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        Intent i_getvalue = activity.getIntent();
        String action = i_getvalue.getAction();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = i_getvalue.getData();
            if(uri != null){
                String title = uri.getQueryParameter("title");
                postid= uri.getQueryParameter("postid");
                appBarLayout.setTitle(title);//设置文章标题
            }
        }else if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the models rawData specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load rawData from a rawData provider.
            postid=getArguments().getString(ARG_ITEM_ID);
            String title =getArguments().getString(ARG_POST_TITLE);
                appBarLayout.setTitle(title);//设置文章标题
            SinglePostActivity.shareTitle=title;



        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == REQ_TTS_STATUS_CHECK)
//        {
//            switch (resultCode) {
//                case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
//                    //这个返回结果表明TTS Engine可以用
//                {
//                    BYBlog.speaker = new TextToSpeech(getActivity(), null);
//                }
//
//                break;
//                case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
//                    //需要的语音数据已损坏
//
//                case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
//                    //缺少需要语言的语音数据
//                case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
//                    //缺少需要语言的发音数据
//                {
//                    //这三种情况都表明数据有错,重新下载安装需要的数据
//                    Toast.makeText(getContext(),"请安装TTS文字转语音引擎！",Toast.LENGTH_SHORT).show();
//                    Intent dataIntent = new Intent();
//                    dataIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                    startActivity(dataIntent);
//                }
//                break;
//                case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
//                    //检查失败
//                default:
//
//                    break;
//            }
//        }
//        else
//        {
//            //其他Intent返回的结果
//        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_content, container, false);
       // View view = inflater.inflate(R.layout.activity_post_single, container, false);
        swipeRefreshLayout =(SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_post_container);
        swipeRefreshLayout.setAddStatesFromChildren(false);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // Toast.makeText(getApplication(), "正在刷新", Toast.LENGTH_SHORT);
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //   Toast.makeText(getApplication(), "刷新完成", Toast.LENGTH_SHORT);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

           postContentView=((WebView) rootView.findViewById(R.id.post_Content));
            WebSettings Settings= postContentView.getSettings();
            Settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            Settings.setJavaScriptEnabled(true);
            Settings.setAllowFileAccess(true);
            mWebView=new MappingWebView(postContentView);
           // postContentView.addJavascriptInterface(mWebView, "JwebView");
            postContentView.addJavascriptInterface(this, "shareButton");

             basehtml= LocalFileReader.getFromAssets("index.html", getContext());

            String iniHtml = basehtml.replaceAll(contentReplace, "<div style=\"text-align:center\">文章加载中，请稍候……</div>");

            postContentView.loadDataWithBaseURL(null, iniHtml, "text/html", "utf-8", null);
            postContentView.setWebViewClient(new BYBlogWebViewClient(getContext()));
            SinglePostRequstAsyncTask task = new SinglePostRequstAsyncTask(1586, "王柏元的博客", "王柏元", "");
            task.execute("?ope=getPostContent&postid="+postid);
        if(BYBlog.speaker==null)
        BYBlog.speaker = new TextToSpeech(getActivity(), null);

//        }

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_speakerRead){
                speak(posttitle + "，" + getActivity().getString(R.string.app_name) + "。" + postcontent);
        }
        return true;
    }

    private void speak(String text) {
        BYBlog.speaker.stop();
       BYBlog.speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }
    /**
     * Created by BrainWang on 2016/1/1.
     */
    public class SinglePostRequstAsyncTask extends BYBlog_AsyncTask {
        public int ID;
        public String Title="" ;
        public int categoryId;
        public String categoryName;
        public String author="" ;
        public int visitCount;

        public SinglePostRequstAsyncTask(int iid,String ititle,String iauthor,String date){
            super();
            Title=ititle;
            author=iauthor;
        }



        @Override
        public boolean parseData(String Data) {
            if(Data.equals("err:network")){
                return false;
            }
            return true;
        }

        @Override
        public void showProgress(final boolean show) {
            swipeRefreshLayout.post(new Runnable() {

                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(show);
                }
            });
        }

        @Override
        public void displayPostContent(boolean success) {
            if(!success){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getContext(), getString(R.string.err_check_network), Toast.LENGTH_SHORT).show();
                    }

                });
                return;
            }
            String splitStr="#{10}";
            String[] resultData=rawData.split(splitStr);
            posttitle=HtmlToText(resultData[0]);
            SinglePostActivity.shareTitle= getSummary(resultData[0]);
            appBarLayout.setTitle(getSummary(resultData[0]));
            String newHtml = basehtml.replaceAll(contentReplace, resultData[1]);
            SinglePostActivity.shareContent= getSummary(resultData[1]);
            String relevanrStr=resultData[2];

            String js="<script type=\"text/javascript\">" +
                    "    \\$(document).ready(function(){" +
                    "    \\$(\".meta\").show();" +
                    "    });" +
                    "</script>";
            String newHtml2 = newHtml.replaceAll(relevantReplace, relevanrStr+js);
            postcontent=HtmlToText(resultData[1]);
            postContentView.loadDataWithBaseURL(null, newHtml2, "text/html", "utf-8", null);
           // mWebView.setContent(rawData);

        }



    }

    public String  HtmlToText(String content){
        String replace="&lt;";
        content=content.replaceAll(replace, "<");
        replace="&gt;";
        content=content.replaceAll(replace, ">");
        replace="&ldquo;";
        content=content.replaceAll(replace, "“");
        replace="&rdquo;";
        content=content.replaceAll(replace, "”");
        replace="<[^>]*?>|\\\\s*|\\t|\\r|\\n";
        content=content.replaceAll(replace, "");

        return content;
    }

    public String getSummary(String content){
        content=HtmlToText(content);
        if(content.length()>101)
        return content.substring(0,100);
        else
            return content;
    }
    @JavascriptInterface
    public void share(String platform){
String url=BYBlog.HOMEURL+"?p="+postid;
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(SinglePostActivity.shareTitle);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(SinglePostActivity.shareContent);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
         oks.setImagePath(Environment.getExternalStorageDirectory()+"/BYBlog/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我想说两句！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        switch (platform){
            case "weibo":
            oks.setPlatform(SinaWeibo.NAME);
                break;
            case "qq":
                oks.setPlatform(QQ.NAME);
                break;
            case "weixin":
            oks.setPlatform(Wechat.NAME);

            break;
            case "pyq":
            oks.setPlatform(WechatMoments.NAME);
            break;
        }
        // 启动分享GUI
        oks.show(getActivity());
    }


}
