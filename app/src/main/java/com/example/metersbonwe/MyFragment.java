package com.example.metersbonwe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.metersbonwe.R;

public class MyFragment extends Fragment {

    private TextView mTv;

    public static MyFragment newInstance(String tag){
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        //传递参数
        myFragment.setArguments(bundle);
        return myFragment;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_first_fragment, null);
        //初始化控件
        mTv = (TextView) view.findViewById(R.id.first);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取参数
        Bundle arguments = getArguments();
        //改变值
        mTv.setText(arguments.getString("tag"));
    }
}
