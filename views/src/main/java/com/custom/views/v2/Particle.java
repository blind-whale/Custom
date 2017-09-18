package com.custom.views.v2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Administrator on 2017/9/18.
 * 粒子
 */

public class Particle {
    private static String TAG="Particle";
    public static int ALPHA_MAX=160;
    public static int ALPHA_MIN=75;

    private Random random=new Random();

    private float mX,mY,mStartX,mStartY;
    private float mDeltaX,mDeltaY;
    private boolean mIsAddX,mIsAddY;
    private int mWidth,mHeight;

    private Paint mPaint;
    private Matrix mBmpMatrix;
    private Bitmap mBitmap;
    private float mBmpCenterX,mBmpCenterY;
    private int mBmpW,mBmpH;
    private float mAngle;
    private float mDeltaAngle;

    public Particle(Bitmap bmp,Matrix matrix,Paint paint,int width,int height,float x,float y){
        mX=x;
        mY=y;
        mStartX=x;
        mStartY=y;
        mPaint=paint;
        mBitmap=bmp;
        mBmpMatrix=matrix;
        mWidth=width;
        mHeight=height;
        mBmpW=bmp.getWidth();
        mBmpH=bmp.getHeight();
        mBmpCenterX=mBmpW/2f;
        mBmpCenterY=mBmpH/2f;

        mIsAddX=random.nextBoolean();
        mIsAddY=random.nextBoolean();

        setRandomParam();

    }

    /**
     * 初始化粒子的运动和自转速度，或当粒子碰触边界时，改变粒子的运动速度和自转速度
     */
    private void setRandomParam(){
        mDeltaX=random.nextInt(2)+1.2f;
        mDeltaY=random.nextInt(2)+1.2f;
        mDeltaAngle=random.nextInt(5)+3f;
    }

    public void draw(Canvas canvas){
        mBmpMatrix.reset();
        mBmpMatrix.preTranslate(mX=getNewState(mIsAddX,mX,mDeltaX),mY=getNewState(mIsAddY,mY,mDeltaY));
        mBmpMatrix.preRotate(mAngle=getNewState(true,mAngle,mDeltaAngle),mBmpCenterX,mBmpCenterY);
        canvas.drawBitmap(mBitmap,mBmpMatrix,mPaint);
        judgeIsOverEdge();
    }

    /**
     * 获取新的mX、mY,mAngle的值
     * @param isAdd
     * @param origin
     * @param delta
     * @return
     */
    private float getNewState(boolean isAdd,float origin,float delta){
        return isAdd?(origin+delta):(origin-delta);
    }

    /**
     * mX,m如果触碰或超出边界，则改变粒子的运动方向，运动速度，自转速度
     */
    private void judgeIsOverEdge(){
        boolean judgeX=mX<=0||(mX>=mWidth-mBmpW);
        boolean judgeY=mY<=0||(mY>=mHeight-mBmpH);

        if(judgeX){
            mIsAddX=!mIsAddX;
            mIsAddY=random.nextBoolean();
            setRandomParam();
            if(mX<=0){
                mX=0;
            }else{
                mX=mWidth-mBmpW;
            }
            return;
        }

        if(judgeY){
            mIsAddY=!mIsAddY;
            mIsAddX=random.nextBoolean();
            setRandomParam();
            if(mY<=0){
                mY=0;
            }else{
                mY=mHeight-mBmpH;
            }
        }
    }



}
