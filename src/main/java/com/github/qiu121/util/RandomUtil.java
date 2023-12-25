package com.github.qiu121.util;

import java.util.Random;

/**
 * 生成随机数
 *
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/21
 */
public class RandomUtil {
    /**
     * 生成随机手机号
     *
     * @return 手机号
     */
    public static Long randomMobile() {
        // ^1[3-9]\d{9}$
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append("1");
        sb.append(random.nextInt(7) + 3);
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }
        return Long.parseLong(sb.toString());
    }

    /**
     * 生成随机盐
     *
     * @param length 盐的长度
     * @return 盐
     */
    public static String randomSalt(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
