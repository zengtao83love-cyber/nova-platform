package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zov.smart.nova.data.access.system.entity.SysDictTypeDO;
import org.apache.ibatis.annotations.Param;

/**
 * 字典类型 Mapper。
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictTypeDO> {

    SysDictTypeDO selectByDictCode(@Param("dictCode") String dictCode);

    IPage<SysDictTypeDO> selectDictTypePage(IPage<SysDictTypeDO> page,
                                            @Param("dictCode") String dictCode,
                                            @Param("dictName") String dictName,
                                            @Param("status") String status);
}
