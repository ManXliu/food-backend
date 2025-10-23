package cn.superbase.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.superbase.common.core.validate.AddGroup;
import cn.superbase.common.core.validate.EditGroup;
import cn.superbase.common.mybatis.core.domain.BaseEntity;
import cn.superbase.system.domain.SysAppUser;

/**
 * app用户信息业务对象 sys_app_user
 *
 * @author YingJie Liu
 * @date 2025-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysAppUser.class, reverseConvertGenerate = false)
public class SysAppUserBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = {EditGroup.class})
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空", groups = {AddGroup.class, EditGroup.class})
    private String userName;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String nickName;

    /**
     * 用户类型（sys_user系统用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private Long avatar;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;

    /**
     * 备注
     */
    private String remark;


}
