package cn.wangbaiyuan.byblog.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.wangbaiyuan.byblog.SinglePostActivity;
import cn.wangbaiyuan.byblog.SinglePostFragment;

/**
 * Created by BrainWang on 2016/1/4.
 */
public class BlogMessagePushReceiver extends PushMessageReceiver {


    @Override
    public void onBind(Context context, int i, String s, String s1, String s2, String s3) {

    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {

    }

    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), SinglePostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String postId="";
        try {
            JSONObject jso=new JSONObject(customContentString);
            postId=jso.getString("postId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtra(SinglePostFragment.ARG_ITEM_ID,postId);
       intent.putExtra(SinglePostFragment.ARG_POST_TITLE, title);
//        context.startActivity(intent);
        context.getApplicationContext().startActivity(intent);

        String notifyString = "通知点击 title=\"" + title + "\" description=\"" + description + "\" customContent=" + customContentString;
        Log.d(TAG, notifyString);
    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
