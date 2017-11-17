package comp.sun.com.customcoms;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017-11-17.
 */

public class CustomProgress extends View {


    private static int DEFAULT_SIZE = 500; //默认空间大小
    private static int DEFAULT_RADIUS = 100; //默认直径

    private int progress;
    private int maxProgress;
    private Paint mCirclePaint;


    private int mWidth;
    private int mHeight;

    private int mRadius;
    private int mStrokeWidth;

    public CustomProgress(Context context) {
        super(context);
        init();
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPaint();
    }


    //初始化画笔
    private void initPaint(){
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.argb(255, 48, 63, 159));
        mCirclePaint.setStrokeWidth(mStrokeWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         mWidth = getViewSize(widthMeasureSpec);
        mHeight = getViewSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int getViewSize(int measureSpec) {
        int size = DEFAULT_SIZE;
        int mode = MeasureSpec.getMode(measureSpec);
        int modeSize = MeasureSpec.getSize(measureSpec);
        return mode == MeasureSpec.AT_MOST || mode == MeasureSpec.EXACTLY ? modeSize : size;
    }
}
