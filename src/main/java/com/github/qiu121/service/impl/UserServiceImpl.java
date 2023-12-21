package com.github.qiu121.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qiu121.common.Result;
import com.github.qiu121.common.ResultEnum;
import com.github.qiu121.dto.LoginDTO;
import com.github.qiu121.entity.User;
import com.github.qiu121.mapper.UserMapper;
import com.github.qiu121.service.IUserService;
import com.github.qiu121.util.MD5Util;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author to_Geek
 * @version 1.0
 * @since 2023/12/16
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     * @param loginDTO 登录信息
     * @return Result
     */

    @Override
    public Result doLogin(LoginDTO loginDTO) {
        String mobile = loginDTO.getMobile();
        String password = loginDTO.getPassword();

        // 使用Validator工具进行参数校验----------------------------
        // if (!StringUtils.hasLength(mobile) || !StringUtils.hasText(password)) {
        //     return Result.error(ResultEnum.LOGIN_ERROR);
        // }
        // if (!ValidatorUtil.isMobile(mobile)) {
        //     return Result.error(ResultEnum.MOBILE_ERROR);
        // }

        // 根据手机号(主键)查询用户
        User user = userMapper.selectById(mobile);
        // System.out.println("user = " + user);
        if (user == null) {
            return Result.error(ResultEnum.LOGIN_ERROR);
        }
        // 判断登录密码
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            return Result.error(ResultEnum.LOGIN_ERROR);
        }
        // 更新用户信息
        user.setLastLoginDate(LocalDateTime.now());     // 最后登录时间
        user.setLoginCount(user.getLoginCount() + 1);   // 登录次数
        userMapper.updateById(user);
        StpUtil.login(user.getId());
        return Result.success(StpUtil.getTokenValue());

    }
}
