package com.custom.views;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

/**
 * Created by Administrator on 2017/9/18.
 */

public class ImageUtils {

    public static Bitmap getThumbnailBmp(Bitmap source,int w,int h){
        return ThumbnailUtils.extractThumbnail(source,w,h);
    }
}
