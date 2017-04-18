package cn.wangbaiyuan.blog.module.post;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactMarker;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.wangbaiyuan.aar.commonView.activity.SwipeBackActivity;
import cn.wangbaiyuan.aar.commonView.widget.CommonActionBar;
import cn.wangbaiyuan.aar.module.ReacNative.ReactNativeMgr;
import cn.wangbaiyuan.blog.R;

public class SinglePostActivity extends SwipeBackActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // this.context
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        setSwipeBackEnable(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        ReactInstanceManager mReactInstanceManager = ReactNativeMgr.getReactInstanceManager(getApplication());
        ReactRootView mReactRootView = new ReactRootView(getApplication().getApplicationContext());
        bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        CommonActionBar actionBar =getCommonActionBar();
        //setTitle(title);
        //getCommonActionBar().getRightImageView().setVisibility(View.VISIBLE);
        actionBar.setLeftTitle("返回");
        actionBar.getRightImageView().setVisibility(View.VISIBLE);
        actionBar.setRightClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
        mReactRootView.startReactApplication(mReactInstanceManager, "SinglePost",bundle);

        FrameLayout postContent = (FrameLayout)findViewById(R.id.activity_single_post);
        postContent.addView(mReactRootView);

    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(bundle.getString("title"));
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(bundle.getString("link"));
        // text是分享文本，所有平台都需要这个字段
        oks.setText(bundle.getString("excerpt"));
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://wangbaiyuan.cn/favicon.ico");
        String imgUrl = bundle.getString("thumb_url");
        if(imgUrl.startsWith("http://")||imgUrl.startsWith("https://"))
            oks.setImageUrl(imgUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(bundle.getString("link"));
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("文章太精彩了！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(bundle.getString("link"));


        //添加自定义分享
        Bitmap enableLogo = BitmapFactory.decodeResource(getResources(), R.drawable.share_more);
        String label = "更多分享";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, bundle.getString("excerpt"));
                intent.setType("text/html");
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(intent, "分享到"));
            }
        };
        oks.setCustomerLogo(enableLogo, label, listener);
        // 启动分享GUI
        oks.show(this);
    }


}
