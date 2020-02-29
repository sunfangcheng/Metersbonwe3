package com.example.metersbonwe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metersbonwe.adapter.MyAdapter;
import com.example.metersbonwe.adapter.SizeAdapter;
import com.example.metersbonwe.base.BaseDialog;
import com.example.metersbonwe.entity.DetailEntity;
import com.example.metersbonwe.utils.GlideUtil;
import com.example.metersbonwe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ：sfc
 * 作者：sfc on 2018/1/10 16:17
 * emil：孙方成 1021190889@qq.com
 * 功能描述：
 */

public class ChoseDialog extends BaseDialog {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.color_recy)
    RecyclerView colorRecy;
    @BindView(R.id.size_recy)
    RecyclerView sizeRecy;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_insure)
    TextView tvInsure;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.tv_color)
    TextView tvColor;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_dec)
    TextView tvDec;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_has_sum)
    TextView tvHasSum;
    private DetailEntity detailEntity;
    private Context mContext;
    private MyAdapter myAdapter;
    private SizeAdapter sizeAdapter;
    private String selectColor;
    private String selectSize;
    private int maxNum;

    public ChoseDialog(Context context, DetailEntity detailEntity) {
        super(context);
        this.detailEntity = detailEntity;
        this.mContext = context;
    }

    List<DetailEntity.ResultBean.SkuInfoBean> skuInfoBeans = new ArrayList<>();

    @Override
    public View getView(Context context) {
        View view_pop = LayoutInflater.from(context).inflate(R.layout.layout_pop, null);
        ButterKnife.bind(this, view_pop);
        setData();
        return view_pop;
    }

    private void setData() {
        GlideUtil.loadLocalImg(mContext, detailEntity.getResult().getProductUrl(), iv);
        tvPrice.setText("¥" + detailEntity.getResult().getSalePrice());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        colorRecy.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(mContext, detailEntity.getResult().getSaleAttrList().getSaleAttr1List());
        colorRecy.setAdapter(myAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        sizeRecy.setLayoutManager(gridLayoutManager);
        sizeAdapter = new SizeAdapter(mContext, detailEntity.getResult().getSaleAttrList().getSaleAttr2List());
        sizeRecy.setAdapter(sizeAdapter);
        skuInfoBeans = detailEntity.getResult().getSkuInfo();

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "选择了" + detailEntity.getResult().getSaleAttrList().getSaleAttr1List().get(position).getSaleAttr1Value(), Toast.LENGTH_SHORT).show();
                selectColor = detailEntity.getResult().getSaleAttrList().getSaleAttr1List().get(position).getSaleAttr1ValueCode();
                getMaxNum(selectColor, selectSize);
            }
        });
        sizeAdapter.setOnItemClickListener(new SizeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "选择了" + detailEntity.getResult().getSaleAttrList().getSaleAttr2List().get(position).getSaleAttr2Value(), Toast.LENGTH_SHORT).show();
                selectSize = detailEntity.getResult().getSaleAttrList().getSaleAttr2List().get(position).getSaleAttr2ValueCode();
                getMaxNum(selectColor, selectSize);
            }
        });

    }

    private void getMaxNum(String selectColor, String selectSize) {
        for (int i = 0; i < skuInfoBeans.size(); i++) {
            if (skuInfoBeans.get(i).getSaleAttr1ValueCode().equals(selectColor) && skuInfoBeans.get(i).getSaleAttr2ValueCode().equals(selectSize)) {
                maxNum = skuInfoBeans.get(i).getStockNum();
                tvHasSum.setText("还剩" + maxNum + "件");
            }
        }
    }

    @Override
    protected void setWindow() {
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = getGravity();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 1400;
        window.setWindowAnimations(getWindowAnimations());
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getWindowAnimations() {
        return R.style.PopAnimation_Down_Up;
    }

    @OnClick({R.id.iv_close, R.id.tv_insure, R.id.tv_dec, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                cancel();
                break;
            case R.id.tv_insure:
                if (Integer.parseInt(tvNum.getText().toString())<1){
                    ToastUtil.show("请选择购买件数");
                }{
                    ToastUtil.show("提交成功");
                }
                break;
            case R.id.tv_dec:
                int num2=Integer.parseInt(tvNum.getText().toString());
                if (num2>1){
                    tvNum.setText(num2-1+"");
                }else {
                    ToastUtil.show("最少购买一件");
                }
                break;
            case R.id.tv_add:
                int num=Integer.parseInt(tvNum.getText().toString());
                if (num<maxNum){
                    tvNum.setText(num+1+"");
                }
                else {
                    ToastUtil.show("库存不足");
                }
                break;
        }
    }
}
