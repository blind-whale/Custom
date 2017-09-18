package com.custom.views.v2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.custom.views.R;
import com.custom.views.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/9/18.
 * Des: 萤火飞舞 粒子运动
 * Address: https://github.com/JadynAi/Particle
 */

public class FirewormsView extends View {
    private int mWidth,mHight;
    private Bitmap bitmap;//粒子图片
    private List<Particle> particles;
    private int particleNum=25;
    private Matrix matrix;
    private Paint mPaint;
    private int bmpRes;
    private Random random=new Random();
    private ValueAnimator timeAnimator;
    public FirewormsView(Context context) {
        this(context,null);
    }

    public FirewormsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FirewormsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray tArray=context.obtainStyledAttributes(attrs, R.styleable.FirewormsView);
        bmpRes=tArray.getResourceId(R.styleable.FirewormsView_particle_sre,R.drawable.ic_launcher_round);
        particleNum=tArray.getInteger(R.styleable.FirewormsView_particle_num,particleNum);
        tArray.recycle();
        init();
    }

    private void init(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha(Particle.ALPHA_MAX);
        mPaint.setStyle(Paint.Style.FILL);
        setLayerType(LAYER_TYPE_HARDWARE,null);
        matrix=new Matrix();
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        bitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getResources(),bmpRes,options),
                UIUtils.dip2pixel(getContext(),25),UIUtils.dip2pixel(getContext(),25));

        timeAnimator=ValueAnimator.ofInt(0).setDuration(30);
        timeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        timeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                invalidate();
           }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=getMeasuredWidth();
        mHight=getMeasuredHeight();
        particles=new ArrayList<>();
        for(int i=0;i<particleNum;i++){
            particles.add(new Particle(bitmap,matrix,mPaint,mWidth,mHight,
                    mWidth*getRandomF(),mHight*getRandomF()));
        }
        if(!timeAnimator.isRunning()){
            timeAnimator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        for(Particle p:particles){
            p.draw(canvas);
        }
        canvas.restore();
    }

    private float getRandomF(){
        float value=random.nextFloat();
        if(value<0.15f){
            value+=0.15f;
        }else if(value>0.85f){
            value-=0.15f;
        }
        return value;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(timeAnimator!=null){
            timeAnimator.end();
            timeAnimator=null;
        }
    }
}
