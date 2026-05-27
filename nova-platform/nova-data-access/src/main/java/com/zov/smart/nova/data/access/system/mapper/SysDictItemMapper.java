package com.zov.smart.nova.data.access.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zov.smart.nova.data.access.system.entity.SysDictItemDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典项 Mapper。
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItemDO> {

    List<SysDictItemDO> selectItemsByDictCode(@Param("dictCode") String dictCode);

    IPage<SysDictItemDO> selectDictItemPage(IPage<SysDictItemDO> page,
                                            @Param("dictCode") String dictCode,
                                            @Param("itemLabel") String itemLabel,
                                            @Param("status") String status);
}
