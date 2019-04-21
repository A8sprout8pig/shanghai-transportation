package com.cunjunwang.shanghai.transportation.model.po;

import lombok.ToString;

import java.util.Date;

@ToString
public class BusStation {
    private Long id;

    private String busStationName;

    private String busLineGoThrough;

    private Integer busLineGoThroughNumber;

    private String busStationAddress;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusStationName() {
        return busStationName;
    }

    public void setBusStationName(String busStationName) {
        this.busStationName = busStationName == null ? null : busStationName.trim();
    }

    public String getBusLineGoThrough() {
        return busLineGoThrough;
    }

    public void setBusLineGoThrough(String busLineGoThrough) {
        this.busLineGoThrough = busLineGoThrough == null ? null : busLineGoThrough.trim();
    }

    public Integer getBusLineGoThroughNumber() {
        return busLineGoThroughNumber;
    }

    public void setBusLineGoThroughNumber(Integer busLineGoThroughNumber) {
        this.busLineGoThroughNumber = busLineGoThroughNumber;
    }

    public String getBusStationAddress() {
        return busStationAddress;
    }

    public void setBusStationAddress(String busStationAddress) {
        this.busStationAddress = busStationAddress == null ? null : busStationAddress.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}