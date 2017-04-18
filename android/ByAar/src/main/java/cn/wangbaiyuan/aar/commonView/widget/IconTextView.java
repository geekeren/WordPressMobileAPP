package cn.wangbaiyuan.aar.commonView.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by BrainWang on 2016/3/27.
 */
public class IconTextView extends TextView {

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iconfont.ttf");
        setTypeface(typeface);
    }
}
