package cn.wangbaiyuan.byblog;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import java.util.ArrayList;

import cn.wangbaiyuan.byblog.models.PostListItemContent;
import cn.wangbaiyuan.byblog.view.CircleImageView;

/**
 * 主界面activity
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,PostItemsFragment.OnListFragmentInteractionListener,CategoryPaperFragment.OnFragmentInteractionListener
{
    BYBlog byblog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, getString(R.string.leave_Message), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main_content, new PostItemsFragment(0)).commit();
        View headerView = navigationView.getHeaderView(0);
        CircleImageView nav_head_image=(CircleImageView)headerView.findViewById(R.id.nav_head_image);
        nav_head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BYBlog.isLogin) {
                    startActivity(new Intent(getBaseContext(), UserCenterActivity.class));
                } else {

                    startActivity(new Intent(getBaseContext(), LoginActivity.class));

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        byblog=new BYBlog();
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,BYBlog.BAIDU_PUSH_API_KEY);

    }
    @Override
    protected void onDestroy ()  {
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,BYBlog.BAIDU_PUSH_API_KEY);
        super.onDestroy ();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Toast.makeText(this,getIntent().toString(),Toast.LENGTH_SHORT).show();
        if(getIntent().getBooleanExtra("external",false))
            super.onBackPressed();
          else  moveTaskToBack(true);

           //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_setting){
            startActivity(new Intent(getBaseContext(), SettingsActivity.class));
            //return true;
        }
        else {
            if (id == R.id.nav_shuoshuo || id == R.id.nav_collect || id == R.id.nav_history) {

                Toast.makeText(getBaseContext(), getString(R.string.not_Implement_func), Toast.LENGTH_SHORT).show();

            } else {

                int categoryId = byblog.getCategoryIdByNav(id);
                PostItemsFragment ft = new PostItemsFragment(categoryId);
                ArrayList<Integer> categorys=new ArrayList<Integer>();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main_content, ft).commit();

            }
            getSupportActionBar().setTitle(item.getTitle());
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//
//    }

    @Override
    public void onListFragmentInteraction(PostListItemContent.PostListItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
