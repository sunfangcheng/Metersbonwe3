package com.example.metersbonwe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metersbonwe.R;
import com.example.metersbonwe.entity.DetailEntity;
import com.example.metersbonwe.utils.GlideUtil;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{
    private Context context;
    private OnItemClickListener onItemClickListener;//声明一下这个接口
    //提供setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{//也可以不在这个activity或者是fragment中来声明接口，可以在项目中单独创建一个interface，就改成static就OK
        //参数（父组件，当前单击的View,单击的View的位置，数据）
        void onItemClick(View view, int position);
    }
    private List<DetailEntity.ResultBean.SaleAttrListBean.SaleAttr1ListBean> list;

    public MyAdapter(Context context, List<DetailEntity.ResultBean.SaleAttrListBean.SaleAttr1ListBean> list) {

        this.context = context;

        this.list = list;

    }

    @NonNull

    @Override

    public MyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MyHolder myHolder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false));
        return myHolder;

    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyHolder myHolder, final int i) {
        GlideUtil.loadLocalImg(context,list.get(i).getImageUrl(),myHolder.imageView);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,i);
            }
        });
    }

    @Override

    public int getItemCount() {
        return list.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MyHolder(@NonNull View itemView) {

            super(itemView);

            imageView = itemView.findViewById(R.id.iv);

        }

    }


}
