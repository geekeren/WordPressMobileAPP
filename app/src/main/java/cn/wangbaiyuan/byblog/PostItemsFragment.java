package cn.wangbaiyuan.byblog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.wangbaiyuan.byblog.models.BYBlog_AsyncTask;
import cn.wangbaiyuan.byblog.models.PostListItemContent;
import cn.wangbaiyuan.byblog.models.PostListItemContent.PostListItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PostItemsFragment extends Fragment {
private static int categoryId=0;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    PostListItemContent PostList=null;
    PostItemRecyclerViewAdapter Adapter=null;
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostItemsFragment() {
    }
    @SuppressLint("ValidFragment")
    public PostItemsFragment(int icategoryId) {
        categoryId=icategoryId;
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostItemsFragment newInstance(int icategoryId) {
        PostItemsFragment fragment = new PostItemsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        categoryId=icategoryId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_item_list, container, false);
        swipeRefreshLayout =(SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
               // Toast.makeText(getContext(),"正在刷新",Toast.LENGTH_SHORT);
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                       // Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        // Set the adapter

        RecyclerView recView= (RecyclerView) view.findViewById(R.id.list);
        if (recView instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) recView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
           PostList=new PostListItemContent(categoryId);
            Adapter= new PostItemRecyclerViewAdapter(PostList.getItem(), mListener,getContext());
            recyclerView.setAdapter(Adapter);

            PostListRequstAsyncTask listRequst=new PostListRequstAsyncTask();
            listRequst.execute("?ope=getPostList&catid="+categoryId);

        }
        return view;
    }


    private class PostListRequstAsyncTask extends BYBlog_AsyncTask{

        @Override
        public boolean parseData(String data) {
            if(data.equals("err:network")){
                return false;
            }

            try {
                JSONObject jsonObject=new JSONObject(data);
                if(jsonObject.has("success")){
                    boolean code= (boolean) jsonObject.get("success");
                    if(code){
                        JSONArray list= jsonObject.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject jo = (JSONObject) list.get(i);
                            String summary= jo.getString("summary");
                            String title= jo.getString("title");
                           String categoryName= jo.getString("categoryName");
                            String author= jo.getString("author");
                            String commentCount= jo.getString("commentCount");
                            String visitCount= jo.getString("visitCount");
                            String id= jo.getString("ID");
                            String date= jo.getString("date");
                            PostList.addItem(PostList
                                    .createPostListItem(id, summary, title, categoryName,author, date, commentCount, visitCount));
                            Adapter.notifyItemInserted(i);
                        }
                        return true;
                    }

                }else{
                    return false;
                }

            } catch (JSONException e) {
                return false;

            }


            return false;
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
           // swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PostListItem item);
    }


}
