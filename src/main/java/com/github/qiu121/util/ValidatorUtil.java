package com.github.qiu121.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/16
 */
public class ValidatorUtil {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 验证手机号是否合法
     * @param mobile 手机号
     * @return true 合法，false 不合法
     */
    public static boolean isMobile(String mobile) {
        if (!StringUtils.hasLength(mobile)) {
            return false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return matcher.matches();

    }
}
