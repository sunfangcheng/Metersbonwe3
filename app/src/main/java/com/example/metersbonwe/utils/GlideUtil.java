package com.example.metersbonwe.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;




/**
 * Created by 复诚博纳 on 2018/1/11.
 */

public class GlideUtil {
    private static int DEFULT_RADIUS=6;


    public static void loadLocalImg(Context context, String url, ImageView imageView){
            Glide.with(context).load("https://pic.bonwebuy.com"+url).dontAnimate().into(imageView);
    }
}
