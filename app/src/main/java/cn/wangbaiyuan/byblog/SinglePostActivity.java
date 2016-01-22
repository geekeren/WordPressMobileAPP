package cn.wangbaiyuan.byblog;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * 每一篇文章显示
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item title are presented side-by-side with a list of items
 *
 */
public class SinglePostActivity extends AppCompatActivity {
    public static String shareTitle="王柏元的博客";
    public static String shareContent="博学广问,自律静思";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_single);


        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.send_comment), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(SinglePostFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(SinglePostFragment.ARG_ITEM_ID));
            arguments.putString(SinglePostFragment.ARG_POST_TITLE,
                    getIntent().getStringExtra(SinglePostFragment.ARG_POST_TITLE));

            SinglePostFragment fragment = new SinglePostFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.post_content_container, fragment)
                    .commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_settings:
                intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);
                return true;

        }

        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more title, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent i_getvalue = getIntent();
            String action = i_getvalue.getAction();

            if(Intent.ACTION_VIEW.equals(action)){
                startActivity(new Intent(this,MainActivity.class));
                finish(); return true;
            }else {try{
                navigateUpTo(new Intent(this, MainActivity.class));
                return true;
            }
                catch (NoSuchMethodError e){
                   // return super.onKeyDown(keyCode, event);
                    startActivity(new Intent(this,MainActivity.class));
                    finish(); return true;
                }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void  onDestroy(){
        if(BYBlog.speaker!=null)
        BYBlog.speaker.stop();
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, BYBlog.BAIDU_PUSH_API_KEY);
      super.onDestroy();
    }

    @Override
    public  boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i_getvalue = getIntent();
            String action = i_getvalue.getAction();

            if (Intent.ACTION_VIEW.equals(action)) {
                Intent intent=new Intent(this, MainActivity.class);
                intent.addCategory("android.intent.category.LAUNCHER");
                startActivity(intent);
                finish();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
