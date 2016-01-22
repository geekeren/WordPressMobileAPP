package cn.wangbaiyuan.byblog.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章目录列表界面上的每一条
 * Helper class for providing sample rawData for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PostListItemContent {

    /**
     * An array of sample (models) items.
     */
    public  final List<PostListItem> ITEMS = new ArrayList<PostListItem>();

    /**
     * A map of sample (models) items, by ID.
     */
    public  final Map<String, PostListItem> ITEM_MAP = new HashMap<String, PostListItem>();

    private  final int COUNT =10;
    public PostListItemContent(int cateid){

//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createPostListItem(i));
//        }
    }
    /**
     * 添加标题
     */
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createPostListItem(i));
//        }
//    }

    public  void addItem(PostListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public  List<PostListItem>  getItem() {
        return ITEMS;
    }
    /**
     * 在这里填充每条文章网络数据
     * @param
     * @param visitCount
     * @return
     */
    public  PostListItem createPostListItem(String id
            , String summary, String title, String categoryName, String author
            , String date, String commentcount, String visitcount) {
        return
      new PostListItem(id,  summary,  title, categoryName, author,  makeData(date),commentcount,visitcount);


    }

    private static String makeData(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date idate = null;
        try {
            idate = sdf.parse(date);
            return DateUtils.toTodayString(idate);
        } catch (ParseException e) {
           return date;
        }


    }

    private static String makeAuthors(int position) {
        return "";
    }

    private static String makeTitles(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("触类旁通学习Swift（1）语言简述").append(position);

        return builder.toString();
    }

    /**
     * A models item representing a piece of rawData.
     */
    public static class PostListItem {
        public final String id;
        public final String summary;
        public final String title;
        public final String date;
        public final String author;
        public final String commentcount;
        public final String visitcount;
        public final String categoryName;

        public PostListItem(String id, String summary, String title, String categoryName, String author, String date, String commentcount, String visitcount) {
            this.id = id;
            this.summary = summary;
            this.title = title;
            this.categoryName=categoryName;
            this.author = author;
            this.date = date;
            this.commentcount =commentcount;
            this.visitcount=visitcount;
        }

        @Override
        public String toString() {
            return summary;
        }
    }
}
