package com.github.qiu121.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/21
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) throws SaTokenException {

        registry.addInterceptor(new SaInterceptor(handler -> {
            // 拦截路径，排除路径
            SaRouter.match("/**")
                    .notMatch("/login/**",
                            "/favicon.ico",
                            "/bootstrap/**",
                            "/jquery-validation/**",
                            "/js/**",
                            "/layer/**"

                    ).check(r -> {
                        // 未登录，拦截重定向到登录页面
                        if (!StpUtil.isLogin()) {
                            SaHolder.getResponse().redirect("/login/toLogin");
                        }
                    })
            ;

        })).addPathPatterns("/**");
    }
}


