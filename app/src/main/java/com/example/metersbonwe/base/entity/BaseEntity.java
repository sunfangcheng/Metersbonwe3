package com.example.metersbonwe.base.entity;


import java.io.Serializable;

/**
 * Created by xionghongjie on 2017/3/1.
 */

public class BaseEntity implements Serializable {


    protected long _id;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
