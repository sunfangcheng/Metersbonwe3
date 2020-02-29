package com.example.metersbonwe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.metersbonwe.base.App;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 针对SharedPreferences 的封装类
 *
 * @author xhj
 */
public class SpfUtils {
    static private SpfUtils instance;
    private SharedPreferences spf;
    private String SpfName = "";
    protected Context context;

    public static String CitysData = "CitysData";
    public static String DetailData = "DetailData";
    public static String CitysTime= "CitysTime";


    public static final String safeState = "SafeState";

    public SpfUtils(Context con, String SpfName) {
        spf = con.getSharedPreferences(SpfName, Context.MODE_PRIVATE);
        this.SpfName = SpfName;
    }

    static public SpfUtils getInstance() {
        return getInstance(App.getAppContext());
    }

    static public SpfUtils getInstance(Context con) {
        if (instance == null) {
            crateObj(con);
            instance.context = con;
        } else {
            if (con != instance.context) {
                instance = null;
                crateObj(con);
            }
        }
        return instance;
    }

    protected synchronized static void crateObj(Context context) {
        if (instance == null) {
            instance = new SpfUtils(context, "mySpfData");
        }
    }

    protected synchronized static void crateObj(Context context, String spfName) {
        if (instance == null) {
            instance = new SpfUtils(context, spfName);
        }
    }

    /**
     * 传如一个自己想写的缓存文件名称，当与之前的不同的时间将可以自动更改保存缓存数据的位置
     *
     * @param con
     * @param SpyName
     * @return
     */
    static public SpfUtils getInstance(Context con, String SpyName) {
        if (instance == null) {
            crateObj(con, SpyName);
            instance.context = con;
        } else {
            if (con != instance.context) {
                instance = null;
                crateObj(con, SpyName);
            }
        }
        return instance;
    }

    /**
     * 保存一个对象到spf
     *
     * @param key
     * @param value
     */
    public void saveByObj(String key, Object value) {
        if (value != null) {
            String data =new Gson().toJson(value, value.getClass());
            save(key, data);
        } else {
            /**
             * 当为null的时候移除key的内容
             */
            remove(key);
        }
    }

    /**
     * 保存一个对象到spf
     *
     * @param key
     * @param list
     */
    public void saveByList(String key, List list, Type type) {
        if (list != null) {
            String data = new Gson().toJson(list, type);
            save(key, data);
        } else {
            /**
             * 当为null的时候移除key的内容
             */
            remove(key);
        }
    }

    /**
     * 保存一个数据到缓存文件中，当出现相同的key将会将新的value覆盖进去
     *
     * @param Key
     * @param Value
     */
    public void save(String Key, String Value) {
        if (Key.equals("Cookie")) {
        }
        Key = keyFilter(Key);
        Editor e = spf.edit();
        e.putString(Key, Value);
        e.commit();
        e.apply();
    }

    /**
     * 清除当前缓存文件的全部数据
     */
    public void Clear() {
        Editor e = spf.edit();
        e.clear();
        e.commit();
        e.apply();
    }

    /**
     * 读取一个数据，当数据不存在的时候返回null
     *
     * @param key
     * @param Class
     * @param <T>
     * @return
     */
    public <T> T readByObj(String key, Class<T> Class) {
        String data =read(key, "@null");
        T result;
        if (data.equals("@null")) {
            return null;
        } else {
            result = new Gson().fromJson(data, Class);
            return result;
        }
    }

    /**
     * 读取一个数据，当数据不存在的时候返回null
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T readByList(String key, Type typeofT) {
        String data = read(key, "@null");
        T result;
        if (data.equals("@null")) {
            return null;
        } else {
            result =new Gson().fromJson(data, typeofT);
            return result;
        }

    }

    /**
     * 读取缓存文件中某指定节点的数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public  synchronized String read(String key, String defValue) {
        key =keyFilter(key);
        return spf.getString(key, defValue);
    }

    private String keyFilter(String key){
//        if(key.equals(TAG_SPF_GESTURE_PWD)||key.equals(safeState)){
//            if(UserComm.userInfo!=null&&!StringUtils.isEmpty(UserComm.userInfo.getUserCode())){
//                return key+ UserComm.userInfo.getUserCode();
//            }
//        }
        // TODO: 2018/1/18  增加你要过滤d
        return key;
    }

    /**
     * 移除某指定的key
     *
     * @param key
     */
    public void remove(String key) {
        key =keyFilter(key);
        Editor e = spf.edit();
        e.remove(key);
        e.commit();
        e.apply();
    }

    /**
     * 读取缓存文件中某指定节点的数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public synchronized boolean readBoolean(String key, boolean defValue) {
        key =keyFilter(key);
        return spf.getBoolean(key, defValue);
    }

    /**
     * 保存一个数据到缓存文件中，当出现相同的key将会将新的value覆盖进去
     *
     * @param Key
     * @param Value
     */
    public void saveBoolean(String Key, boolean Value) {
        Key = keyFilter(Key);
        Editor e = spf.edit();
        e.putBoolean(Key, Value);
        e.commit();
        e.apply();
    }
}
