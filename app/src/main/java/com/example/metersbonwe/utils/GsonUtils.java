package com.example.metersbonwe.utils;

import com.example.metersbonwe.entity.JsonBean;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * @author ：sfc
 *         作者：sfc on 2018/1/9 11:44
 *         emil：孙方成 1021190889@qq.com
 *         功能描述：
 */

public class GsonUtils {
    public static ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
