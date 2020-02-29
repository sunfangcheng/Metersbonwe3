package com.example.metersbonwe.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.metersbonwe.R;
import com.example.metersbonwe.view.callback.TopBarCallback;


/**
 * Created by 复诚博纳 on 2018/1/3.
 */

public class TopBar extends Toolbar implements OnClickListener{

    ImageButton btn_left;
    TextView tv_middle;

    public TextView getTv_middle() {
        return tv_middle;
    }

    public void setTvMiddleStyle(boolean isBold){
        if (isBold)
            tv_middle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        else
            tv_middle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    public void setTv_middle(String middle) {
        this.tv_middle.setText(middle);
    }

    public TextView getTv_right() {
        return tv_right;
    }

    public void setTv_right(TextView tv_right) {
        this.tv_right = tv_right;
    }


    public void setRightVisible(boolean isVisible) {
        this.tv_right.setVisibility(isVisible?VISIBLE:GONE);
    }

    public void setTv_rightContent(String rightContent) {
        if(this.tv_right != null){
            tv_right.setText(rightContent);
        }
    }

    TextView tv_right;

    TopBarCallback topBarCallback;

    public void setTopBarCallback(TopBarCallback topBarCallback) {
        this.topBarCallback = topBarCallback;
    }

    public TopBar(Context context) {
        super(context,null);
    }

    public TopBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TopBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init( AttributeSet attrs){
        setContentInsetsRelative(0,0);
        setContentInsetsAbsolute(0,0);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_topbar,null);

        if(attrs == null) return;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TopBar);
        String middleText = typedArray.getString(R.styleable.TopBar_tb_middleText);
        boolean bold = typedArray.getBoolean(R.styleable.TopBar_tb_middleTextBold,false);
        String rightText = typedArray.getString(R.styleable.TopBar_tb_rightText);
        int rightImage = typedArray.getResourceId(R.styleable.TopBar_tb_rightImage,0);
        int leftImage = typedArray.getResourceId(R.styleable.TopBar_tb_leftImage,0);
        Boolean isLeftBtnShow = typedArray.getBoolean(R.styleable.TopBar_tb_isLeftBtnShow ,true);
        Boolean isRightBtnShow = typedArray.getBoolean(R.styleable.TopBar_tb_isRightBtnShow ,true);
        int middleTextColor= typedArray.getColor(R.styleable.TopBar_tb_middleTextColor, Color.BLACK);
        int rightTextColor = typedArray.getColor(R.styleable.TopBar_tb_rightTextColor, Color.BLACK);
        tv_middle = (TextView) view.findViewById(R.id.tv_middle);
        if(bold){
            TextPaint paint = tv_middle.getPaint();
            paint.setFakeBoldText(true);
        }
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        btn_left = (ImageButton) view.findViewById(R.id.btn_left);
        if(isLeftBtnShow){
            btn_left.setOnClickListener(this);
        }else {
            btn_left.setVisibility(GONE);
        }
        tv_right.setVisibility(isRightBtnShow?VISIBLE:GONE);
        tv_right.setOnClickListener(this);
        tv_middle.setText(middleText);
        tv_middle.setTextColor(middleTextColor);
        tv_right.setText(rightText);
        tv_right.setTextColor(rightTextColor);
        if (leftImage!=0){
            btn_left.setImageResource(leftImage);
        }
        if(rightImage!=0)
            tv_right.setCompoundDrawablesWithIntrinsicBounds(rightImage,0,0,0);
        typedArray.recycle();
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onClick(View view) {
       if(view.getId() == R.id.btn_left){
           if(topBarCallback==null||topBarCallback.onLeftClicked(view))
               if (getContext() instanceof Activity) ((Activity) getContext()).onBackPressed();
       }else if(view.getId() == R.id.tv_right){
           if(topBarCallback!= null){
               topBarCallback.onRightClicked(view);
           }
       }
    }
}
