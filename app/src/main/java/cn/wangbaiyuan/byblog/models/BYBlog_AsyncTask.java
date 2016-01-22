package cn.wangbaiyuan.byblog.models;

import android.os.AsyncTask;

import java.io.IOException;

import cn.wangbaiyuan.byblog.network.BYBlog_HttpClient;

/**
 * Created by BrainWang on 2016/1/2.
 */
public abstract class BYBlog_AsyncTask extends AsyncTask<String,Void,String>{
    public String rawData;
    public BYBlog_AsyncTask(){

    }
    @Override
    protected void onPreExecute() {
        showProgress(true);
    }
    @Override
    protected String doInBackground(String... params) {
        String rURL=params[0];
        BYBlog_HttpClient client=new BYBlog_HttpClient(rURL);
        try {
            rawData =client.getURLResponse();
        } catch (IOException e) {
            rawData ="err:network";
        }
        return rawData;
    }
    @Override
    protected void onPostExecute(String content) {

        displayPostContent(parseData(content));
        showProgress(false);
    }
    public abstract boolean parseData(String data);
    public abstract void  showProgress(boolean show);
    public abstract void displayPostContent(boolean success);

}
