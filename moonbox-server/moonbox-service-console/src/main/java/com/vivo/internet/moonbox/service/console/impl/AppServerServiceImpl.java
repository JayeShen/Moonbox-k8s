package com.vivo.internet.moonbox.service.console.impl;

import com.vivo.internet.moonbox.dal.entity.SysApp;
import com.vivo.internet.moonbox.dal.entity.SysAppExample;
import com.vivo.internet.moonbox.dal.mapper.SysAppMapper;
import com.vivo.internet.moonbox.service.console.AppServerService;
import com.vivo.internet.moonbox.service.console.vo.ActiveAppServerHostInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppServerServiceImpl implements AppServerService {

    @Resource
    private SysAppMapper sysAppMapper;
    @Override
    public List<ActiveAppServerHostInfoVo> selectAppServerHostInfoListFromAppName(String appName) {
        SysAppExample example = new SysAppExample();
        example.createCriteria().andAppNameEqualTo(appName);
        List<SysApp> sysApps = sysAppMapper.selectByExample(example);
        return  sysApps.stream().map(sysApp -> ActiveAppServerHostInfoVo.builder()
                .id(sysApp.getId())
                .systemCode(sysApp.getSystemCode())
                .systemName(sysApp.getSystemName())
                .appName(sysApp.getAppName())
                .channelId(sysApp.getChannelId())
                .lastHeartbeatTime(sysApp.getLastHeartbeatTime())
                .ip(sysApp.getIp())
                .remark(sysApp.getRemark())
                .creator(sysApp.getCreator())
                .appEnv(sysApp.getAppEnv())
                .createTime(sysApp.getCreateTime())
                .updateTime(sysApp.getUpdateTime())
                .onlineState(sysApp.getOnlineState())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<ActiveAppServerHostInfoVo> selectAppServerHostInfoList(ActiveAppServerHostInfoVo activeAppServerHostInfoVo) {
        SysAppExample example = new SysAppExample();
        example.createCriteria().
                andAppNameEqualTo(activeAppServerHostInfoVo.getAppName()).
                andIpEqualTo(activeAppServerHostInfoVo.getIp());
        List<SysApp> sysApps = sysAppMapper.selectByExample(example);
        return  sysApps.stream().map(sysApp -> ActiveAppServerHostInfoVo.builder()
                .id(sysApp.getId())
                .systemCode(sysApp.getSystemCode())
                .systemName(sysApp.getSystemName())
                .appName(sysApp.getAppName())
                .channelId(sysApp.getChannelId())
                .lastHeartbeatTime(sysApp.getLastHeartbeatTime())
                .ip(sysApp.getIp())
                .remark(sysApp.getRemark())
                .creator(sysApp.getCreator())
                .appEnv(sysApp.getAppEnv())
                .createTime(sysApp.getCreateTime())
                .updateTime(sysApp.getUpdateTime())
                .onlineState(sysApp.getOnlineState())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<ActiveAppServerHostInfoVo> selectAppServerHostInfoListFromChannelId(ActiveAppServerHostInfoVo activeAppServerHostInfoVo) {
        SysAppExample example = new SysAppExample();
        example.createCriteria().
                andChannelIdEqualTo(activeAppServerHostInfoVo.getChannelId()).
                andIpEqualTo(activeAppServerHostInfoVo.getIp());
        List<SysApp> sysApps = sysAppMapper.selectByExample(example);
        return  sysApps.stream().map(sysApp -> ActiveAppServerHostInfoVo.builder()
                .id(sysApp.getId())
                .systemCode(sysApp.getSystemCode())
                .systemName(sysApp.getSystemName())
                .appName(sysApp.getAppName())
                .channelId(sysApp.getChannelId())
                .lastHeartbeatTime(sysApp.getLastHeartbeatTime())
                .ip(sysApp.getIp())
                .remark(sysApp.getRemark())
                .creator(sysApp.getCreator())
                .appEnv(sysApp.getAppEnv())
                .createTime(sysApp.getCreateTime())
                .updateTime(sysApp.getUpdateTime())
                .onlineState(sysApp.getOnlineState())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<ActiveAppServerHostInfoVo> selectAppServerHostInfoListFromOnlineState(ActiveAppServerHostInfoVo activeAppServerHostInfoVo) {
        SysAppExample example = new SysAppExample();
        example.createCriteria().
                andAppNameEqualTo(activeAppServerHostInfoVo.getAppName()).
                andOnlineStateEqualTo(activeAppServerHostInfoVo.getOnlineState());
        List<SysApp> sysApps = sysAppMapper.selectByExample(example);
        return  sysApps.stream().map(sysApp -> ActiveAppServerHostInfoVo.builder()
                .id(sysApp.getId())
                .systemCode(sysApp.getSystemCode())
                .systemName(sysApp.getSystemName())
                .appName(sysApp.getAppName())
                .channelId(sysApp.getChannelId())
                .lastHeartbeatTime(sysApp.getLastHeartbeatTime())
                .ip(sysApp.getIp())
                .remark(sysApp.getRemark())
                .creator(sysApp.getCreator())
                .appEnv(sysApp.getAppEnv())
                .createTime(sysApp.getCreateTime())
                .updateTime(sysApp.getUpdateTime())
                .onlineState(sysApp.getOnlineState())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void insertAppServerHostInfo(ActiveAppServerHostInfoVo activeAppServerHostInfoVo) {
        SysApp sysApp = new SysApp();
        sysApp.setSystemCode(activeAppServerHostInfoVo.getSystemCode());
        sysApp.setSystemName(activeAppServerHostInfoVo.getSystemName());
        sysApp.setAppName(activeAppServerHostInfoVo.getAppName());
        sysApp.setChannelId(activeAppServerHostInfoVo.getChannelId());
        sysApp.setLastHeartbeatTime(new Date());
        sysApp.setIp(activeAppServerHostInfoVo.getIp());
        sysApp.setRemark(activeAppServerHostInfoVo.getRemark());
        sysApp.setCreator(activeAppServerHostInfoVo.getCreator());
        sysApp.setAppEnv(activeAppServerHostInfoVo.getAppEnv());
        sysApp.setCreateTime(new Date());
        sysApp.setOnlineState(1);
        sysAppMapper.insert(sysApp);
    }

    @Override
    public void updateAppServerHostInfo(ActiveAppServerHostInfoVo activeAppServerHostInfoVo) {
        SysApp sysApp = new SysApp();
        sysApp.setId(activeAppServerHostInfoVo.getId());
        sysApp.setSystemCode(activeAppServerHostInfoVo.getSystemCode());
        sysApp.setSystemName(activeAppServerHostInfoVo.getSystemName());
        sysApp.setAppName(activeAppServerHostInfoVo.getAppName());
        sysApp.setChannelId(activeAppServerHostInfoVo.getChannelId());
        sysApp.setLastHeartbeatTime(new Date());
        sysApp.setIp(activeAppServerHostInfoVo.getIp());
        sysApp.setRemark(activeAppServerHostInfoVo.getRemark());
        sysApp.setCreator(activeAppServerHostInfoVo.getCreator());
        sysApp.setAppEnv(activeAppServerHostInfoVo.getAppEnv());
        sysApp.setCreateTime(activeAppServerHostInfoVo.getCreateTime());
        sysApp.setOnlineState(activeAppServerHostInfoVo.getOnlineState());
        sysAppMapper.updateByPrimaryKeySelective(sysApp);
    }
}
