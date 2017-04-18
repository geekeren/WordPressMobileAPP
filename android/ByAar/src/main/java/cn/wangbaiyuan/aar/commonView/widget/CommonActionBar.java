package cn.wangbaiyuan.aar.commonView.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wangbaiyuan.androidlib.R;

/**
 * Created by BrainWang on 2017-02-28.
 */

public class CommonActionBar extends BaseActionBar {

    private LinearLayout mLeftViewContainer;
    private ImageView mBackBtn;
    private TextView mLeftTitle;
    private LinearLayout mCenterViewContainer;
    private ImageView mCenterLogo;
    private TextView mCenterTitle;
    private LinearLayout mRightViewContainer;
    private TextView mRightTitle;
    private ImageView mRightImageView;

    private float mScrollHeight;
    private String mRightImageUrl;
    private String mRightDefaultImageUrl;
    public CommonActionBar(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (mContext == null) {
            return;
        }
        if (mContentView == null) {
            //默认布局是有三部分组成
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.view_customactionbar, null);
        }
        mLeftViewContainer = (LinearLayout) mContentView.findViewById(R.id.actionbar_left_container);
        mCenterViewContainer = (LinearLayout) mContentView.findViewById(R.id.actionbar_center_container);
        mRightViewContainer = (LinearLayout) mContentView.findViewById(R.id.actionbar_right_container);
        mBackBtn = (ImageView) mContentView.findViewById(R.id.action_bar_back);
        mLeftTitle = (TextView)mContentView.findViewById(R.id.action_left_title);
        mCenterLogo = (ImageView) mContentView.findViewById(R.id.action_bar_logo);
        mCenterTitle = (TextView)mContentView.findViewById(R.id.action_bar_title);
        mRightTitle = (TextView)mContentView.findViewById(R.id.action_right_title);
        mRightImageView = (ImageView)mContentView.findViewById(R.id.action_right_imageView);

    }

    public CommonActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCustomView(View view) {
        if (view == null) {
            return;
        }
        mContentView = view;
    }
    /**
     * 返回右边布局viewGroup
     * @return
     */
    public LinearLayout getRightViewContainer() {
        return mRightViewContainer;
    }

    /**
     * 返回左边布局viewGroup
     * @return
     */
    public LinearLayout getLeftViewContainer() {
        return mLeftViewContainer;
    }

    /**
     * 返回中间布局viewGroup
     * @return
     */
    public LinearLayout getCenterViewContainer(){
        return mCenterViewContainer;
    }

    /**
     * 返回左边imageView
     * @return
     */
    public ImageView getLeftImageView(){
        return mBackBtn;
    }

    /**
     * 设置左边imageView
     * @return
     */
    public void setLeftImageView(int resId) {
        mBackBtn.setImageResource(resId);
        mBackBtn.setVisibility(VISIBLE);
    }

    /**
     * 返回左边titleView
     * @return
     */
    public TextView getLeftTitleView() {
        return mLeftTitle;
    }

    /**
     * 设置左边titleV
     * @return
     */
    public void setLeftTitle(String title){
        mLeftTitle.setText(title);
        mLeftTitle.setVisibility(VISIBLE);
        mCenterTitle.setVisibility(GONE);
    }

    /**
     * 设置左边title 颜色
     *
     * @return
     */
    public void setLeftTitleColor(int color) {
        mLeftTitle.setTextColor(color);
    }

    /**
     * 设置右边title 颜色
     *
     * @return
     */
    public void setRightTitleColor(int color) {
        mRightTitle.setTextColor(color);
    }

    /**
     * 返回中间titleView
     * @return
     */
    public TextView getCenterTitleView(){
        return mCenterTitle;
    }

    /**
     * 返回中间Logo
     * @return
     */
    public ImageView getCenterLogo(){
        return mCenterLogo;
    }

    /**
     * 设置中间Logo
     *
     * @return
     */
    public void setCenterLogo(int resId) {
        mCenterLogo.setImageResource(resId);
        mCenterLogo.setVisibility(VISIBLE);
    }


    /**
     * 设置中间titleV
     * @return
     */

    public void setTitle(String title){
        mCenterTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mCenterTitle.setSingleLine(true);
        mCenterTitle.setSelected(true);
        mCenterTitle.setFocusable(true);
        mCenterTitle.setFocusableInTouchMode(true);
        mCenterTitle.setText(title);
        mCenterTitle.setVisibility(VISIBLE);
    }
    /**
     * 返回右边titleView
     * @return
     */
    public TextView getRightTitleView(){
        return mRightTitle;
    }

    /**
     * 返回右边ImageView
     * @return
     */
    public ImageView getRightImageView(){
        return  mRightImageView;
    }

    /**
     * 设置右边ImageView
     * @return
     */
    public void setRightImageView(int resId) {
        mRightImageView.setImageResource(resId);
        mRightImageView.setVisibility(VISIBLE);
    }

    /**
     * 设置右边title
     * @return
     */
    public void setRightTitle(String title) {
        mRightTitle.setText(title);
        mRightTitle.setVisibility(VISIBLE);
    }

    /**
     * 设置左边点击事件处理
     */
    public void setLeftClickedListener(OnClickListener l){
        if (mLeftViewContainer != null){
            mLeftViewContainer.setOnClickListener(l);
        }
    }
    /**
     * 设置右边点击事件处理
     */
    public void setRightClickedListener(OnClickListener r){
        if (mRightViewContainer != null){
            mRightViewContainer.setOnClickListener(r);
        }
    }

    /**
     * 返回actionbar滚动高度
     */
    public float getScrollHeight(){
        return mScrollHeight;
    }

    /**
     * 设置actionbar滚动高度
     */
    public void setScrollHeight(float height) {
        this.mScrollHeight = height;
    }

    /**
     * 返回右边图片的链接
     */
    public String getRightDefaultImageUrl(){
        return mRightDefaultImageUrl;
    }

    /**
     * 设置右边图片的链接
     */
    public void setRightDefaultImageUrl(String imageUrl) {
        this.mRightDefaultImageUrl = imageUrl;
    }

    /**
     * 返回右边图片的链接
     */
    public String getRightImageUrl(){
        return mRightImageUrl;
    }

    /**
     * 设置右边图片的链接
     */
    public void setRightImageUrl(String imageUrl) {
        this.mRightImageUrl = imageUrl;
    }

}
