package com.example.metersbonwe.base.entity;



/**
 * Created by xionghongjie on 2017/1/11.
 */

public class BaseRequest {

    public String token ;//= UserComm.userToken();

    public String appVersion;// = UserComm.currVersionCode();

    public String source = "Android";


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
