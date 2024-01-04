package com.vivo.internet.moonbox.dal.entity;

import java.util.Date;

public class SysApp {
    /**
    * 自增主键
    */
    private Long id;

    /**
    * 系统编码
    */
    private String systemCode;

    /**
    * 系统名称
    */
    private String systemName;

    /**
    * 项目名
    */
    private String appName;

    /**
    * netty管道id
    */
    private String channelId;

    /**
    * 最后上报时间
    */
    private Date lastHeartbeatTime;

    /**
    * 机器ip
    */
    private String ip;

    /**
    * 补充字段
    */
    private String remark;

    /**
    * 创建人
    */
    private String creator;

    /**
    * 环境
    */
    private String appEnv;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 数据删除状态,1在线，0为离线
    */
    private Integer onlineState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode == null ? null : systemCode.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId == null ? null : channelId.trim();
    }

    public Date getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }

    public void setLastHeartbeatTime(Date lastHeartbeatTime) {
        this.lastHeartbeatTime = lastHeartbeatTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getAppEnv() {
        return appEnv;
    }

    public void setAppEnv(String appEnv) {
        this.appEnv = appEnv == null ? null : appEnv.trim();
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

    public Integer getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(Integer onlineState) {
        this.onlineState = onlineState;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysApp other = (SysApp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSystemCode() == null ? other.getSystemCode() == null : this.getSystemCode().equals(other.getSystemCode()))
            && (this.getSystemName() == null ? other.getSystemName() == null : this.getSystemName().equals(other.getSystemName()))
            && (this.getAppName() == null ? other.getAppName() == null : this.getAppName().equals(other.getAppName()))
            && (this.getChannelId() == null ? other.getChannelId() == null : this.getChannelId().equals(other.getChannelId()))
            && (this.getLastHeartbeatTime() == null ? other.getLastHeartbeatTime() == null : this.getLastHeartbeatTime().equals(other.getLastHeartbeatTime()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getAppEnv() == null ? other.getAppEnv() == null : this.getAppEnv().equals(other.getAppEnv()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getOnlineState() == null ? other.getOnlineState() == null : this.getOnlineState().equals(other.getOnlineState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSystemCode() == null) ? 0 : getSystemCode().hashCode());
        result = prime * result + ((getSystemName() == null) ? 0 : getSystemName().hashCode());
        result = prime * result + ((getAppName() == null) ? 0 : getAppName().hashCode());
        result = prime * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
        result = prime * result + ((getLastHeartbeatTime() == null) ? 0 : getLastHeartbeatTime().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getAppEnv() == null) ? 0 : getAppEnv().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getOnlineState() == null) ? 0 : getOnlineState().hashCode());
        return result;
    }
}