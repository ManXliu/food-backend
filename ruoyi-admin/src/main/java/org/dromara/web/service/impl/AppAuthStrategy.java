package org.dromara.web.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.SystemConstants;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.domain.model.PasswordLoginBody;
import org.dromara.common.core.enums.LoginType;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.tenant.helper.TenantHelper;
import org.dromara.system.domain.SysAppUser;
import org.dromara.system.domain.vo.SysAppUserVo;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.system.mapper.SysAppUserMapper;
import org.dromara.web.domain.vo.LoginVo;
import org.dromara.web.service.IAuthStrategy;
import org.dromara.web.service.SysLoginService;
import org.springframework.stereotype.Service;

/**
 * App认证策略
 *
 * @author YingJie Liu
 */
@Slf4j
@Service("app" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class AppAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;
    private final SysAppUserMapper sysAppUserMapper;

    @Override
    public LoginVo login(String body, SysClientVo client) {
        PasswordLoginBody loginBody = JsonUtils.parseObject(body, PasswordLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String tenantId = loginBody.getTenantId();
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();

        LoginUser loginUser = TenantHelper.dynamic(tenantId, () -> {
            SysAppUserVo user = loadUserByUsername(username);
            loginService.checkLogin(LoginType.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, user.getPassword()));
            // 此处可根据登录用户的数据不同 自行创建 loginUser
            return loginService.buildLoginAppUser(user);
        });
        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        SaLoginParameter model = new SaLoginParameter();
        model.setDeviceType(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientId());
        // 生成token
        LoginHelper.login(loginUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(client.getClientId());
        return loginVo;
    }

    private SysAppUserVo loadUserByUsername(String username) {
        SysAppUserVo user = sysAppUserMapper.selectVoOne(new LambdaQueryWrapper<SysAppUser>().eq(SysAppUser::getUserName, username));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (SystemConstants.DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        return user;
    }

}
