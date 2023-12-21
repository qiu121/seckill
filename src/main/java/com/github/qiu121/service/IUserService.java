package com.github.qiu121.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qiu121.common.Result;
import com.github.qiu121.dto.LoginDTO;
import com.github.qiu121.entity.User;

/**
* @author to_Geek
* @since 2023/12/16
* @version 1.0
*/
public interface IUserService extends IService<User> {
    /**
     * 登录
     * @param loginDTO 登录信息
     * @return  Result
     */
    Result doLogin(LoginDTO loginDTO);
}
