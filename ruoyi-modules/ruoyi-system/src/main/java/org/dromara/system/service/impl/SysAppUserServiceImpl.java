package org.dromara.system.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.SysAppUserBo;
import org.dromara.system.domain.vo.SysAppUserVo;
import org.dromara.system.domain.SysAppUser;
import org.dromara.system.mapper.SysAppUserMapper;
import org.dromara.system.service.ISysAppUserService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * app用户信息Service业务层处理
 *
 * @author YingJie Liu
 * @date 2025-10-23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysAppUserServiceImpl implements ISysAppUserService {

    private final SysAppUserMapper baseMapper;

    /**
     * 查询app用户信息
     *
     * @param userId 主键
     * @return app用户信息
     */
    @Override
    public SysAppUserVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询app用户信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return app用户信息分页列表
     */
    @Override
    public TableDataInfo<SysAppUserVo> queryPageList(SysAppUserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysAppUser> lqw = buildQueryWrapper(bo);
        Page<SysAppUserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的app用户信息列表
     *
     * @param bo 查询条件
     * @return app用户信息列表
     */
    @Override
    public List<SysAppUserVo> queryList(SysAppUserBo bo) {
        LambdaQueryWrapper<SysAppUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysAppUser> buildQueryWrapper(SysAppUserBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysAppUser> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysAppUser::getUserId);
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), SysAppUser::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getEmail()), SysAppUser::getEmail, bo.getEmail());
        lqw.eq(StringUtils.isNotBlank(bo.getPhonenumber()), SysAppUser::getPhonenumber, bo.getPhonenumber());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysAppUser::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增app用户信息
     *
     * @param bo app用户信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysAppUserBo bo) {
        SysAppUser add = MapstructUtils.convert(bo, SysAppUser.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改app用户信息
     *
     * @param bo app用户信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysAppUserBo bo) {
        SysAppUser update = MapstructUtils.convert(bo, SysAppUser.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysAppUser entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除app用户信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
