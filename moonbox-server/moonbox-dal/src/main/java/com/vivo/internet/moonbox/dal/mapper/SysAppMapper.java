package com.vivo.internet.moonbox.dal.mapper;

import com.vivo.internet.moonbox.dal.entity.SysApp;
import com.vivo.internet.moonbox.dal.entity.SysAppExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysAppMapper {
    long countByExample(SysAppExample example);

    int deleteByExample(SysAppExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysApp record);

    int insertSelective(SysApp record);

    List<SysApp> selectByExample(SysAppExample example);

    SysApp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysApp record, @Param("example") SysAppExample example);

    int updateByExample(@Param("record") SysApp record, @Param("example") SysAppExample example);

    int updateByPrimaryKeySelective(SysApp record);

    int updateByPrimaryKey(SysApp record);
}