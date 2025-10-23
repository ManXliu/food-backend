package cn.superbase.system.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import cn.superbase.common.excel.annotation.ExcelDictFormat;
import cn.superbase.common.excel.convert.ExcelDictConvert;
import cn.superbase.common.translation.annotation.Translation;
import cn.superbase.common.translation.constant.TransConstant;
import cn.superbase.system.domain.SysAppUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * app用户信息视图对象 sys_app_user
 *
 * @author YingJie Liu
 * @date 2025-10-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysAppUser.class)
public class SysAppUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @ExcelProperty(value = "部门ID")
    private Long deptId;

    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 用户类型（sys_user系统用户）
     */
    @ExcelProperty(value = "用户类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "s=ys_user系统用户")
    private String userType;

    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ExcelProperty(value = "手机号码")
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelProperty(value = "用户性别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String sex;

    /**
     * 头像地址
     */
    @ExcelProperty(value = "头像地址")
    private Long avatar;

    /**
     * 头像地址Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "avatar")
    private String avatarUrl;
    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ExcelProperty(value = "帐号状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_status")
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @ExcelProperty(value = "删除标志", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=代表存在,1=代表删除")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @ExcelProperty(value = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    private Date loginDate;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 租户ID
     */
    private String tenantId;


}
