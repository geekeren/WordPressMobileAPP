package cn.wangbaiyuan.byblog;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Show the Up button in the action bar.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
//判断是否是搜索请求
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//获取搜索的查询内容（关键字）
            String query = intent.getStringExtra(SearchManager.QUERY);
//执行相应的查询动作
           // doMySearch(query);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        // 通过SearchManager获取搜索配置信息，包括：接收intent的activity，searchabel.xml中的信息。
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        // me.li2.android.photogallery/me.li2.android.photogallery.PhotoGalleryActivity
        ComponentName name = getComponentName();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(name);

        // 然后将相关信息通知给SearchView。
        searchView.setSearchableInfo(searchableInfo);

        // Expand and give focus to SearchView automatically
        // http://stackoverflow.com/a/11710098/2722270
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
//
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(searchView, InputMethodManager.SHOW_FORCED);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more title, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSearchRequested() {
//这个方法中干你想干的事，比如做一些被始化工作
        return super.onSearchRequested();
    }
}
