package cn.wangbaiyuan.byblog.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.wangbaiyuan.byblog.R;

/**
 * Created by BrainWang on 2016/1/1.
 */
    public class CircleImageView extends ImageView {

        Path path;
        public PaintFlagsDrawFilter mPaintFlagsDrawFilter;// 毛边过滤
        Paint paint;
    private Activity context;

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            // TODO Auto-generated constructor stub
            init();
        }

        public CircleImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
            init();
        }

        public CircleImageView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            init();
        }
        public void init(){
            mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0,
                    Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setColor(getResources().getColor(R.color.colorPrimary));

        }

        @Override
        protected void onDraw(Canvas cns) {
            // TODO Auto-generated method stub
            float h = getMeasuredHeight();
            float w = getMeasuredWidth();
            if (path == null) {
                path = new Path();
                path.addCircle(
                        w/2.0f
                        , h/2.0f
                        , (float) Math.min(w/2.0f, (h / 2.0))
                        , Path.Direction.CCW);
                path.close();
            }
            cns.drawCircle(w/2.0f, h/2.0f,  Math.min(w/2.0f, h / 2.0f) , paint);
            int saveCount = cns.getSaveCount();
            cns.save();

            try{
                cns.clipPath(path, Region.Op.REPLACE);
            }catch (UnsupportedOperationException e){
                e.printStackTrace();
            }

            //cns.setDrawFilter(mPaintFlagsDrawFilter);
            cns.drawColor(getResources().getColor(R.color.colorPrimary));
            super.onDraw(cns);
            cns.restoreToCount(saveCount);
        }

    }
