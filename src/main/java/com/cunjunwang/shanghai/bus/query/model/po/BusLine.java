package com.cunjunwang.shanghai.bus.query.model.po;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
public class BusLine {
    private Long id;

    private String busLine;

    private String upGoingStartStation;

    private String upGoingTerminalStation;

    private String upGoingFirstTime;

    private String upGoingLastTime;

    private Integer upGoingStationCount;

    private String downGoingStartStation;

    private String downGoingTerminalStation;

    private String downGoingFirstTime;

    private String downGoingLastTime;

    private Integer downGoingStationCount;

    private BigDecimal lineLength;

    private String busDescription;

    private String busCost;

    private String busComment;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine == null ? null : busLine.trim();
    }

    public String getUpGoingStartStation() {
        return upGoingStartStation;
    }

    public void setUpGoingStartStation(String upGoingStartStation) {
        this.upGoingStartStation = upGoingStartStation == null ? null : upGoingStartStation.trim();
    }

    public String getUpGoingTerminalStation() {
        return upGoingTerminalStation;
    }

    public void setUpGoingTerminalStation(String upGoingTerminalStation) {
        this.upGoingTerminalStation = upGoingTerminalStation == null ? null : upGoingTerminalStation.trim();
    }

    public String getUpGoingFirstTime() {
        return upGoingFirstTime;
    }

    public void setUpGoingFirstTime(String upGoingFirstTime) {
        this.upGoingFirstTime = upGoingFirstTime == null ? null : upGoingFirstTime.trim();
    }

    public String getUpGoingLastTime() {
        return upGoingLastTime;
    }

    public void setUpGoingLastTime(String upGoingLastTime) {
        this.upGoingLastTime = upGoingLastTime == null ? null : upGoingLastTime.trim();
    }

    public Integer getUpGoingStationCount() {
        return upGoingStationCount;
    }

    public void setUpGoingStationCount(Integer upGoingStationCount) {
        this.upGoingStationCount = upGoingStationCount;
    }

    public String getDownGoingStartStation() {
        return downGoingStartStation;
    }

    public void setDownGoingStartStation(String downGoingStartStation) {
        this.downGoingStartStation = downGoingStartStation == null ? null : downGoingStartStation.trim();
    }

    public String getDownGoingTerminalStation() {
        return downGoingTerminalStation;
    }

    public void setDownGoingTerminalStation(String downGoingTerminalStation) {
        this.downGoingTerminalStation = downGoingTerminalStation == null ? null : downGoingTerminalStation.trim();
    }

    public String getDownGoingFirstTime() {
        return downGoingFirstTime;
    }

    public void setDownGoingFirstTime(String downGoingFirstTime) {
        this.downGoingFirstTime = downGoingFirstTime == null ? null : downGoingFirstTime.trim();
    }

    public String getDownGoingLastTime() {
        return downGoingLastTime;
    }

    public void setDownGoingLastTime(String downGoingLastTime) {
        this.downGoingLastTime = downGoingLastTime == null ? null : downGoingLastTime.trim();
    }

    public Integer getDownGoingStationCount() {
        return downGoingStationCount;
    }

    public void setDownGoingStationCount(Integer downGoingStationCount) {
        this.downGoingStationCount = downGoingStationCount;
    }

    public BigDecimal getLineLength() {
        return lineLength;
    }

    public void setLineLength(BigDecimal lineLength) {
        this.lineLength = lineLength;
    }

    public String getBusDescription() {
        return busDescription;
    }

    public void setBusDescription(String busDescription) {
        this.busDescription = busDescription == null ? null : busDescription.trim();
    }

    public String getBusCost() {
        return busCost;
    }

    public void setBusCost(String busCost) {
        this.busCost = busCost == null ? null : busCost.trim();
    }

    public String getBusComment() {
        return busComment;
    }

    public void setBusComment(String busComment) {
        this.busComment = busComment == null ? null : busComment.trim();
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