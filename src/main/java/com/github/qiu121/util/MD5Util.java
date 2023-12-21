package com.github.qiu121.util;

import cn.hutool.crypto.SecureUtil;
import org.springframework.stereotype.Component;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/16
 */
@Component
public class MD5Util {

    public static String md5(String str) {
        return SecureUtil.md5(str);
    }

    /**
     * 前端加密使用的盐
     */
    private static final String salt = "1a2b3c4d";

    /**
     * 第一次加密
     *
     * @param inputPass
     * @return 第一次加密后的密码
     */
    public static String inputPassToFromPass(String inputPass) {

        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次加密
     *
     * @param formPass
     * @param salt     盐，来自数据库
     * @return 第二次加密后的密码
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 两次加密
     *
     * @param inputPass 前端传入的明文密码
     * @param salt      盐，来自数据库
     * @return 两次加密后的密码
     */

    public static String inputPassToDBPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        return formPassToDBPass(fromPass, salt);
    }

    public static void main(String[] args) {
        // d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(formPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9", salt));
        System.out.println(inputPassToDBPass("123456", salt));
    }
}
