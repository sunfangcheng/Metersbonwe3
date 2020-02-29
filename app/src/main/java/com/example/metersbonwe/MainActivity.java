package com.example.metersbonwe;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_details)
    Button btDetails;
    @BindView(R.id.bt_address)
    Button btAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_details, R.id.bt_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_details:
                DetailsActivity.open(this);
                break;
            case R.id.bt_address:
                AddressActivity.open(this);
                break;
        }
    }
}
