package cn.wangbaiyuan.blog.module.post;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.wangbaiyuan.aar.commonView.activity.CommonActionBarActivity;
import cn.wangbaiyuan.aar.commonView.activity.SwipeBackActivity;
import cn.wangbaiyuan.aar.module.ReacNative.BYNativeModule;
import cn.wangbaiyuan.aar.module.ReacNative.ReactNativeMgr;
import cn.wangbaiyuan.blog.R;

/**
 * 说说列表Activity
 */
public class ShuoShuoActivity extends CommonActionBarActivity {

    public Bundle bundle = new Bundle();
    private ReactRootView mReactRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuoshuo);
        //setSwipeBackEnable(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        ReactInstanceManagerBuilder mReactInstanceBuilder = ReactNativeMgr.getReactInstanceBuilder(getApplication());
        mReactInstanceBuilder.addPackage(new ReactPackage() {
            public ShuoShuoModule shuoShuoModule;

            @Override
            public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
                List<NativeModule> modules = new ArrayList<>();
                shuoShuoModule = new ShuoShuoModule(reactContext);
                modules.add(shuoShuoModule);
                return modules;
            }

            @Override
            public List<Class<? extends JavaScriptModule>> createJSModules() {
                return Collections.emptyList();
            }

            @Override
            public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
                return Collections.emptyList();
            }
        });
        ReactInstanceManager mReactInstanceManager = mReactInstanceBuilder.build();
        mReactRootView = new ReactRootView(getApplication().getApplicationContext());
        mReactRootView.startReactApplication(mReactInstanceManager, "ShuoShuo", getIntent().getExtras());
        getCommonActionBar().setLeftTitle("说说");
        FrameLayout postContent = (FrameLayout) findViewById(R.id.activity_single_post);
        postContent.addView(mReactRootView);
        getCommonActionBar().setRightImageView(R.mipmap.shuoshuo_add);
        registerForContextMenu(mReactRootView);
        //mReactRootView.showContextMenu();
        //mReactInstanceManager.addReactInstanceEventListener();
        getCommonActionBar().setRightClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mReactRootView.showContextMenu();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // menu.setHeaderTitle("复制与分享");
        menu.add(0, 0, Menu.NONE, "复制到剪贴板");
        menu.add(0, 1, Menu.NONE, "分享到朋友圈");
        menu.add(0, 2, Menu.NONE, "更多分享");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("说说", bundle.getString("content"));
                cm.setPrimaryClip(mClipData);
                final Snackbar snackbar =  Snackbar.make(ShuoShuoActivity.this.getWindow().getDecorView(), getString(R.string.copy_successfully), Snackbar.LENGTH_SHORT);
                snackbar.show();

                break;
            case 1:
                showShare(WechatMoments.NAME);
                break;
            case 2:
                showShare(null);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showShare(String platform) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // text是分享文本，所有平台都需要这个字段
        oks.setText(bundle.getString("content"));
        oks.setComment("文章太精彩了！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
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
        if (platform != null) {
            oks.setPlatform(platform);
        }
        oks.show(this);
    }

    private void showContextMenu() {
        mReactRootView.showContextMenu();
    }

    class ShuoShuoModule extends ReactContextBaseJavaModule {

        public ShuoShuoModule(ReactApplicationContext reactContext) {
            super(reactContext);
        }

        @Override
        public boolean canOverrideExistingModule() {
            return true;
        }

        @Override
        public String getName() {
            return "ShuoShuoModule";
        }

        @ReactMethod
        public void share(ReadableMap map) {
            // Bundle bundle =new Bundle();
            if (map != null) {
                ReadableMapKeySetIterator iter = map.keySetIterator();
                while (iter.hasNextKey()) {
                    String key = iter.nextKey();
                    ReadableType val = map.getType(key);
                    if (val.equals(ReadableType.String))
                        bundle.putString(key, map.getString(key));

                }

                ShuoShuoActivity.this.showContextMenu();
            }

        }


    }


}
