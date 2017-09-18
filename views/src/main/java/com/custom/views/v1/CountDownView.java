package com.custom.views.v1;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.custom.views.R;
import com.custom.views.UIUtils;

/**
 * Created by Administrator on 2017/9/15.
 * Address: https://github.com/SuperKotlin/PointLoadingView
 * Des: 环形 显示倒计时 进度。
 */

public class CountDownView extends View {

    // 圆形背景
    private Paint circlePaint;
    private int circleRadius;
    private int circleColor= Color.parseColor("#55B2E5");

    // 弧形进度条
    private Paint arcPaint;
    private int arcColor=Color.parseColor("#3C3F41");
    private int arcWidth;
    private int arcDirection;//1，顺时针；2，逆时针
    private float arcStartAngle;
    private float arcEndAngle;
    private float arcCurrentSweepAngle;

    // 剩余时间文本
    private Paint textPaint;
    private int textSize;
    private int textColor=Color.BLACK;
    private String timeUnit="";
    private int time;
    private String currentTime="";
    private OnTimeOverListener onTimeOverListener;

    public CountDownView(Context context) {
        this(context,null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray tArray=context.obtainStyledAttributes(attrs, R.styleable.CountDownView);

        circleRadius= (int) tArray.getDimension(R.styleable.CountDownView_cd_circle_radius, UIUtils.dip2pixel(context,25));
        circleColor=tArray.getColor(R.styleable.CountDownView_cd_circle_color,circleColor);

        arcWidth= (int) tArray.getDimension(R.styleable.CountDownView_cd_arc_width,UIUtils.dip2pixel(context,3));
        arcColor=tArray.getColor(R.styleable.CountDownView_cd_arc_color,arcColor);
        arcDirection=tArray.getInt(R.styleable.CountDownView_cd_direction,1);
        arcStartAngle=tArray.getInt(R.styleable.CountDownView_cd_start_point,0);

        textColor=tArray.getColor(R.styleable.CountDownView_cd_text_color,textColor);
        textSize= (int) tArray.getDimension(R.styleable.CountDownView_cd_text_size,UIUtils.sp2pixel(context,14));
        timeUnit=tArray.getString(R.styleable.CountDownView_cd_time_unit);
        if(timeUnit==null){
            timeUnit="";
        }
        time=tArray.getInteger(R.styleable.CountDownView_cd_time,3);

        tArray.recycle();

        init();
    }

    private void init(){
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(circleColor);
        arcPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setColor(arcColor);
        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(circleRadius*2,circleRadius*2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(circleRadius,circleRadius,circleRadius-arcWidth,circlePaint);

        RectF arcRect=new RectF(arcWidth/2,arcWidth/2,circleRadius*2-arcWidth/2,circleRadius*2-arcWidth/2);
        canvas.drawArc(arcRect,arcStartAngle,arcCurrentSweepAngle,false,arcPaint);

        float textW=textPaint.measureText(currentTime,0,currentTime.length());
        float textX=circleRadius-textW/2;
        Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
        float textY=circleRadius-(fontMetrics.bottom+fontMetrics.ascent)/2;
        canvas.drawText(currentTime,textX,textY,textPaint);
    }

    public void start(){
        ValueAnimator timeAnimator=ValueAnimator.ofInt(time,0);
        timeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (Integer) animation.getAnimatedValue();
                currentTime=value+timeUnit;
            }
        });

        ValueAnimator progressAnimator=null;

        switch (arcDirection){
            case 1:
                // 顺时针方向 进度条增长
                progressAnimator=ValueAnimator.ofFloat(0,360);
                break;
            case 2:
                // 顺时针方向 进度条递减
                progressAnimator=ValueAnimator.ofFloat(-360,0);
                break;
            case 3:
                // 逆时针方向 进度条增长
                progressAnimator= ValueAnimator.ofFloat(0,-360);
                break;
            case 4:
                // 逆时针方向 进度条递减
                progressAnimator=ValueAnimator.ofFloat(360,0);
                break;
        }
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arcCurrentSweepAngle= (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(timeAnimator,progressAnimator);
        animatorSet.setDuration(time*1000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(onTimeOverListener!=null)
                    onTimeOverListener.onTimeOver();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    public void setOnTimeOverListener(OnTimeOverListener listner){
        onTimeOverListener=listner;
    }

    public static interface OnTimeOverListener{
        void onTimeOver();
    }

}
