package cn.wangbaiyuan.blog.module.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.chinaztt.entity.ItemBean;
import com.chinaztt.utils.ItemDataUtils;
import com.chinaztt.widget.DragLayout;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nineoldandroids.view.ViewHelper;

import cn.wangbaiyuan.aar.common.utils.LocalRouteUtil;
import cn.wangbaiyuan.aar.commonView.activity.BaseReactActivity;
import cn.wangbaiyuan.blog.R;
import cn.wangbaiyuan.blog.RouteUtil;
import cn.wangbaiyuan.blog.module.post.ShuoShuoActivity;
import cn.wangbaiyuan.blog.module.post.SinglePostActivity;

public class MainActivity extends BaseReactActivity
        implements MainContentFragment.OnFragmentInteractionListener,
        RNFragment.OnFragmentInteractionListener,CategoryFragment.OnFragmentInteractionListener {

    // Used to load the 'native-lib' library on application startup.


    private DragLayout dl;
    private ListView lv;
    private ImageView ivIcon, ivBottom;
    private QuickAdapter<ItemBean> quickAdapter;
    private FrameLayout main_content;
    private LinearLayout user_info_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
        initDragLayout();
        initView();

    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            //界面打开的时候
            @Override
            public void onOpen() {
            }

            //界面关闭的时候
            @Override
            public void onClose() {
            }

            //界面滑动的时候
            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(ivIcon, 1 - percent);
            }
        });
        //dl.setEnabled(false);
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ViewGroup linear_bar = (ViewGroup) findViewById(R.id.rl_title);
            final int statusHeight = getStatusBarHeight();
            linear_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = linear_bar.getHeight();
                    android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) linear_bar.getLayoutParams();
                    params.height = statusHeight + titleHeight;
                    linear_bar.setLayoutParams(params);
                }
            });

        }
    }

    private void initView() {
        user_info_container = (LinearLayout)findViewById(R.id.user_info_container);
        user_info_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouteUtil.openUriWithBundles("wbyblog://wangbaiyuan.cn/login",null);
            }
        });

        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        ivBottom = (ImageView) findViewById(R.id.iv_bottom);

        lv = (ListView) findViewById(R.id.lv);
        main_content = (FrameLayout) findViewById(R.id.main_content);
        lv.setAdapter(quickAdapter = new QuickAdapter<ItemBean>(this, R.layout.item_left_layout, ItemDataUtils.getItemBeans()) {
            @Override
            protected void convert(BaseAdapterHelper helper, ItemBean item) {
                helper.setImageResource(R.id.item_img, item.getImg())
                        .setText(R.id.item_tv, item.getTitle());
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if(position==3){
                    Intent intent = new Intent(MainActivity.this, ShuoShuoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });
        MainContentFragment main = MainContentFragment.newInstance("", "");
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, main).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if(dl.isOpen()){
            dl.close();
            return;
        }
        moveTaskToBack(false);
        super.onBackPressed();
    }
}
