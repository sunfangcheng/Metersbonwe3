package com.example.metersbonwe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.metersbonwe.entity.DetailEntity;
import com.example.metersbonwe.utils.GetJsonDataUtil;
import com.example.metersbonwe.utils.GlideUtil;
import com.example.metersbonwe.view.TopBar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.ll_below)
    LinearLayout llBelow;
    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_old_price)
    TextView tvOldPrice;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_select)
    TextView tvSelect;

    public static void open(Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        context.startActivity(intent);
    }

    List<String> mTitle;
    List<Fragment> mFragment;
   private   DetailEntity detailEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        getData();
        mTitle = new ArrayList<>();
        mTitle.add("详情");
        mTitle.add("评论");
        mFragment = new ArrayList<>();
        mFragment.add(MyFragment.newInstance("详情"));
        mFragment.add(MyFragment.newInstance("评论"));
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        tablayout.setupWithViewPager(viewpager);
    }

    private void getData() {
        String s = GetJsonDataUtil.getJson("details.json", this);
        Gson gson = new Gson();
        detailEntity = gson.fromJson(s, DetailEntity.class);
        if (detailEntity != null) {
            tvPrice.setText("¥" + detailEntity.getResult().getMarketPrice() + "");
            tvOldPrice.setText("初上市价格" + detailEntity.getResult().getProtectPrice() + "");
            // 设置数据
            banner.setPages(detailEntity.getResult().getGalleryList(), new MZHolderCreator<BannerViewHolder>() {
                @Override
                public BannerViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
        }
    }

    @OnClick(R.id.tv_select)
    public void onViewClicked() {
      ChoseDialog choseDialog=new ChoseDialog(DetailsActivity.this,detailEntity);
      choseDialog.show();
    }

    public static class BannerViewHolder implements MZViewHolder<DetailEntity.ResultBean.GalleryListBean> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int i, DetailEntity.ResultBean.GalleryListBean galleryListBeans) {
            GlideUtil.loadLocalImg(context, galleryListBeans.getImageUrl(), mImageView);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        banner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();//开始轮播
    }
}
