package com.example.metersbonwe.base;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.metersbonwe.R;


/**
 * Created by 复诚博纳 on 2018/1/8.
 */

public abstract class BaseDialog {
    protected AlertDialog mDialog;
    protected Context mContext;
    public BaseDialog(Context context)
    {
        mContext = context;
    }


    public void show()
    {
        if(isShowing()) return;
        if(mDialog==null) {
            mDialog = new AlertDialog.Builder(mContext).create();
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            setContentView(mDialog);
            setWindow();
            loadOtherData();
        }else {
            mDialog.show();
        }
    }

    protected void setWindow() {
        Window window = mDialog.getWindow();
        //为了让EditText可以获取焦点
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = getGravity();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setWindowAnimations(getWindowAnimations());
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    protected void loadOtherData(){}

    public boolean isShowing()
    {
        if(mDialog != null) return mDialog.isShowing();
        return false;
    }

    public void cancel()
    {
        if(mDialog != null && mDialog.isShowing()) mDialog.cancel();
    }

    //可以子类重写 修改动画
    protected int getWindowAnimations()
    {
        //中间的动画
        return R.style.PopAnimation_Magnify;
        //从下面向上移动
//		return R.style.PopAnimationSilideButtom;
    }

    //可以子类重写 修改位置
    protected int getGravity()
    {
        return Gravity.CENTER;
    }

    public abstract View getView(Context context);

    public  void setContentView(AlertDialog mDialog){
        mDialog.setContentView(getView(mContext));
    }
}
