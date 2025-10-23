package org.dromara.system.controller.system;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.system.domain.vo.SysAppUserVo;
import org.dromara.system.domain.bo.SysAppUserBo;
import org.dromara.system.service.ISysAppUserService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * app用户信息
 *
 * @author YingJie Liu
 * @date 2025-10-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/appUser")
public class SysAppUserController extends BaseController {

    private final ISysAppUserService sysAppUserService;

    /**
     * 查询app用户信息列表
     */
    @SaCheckPermission("system:appUser:list")
    @GetMapping("/list")
    public TableDataInfo<SysAppUserVo> list(SysAppUserBo bo, PageQuery pageQuery) {
        return sysAppUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出app用户信息列表
     */
    @SaCheckPermission("system:appUser:export")
    @Log(title = "app用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysAppUserBo bo, HttpServletResponse response) {
        List<SysAppUserVo> list = sysAppUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "app用户信息", SysAppUserVo.class, response);
    }

    /**
     * 获取app用户信息详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:appUser:query")
    @GetMapping("/{userId}")
    public R<SysAppUserVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(sysAppUserService.queryById(userId));
    }

    /**
     * 新增app用户信息
     */
    @SaCheckPermission("system:appUser:add")
    @Log(title = "app用户信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysAppUserBo bo) {
        return toAjax(sysAppUserService.insertByBo(bo));
    }

    /**
     * 修改app用户信息
     */
    @SaCheckPermission("system:appUser:edit")
    @Log(title = "app用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysAppUserBo bo) {
        return toAjax(sysAppUserService.updateByBo(bo));
    }

    /**
     * 删除app用户信息
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:appUser:remove")
    @Log(title = "app用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(sysAppUserService.deleteWithValidByIds(List.of(userIds), true));
    }
}
