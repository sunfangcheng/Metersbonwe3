package com.example.metersbonwe.base.entity;

/**
 * @author ：sfc
 *         作者：sfc on 2018/1/10 15:45
 *         emil：孙方成 1021190889@qq.com
 *         功能描述：
 */

public class BannerEnity {

    /**
     * regionId : 1
     * createTime : 2018-01-04 16:14:25
     * regionName : ccc
     * updateTime : 2018-01-04 16:14:25
     * id : 123456
     * url : www.dcsfefw.com
     */

    private int regionId;
    private String createTime;
    private String regionName;
    private String updateTime;
    private int id;
    private String url;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
