package com.example.metersbonwe.base.adapt;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public Context mContext;
    public T data;

    public BaseViewHolder(View v) {
        super(v);
        mContext = v.getContext();
        if (((ViewGroup) v).getChildCount() > 0)
            ButterKnife.bind(this, v);
    }

    /**
     * ViewHolder的Type，同时也是它的LayoutId
     *
     * @return
     */
    public abstract int getType();

    /**
     * 绑定ViewHolder
     *
     * @return
     */

    protected void onBindViewHolder(View view, T obj) {
        this.data = obj;
    }
}
