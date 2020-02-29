package com.example.metersbonwe.base.entity;

/**
 * Created by sfc on 2017/3/29.
 */

public class BaseDataResponse<T> extends BaseResponse<T> {

    private HttpData<T> body;

    public T getBody() {
        if (body != null) return body.getData();
        return null;
    }

    public void setBody(HttpData<T> body) {
        this.body = body;
    }

    public class HttpData<T> {
        private T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
