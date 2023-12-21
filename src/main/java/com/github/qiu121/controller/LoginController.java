package com.github.qiu121.controller;

import cn.hutool.core.lang.UUID;
import com.github.qiu121.common.Result;
import com.github.qiu121.dto.LoginDTO;
import com.github.qiu121.service.IUserService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author to_Geek
 * @version 1.0
 * @since 2023/12/16
 */
@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
    @Resource
    private IUserService userService;

    /**
     * 跳转到登录页面
     *
     * @return login.html
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录
     *
     * @param loginDTO 登录信息
     * @return Result
     */

    @PostMapping("/doLogin")
    @ResponseBody
    public Result doLogin(@Validated LoginDTO loginDTO) {
        Result result = userService.doLogin(loginDTO);
        log.info("{}", loginDTO);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("\\u4e0d\\u662f\\u4e00\\u4e2a\\u5408\\u6cd5\\u7684\\u7535\\u5b50\\u90ae\\u4ef6\\u5730\\u5740");
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);

        System.out.println(java.util.UUID.randomUUID().toString());


    }
}
