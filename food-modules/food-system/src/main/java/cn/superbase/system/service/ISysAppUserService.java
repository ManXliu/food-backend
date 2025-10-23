package cn.superbase.system.service;

import cn.superbase.system.domain.vo.SysAppUserVo;
import cn.superbase.system.domain.bo.SysAppUserBo;
import cn.superbase.common.mybatis.core.page.TableDataInfo;
import cn.superbase.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * app用户信息Service接口
 *
 * @author YingJie Liu
 * @date 2025-10-23
 */
public interface ISysAppUserService {

    /**
     * 查询app用户信息
     *
     * @param userId 主键
     * @return app用户信息
     */
    SysAppUserVo queryById(Long userId);

    /**
     * 分页查询app用户信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return app用户信息分页列表
     */
    TableDataInfo<SysAppUserVo> queryPageList(SysAppUserBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的app用户信息列表
     *
     * @param bo 查询条件
     * @return app用户信息列表
     */
    List<SysAppUserVo> queryList(SysAppUserBo bo);

    /**
     * 新增app用户信息
     *
     * @param bo app用户信息
     * @return 是否新增成功
     */
    Boolean insertByBo(SysAppUserBo bo);

    /**
     * 修改app用户信息
     *
     * @param bo app用户信息
     * @return 是否修改成功
     */
    Boolean updateByBo(SysAppUserBo bo);

    /**
     * 校验并批量删除app用户信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
