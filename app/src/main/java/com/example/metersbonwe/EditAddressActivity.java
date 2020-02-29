package com.example.metersbonwe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.metersbonwe.entity.AddressEntity;
import com.example.metersbonwe.entity.JsonBean;
import com.example.metersbonwe.utils.GetJsonDataUtil;
import com.example.metersbonwe.utils.GsonUtils;
import com.example.metersbonwe.utils.SpfUtils;
import com.example.metersbonwe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditAddressActivity extends AppCompatActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_info)
    EditText etInfo;
    private String addr;
    private OptionsPickerView pvOptions;
    private static AddressEntity.ResultBean resultBean;
    private static AddressEntity addressEntity;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private String cityName;
    private String provName;
    private String areaName;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private Thread thread;
    private static int j;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    break;
                case MSG_LOAD_FAILED:
                    break;

            }
        }
    };

    public static void open(Context context, AddressEntity.ResultBean resultBean1,int i) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        context.startActivity(intent);
        resultBean = resultBean1;
        j=i;
    }

    public static void open(Context context,AddressEntity addressEntity1) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        context.startActivity(intent);
        resultBean=null;
        addressEntity=addressEntity1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_adress);
        ButterKnife.bind(this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        if (resultBean!=null){
            etName.setText(resultBean.getConsignee());
            etPhone.setText(resultBean.getMobile());
            etInfo.setText(resultBean.getAddress());
            tvCity.setText(resultBean.getProvinceName()+resultBean.getCityName()+resultBean.getDistrictName());
            cityName=resultBean.getCityName();
            provName=resultBean.getProvinceName();
            areaName=resultBean.getDistrictName();
        }
    }

    /**
     * 校验账号
     */
    private void cheack() {

        // 检查参数
        String userNameCheck = etName.getText().toString().trim();
        if (userNameCheck.isEmpty()) {
            ToastUtil.show("收货人姓名不能为空");
            //etLoginUsername.setError("用户名不能为空");
            return;
        }
        // 检查参数
        String userPhoneCheck = etPhone.getText().toString().trim();
        if (userNameCheck.isEmpty()) {
            ToastUtil.show("手机号不能为空");
            //etLoginUsername.setError("用户名不能为空");
            return;
        }
        if (!isMobile(userPhoneCheck)) {
            ToastUtil.show("手机号码格式不正确");
            return;
        }
        // 检查参数
        String cityCheck = tvCity.getText().toString().trim();
        if (("所在地区").equals(cityCheck)) {
            ToastUtil.show("请填写城市");
            //etLoginUsername.setError("用户名不能为空");
            return;
        }
        // 检查参数,验证码
        String verificationCodeCheck = etInfo.getText().toString();
        if (verificationCodeCheck.isEmpty()) {
            ToastUtil.show("请填写街道信息");
            return;
        }
        save();
    }

    private void save() {
        if (resultBean==null){
            ToastUtil.show("保存");
            AddressEntity.ResultBean resultBean1=new AddressEntity.ResultBean();
            resultBean1.setCityName(cityName);
            resultBean1.setProvinceName(provName);
            resultBean1.setDistrictName(areaName);
            resultBean1.setAddress(etInfo.getText().toString());
            resultBean1.setMobile(etPhone.getText().toString());
            resultBean1.setConsignee(etName.getText().toString());
            addressEntity.getResult().add(resultBean1);
            List<AddressEntity.ResultBean> resultBeanList=new ArrayList<>();
            resultBeanList.addAll(addressEntity.getResult());
            addressEntity.setResult(resultBeanList);
            SpfUtils.getInstance().saveByObj("address",addressEntity);
            this.finish();
        }else {
            ToastUtil.show("修改成功");
            addressEntity= SpfUtils.getInstance().readByObj("address",AddressEntity.class);
            for (int i=0;i<addressEntity.getResult().size();i++){
                if (j==i){
                    addressEntity.getResult().get(i).setProvinceName(provName);
                    addressEntity.getResult().get(i).setDistrictName(areaName);
                    addressEntity.getResult().get(i).setAddress(etInfo.getText().toString());
                    addressEntity.getResult().get(i).setMobile(etPhone.getText().toString());
                    addressEntity.getResult().get(i).setConsignee(etName.getText().toString());
                    addressEntity.getResult().get(i).setCityName(cityName);
                }
            }
            SpfUtils.getInstance().saveByObj("address",addressEntity);
            this.finish();
        }
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    @OnClick({R.id.tv_city, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city:
                ShowPickerView();
                break;
            case R.id.tv_save:
                cheack();
                break;
        }
    }

    private void ShowPickerView() {// 弹出选择器 /**
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tvCity.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));
                provName=options1Items.get(options1).getPickerViewText() + "";
                cityName=options2Items.get(options1).get(options2)  + "";
                areaName=options3Items.get(options1).get(options2).get(options3) + "";
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = GetJsonDataUtil.getJson("province.json", EditAddressActivity.this);//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = GsonUtils.parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);

        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
}
