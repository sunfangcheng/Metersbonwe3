package com.example.metersbonwe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metersbonwe.R;
import com.example.metersbonwe.entity.AddressEntity;
import com.example.metersbonwe.entity.DetailEntity;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.MyHolder> {

    private Context context;

    private List<DetailEntity.ResultBean.SaleAttrListBean.SaleAttr2ListBean> list;
    private OnItemClickListener onItemClickListener;//声明一下这个接口
    //提供setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{//也可以不在这个activity或者是fragment中来声明接口，可以在项目中单独创建一个interface，就改成static就OK
        //参数（父组件，当前单击的View,单击的View的位置，数据）
        void onItemClick(View view, int position);
    }
    public SizeAdapter(Context context, List<DetailEntity.ResultBean.SaleAttrListBean.SaleAttr2ListBean> list) {

        this.context = context;

        this.list = list;

    }

    @NonNull

    @Override

    public SizeAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MyHolder myHolder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_size, viewGroup, false));

        return myHolder;

    }

    @Override

    public void onBindViewHolder(@NonNull SizeAdapter.MyHolder myHolder, final int i) {
        myHolder.textView.setText(list.get(i).getSaleAttr2Value());
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

        private TextView textView;

        public MyHolder(@NonNull View itemView) {

            super(itemView);

            textView = itemView.findViewById(R.id.tv_name);

        }

    }

}
