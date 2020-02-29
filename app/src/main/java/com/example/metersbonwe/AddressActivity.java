package com.example.metersbonwe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metersbonwe.adapter.AddressAdapter;
import com.example.metersbonwe.adapter.SizeAdapter;
import com.example.metersbonwe.entity.AddressEntity;
import com.example.metersbonwe.entity.DetailEntity;
import com.example.metersbonwe.utils.GetJsonDataUtil;
import com.example.metersbonwe.utils.SpfUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends AppCompatActivity {
    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.add_address)
    TextView addAddress;
    AddressEntity addressEntity;
    private AddressAdapter addressAdapter;
    public static void open(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        addressEntity= SpfUtils.getInstance().readByObj("address",AddressEntity.class);
        if (addressEntity==null){
            String s = GetJsonDataUtil.getJson("data.json", this);
            Gson gson = new Gson();
            addressEntity = gson.fromJson(s, AddressEntity.class);
            SpfUtils.getInstance().saveByObj("address",addressEntity);
            addressAdapter = new AddressAdapter(AddressActivity.this, addressEntity.getResult());
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AddressActivity.this);
            recy.setLayoutManager(linearLayoutManager);
            recy.setAdapter(addressAdapter);
        }
         else {
            addressAdapter = new AddressAdapter(AddressActivity.this, addressEntity.getResult());
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AddressActivity.this);
            recy.setLayoutManager(linearLayoutManager);
            recy.setAdapter(addressAdapter);
        }
    }

    @OnClick(R.id.add_address)
    public void onViewClicked() {
       EditAddressActivity.open(AddressActivity.this,addressEntity);
    }
}
