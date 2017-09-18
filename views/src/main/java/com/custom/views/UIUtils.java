package com.custom.views;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 2017/9/15.
 */

public class UIUtils {

    /**
     * dip to pixel
     * @param context
     * @param dip
     * @return
     */
    public static int dip2pixel(Context context,int dip){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,context.getResources().getDisplayMetrics());
    }

    public static int sp2pixel(Context context,int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.getResources().getDisplayMetrics());
    }
}
