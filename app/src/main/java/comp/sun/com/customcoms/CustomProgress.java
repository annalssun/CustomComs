package comp.sun.com.customcoms;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017-11-17.
 */

public class CustomProgress extends View {


    private  int DEFAULT_SIZE = DisplayUtil.px2dip(getContext(),300); //默认空间大小
    private  int DEFAULT_RADIUS = DisplayUtil.px2dip(getContext(),10); //默认直径

    private int progress=100;
    private int maxProgress =100;
    private Paint mCirclePaint;
    private Paint mArcPaint;


    private int mWidth;
    private int mHeight;

    private int mRadius;
    private int mStrokeWidth = DisplayUtil.px2dip(getContext(),40);

    private int currentProgress=0;

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
    private void initPaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.argb(255, 48, 63, 159));
        mCirclePaint.setStrokeWidth(mStrokeWidth);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(Color.RED);
        mArcPaint.setStrokeWidth(mStrokeWidth);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = mWidth - paddingLeft - paddingRight;
        int height = mHeight - paddingBottom - paddingTop;

        float centerX = paddingLeft+width/2;
        float centerY = paddingTop+height/2;

        //半径=长、宽最小值/2-边界线的宽度
        float radius = Math.min(width, height) / 2-mStrokeWidth;

        /**画外侧圆*/
        canvas.drawCircle(centerX, centerY, radius, mCirclePaint);

        float startAngle = -90;
        float sweepAngle = 360 * currentProgress / maxProgress;
        RectF rectF= new RectF(centerX-radius,centerY-radius,centerX+radius,centerY+radius);
        canvas.drawArc(rectF,startAngle-270*currentProgress/maxProgress,-sweepAngle,false,mArcPaint);

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

    public void start(){
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.play(animationToCircle());
        animationSet.start();
    }

    private ValueAnimator animationToCircle() {
        ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentProgress = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        return animator;
    }
}
