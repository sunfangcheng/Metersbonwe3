package com.example.metersbonwe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metersbonwe.EditAddressActivity;
import com.example.metersbonwe.R;
import com.example.metersbonwe.entity.AddressEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;//声明一下这个接口

    //提供setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {//也可以不在这个activity或者是fragment中来声明接口，可以在项目中单独创建一个interface，就改成static就OK

        //参数（父组件，当前单击的View,单击的View的位置，数据）
        void onItemClick(View view, int position);
    }

    private List<AddressEntity.ResultBean> list;

    public AddressAdapter(Context context, List<AddressEntity.ResultBean> list) {

        this.context = context;

        this.list = list;

    }

    @NonNull

    @Override

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MyHolder myHolder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_address, viewGroup, false));
        return myHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        myHolder.tvName.setText(list.get(i).getConsignee());
        myHolder.tvAddress.setText(list.get(i).getProvinceName()+list.get(i).getCityName()+list.get(i).getDistrictName()+list.get(i).getAddress());
        myHolder.tvPhone.setText(list.get(i).getMobile());
        myHolder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAddressActivity.open(context,list.get(i),i);
            }
        });
    }

    @Override

    public int getItemCount() {
        return list.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPhone;
        TextView tvAddress;
        Button button;
        Button button2;
        Button button3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvPhone=itemView.findViewById(R.id.tv_phone);
            tvAddress=itemView.findViewById(R.id.tv_address);
            button2=itemView.findViewById(R.id.button2);
        }

    }
}
