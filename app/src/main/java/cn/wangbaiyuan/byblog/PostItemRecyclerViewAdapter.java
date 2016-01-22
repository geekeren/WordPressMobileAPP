package cn.wangbaiyuan.byblog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.wangbaiyuan.byblog.PostItemsFragment.OnListFragmentInteractionListener;
import cn.wangbaiyuan.byblog.models.PostListItemContent.PostListItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PostListItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your date type.
 */
public class PostItemRecyclerViewAdapter extends RecyclerView.Adapter<PostItemRecyclerViewAdapter.ViewHolder> {

    private final List<PostListItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mcontext;
    public PostItemRecyclerViewAdapter(List<PostListItem> items, OnListFragmentInteractionListener listener,Context context) {
        mcontext=context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post_item, parent, false);
        return new ViewHolder(view,mcontext);
    }

    /**
     * 填写列表数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).title);
        holder.mAuthor.setText(mValues.get(position).author);
        holder.mComment.setText(mValues.get(position).commentcount);
        holder.mVisit.setText(mValues.get(position).visitcount);
        holder.mDate.setText(mValues.get(position).date);
        holder.mSummaryView.setText(mValues.get(position).summary);


/**
 * 文章列表被点击
 */
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, SinglePostActivity.class);
                intent.putExtra(SinglePostFragment.ARG_ITEM_ID, holder.mItem.id);
                intent.putExtra(SinglePostFragment.ARG_POST_TITLE, holder.mItem.title);
                context.startActivity(intent);

                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mAuthor;
        public final TextView mComment;

        public final TextView mSummaryView;
        private final TextView mDate;
        public PostListItem mItem;
        public final TextView  mVisit;


        public ViewHolder(View view, Context mcontext) {
            super(view);
            mView = view;
            Typeface font = Typeface.createFromAsset(mcontext.getAssets(),"font/fontawesome-webfont.ttf");
            mTitle = (TextView) view.findViewById(R.id.title);
            mDate = (TextView) view.findViewById(R.id.text_listitem_post_date);
            mAuthor=(TextView) view.findViewById(R.id.text_listitem_post_author);
            mComment=(TextView) view.findViewById(R.id.text_listItem_post_comment_count);
            mVisit=(TextView) view.findViewById(R.id.text_listitem_post_visit_count);
            TextView ico_date = (TextView) view.findViewById(R.id.list_ico_date);
            TextView ico_visit = (TextView) view.findViewById(R.id.list_ico_visit);
            TextView ico_user=(TextView)view.findViewById(R.id.list_ico_user);
            TextView ico_comment=(TextView)view.findViewById(R.id.list_ico_comment);
            ico_user.setTypeface(font);
             ico_comment.setTypeface(font);
            ico_date.setTypeface(font);
            ico_visit.setTypeface(font);
            mSummaryView = (TextView) view.findViewById(R.id.summary);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSummaryView.getText() + "'";
        }
    }
}
