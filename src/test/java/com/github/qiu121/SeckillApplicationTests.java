package com.github.qiu121;

import cn.hutool.crypto.SecureUtil;
import com.github.qiu121.entity.User;
import com.github.qiu121.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.qiu121.util.MD5Util.inputPassToDBPass;
import static com.github.qiu121.util.RandomUtil.randomMobile;
import static com.github.qiu121.util.RandomUtil.randomSalt;
import static com.github.qiu121.util.TokenUtil.getToken;
import static com.github.qiu121.util.TokenUtil.saveTokenToFile;

@SpringBootTest
@Slf4j
class SeckillApplicationTests {
    @Resource
    private IUserService userService;

    @Test
    void contextLoads() {
        String string = SecureUtil.md5("1212121");
        System.out.println(string);
    }

    /**
     * 生成用户
     */
    @Test
    void generateUser() {
        ArrayList<User> userList = new ArrayList<>();
        int count = 100;

        for (int i = 0; i < count; i++) {

            User user = new User();
            if (i == 0) {
                user.setNickname("admin");
            }
            user.setId(randomMobile());
            user.setSalt(randomSalt(6));
            user.setPassword(inputPassToDBPass("123456", user.getSalt()));
            user.setRegisterDate(LocalDateTime.now());
            userList.add(user);
            log.info("创建用户：{}", user.getId());
        }
        // userList.forEach(System.out::println);
        userService.saveBatch(userList);
    }

    /**
     * 批量获取用户Token。
     * Java模拟发送POST请求，批量获取登录用户的Token,请求地址为，http://localhost:8080/login/doLogin,
     * 参数类型为param，有两个参数，mobile、password(固定为"d3b1294a61a07da9b49b6e22b2cbd7f9",明文密码为"123456")
     * mobile的数据来自数据表user中获取的id字段的值，登录成功返回JSON格式数据，其中包含data属性中的数据即为Token
     * 对应响应数据格式如下：
     * ```json
     * {
     * "code": 200,
     * "message": "SUCCESS",
     * "data": "4c0eca9a3cb94394b2d9e2fa7808e2bf"
     * }
     * ```
     * 将获取的Token存储到一个 token.txt中，每行一个,
     */
    @Test
    void generateToken() {

        List<Long> mobileList = userService.list()
                .stream()
                .map(User::getId)
                .toList();
        for (Long mobile : mobileList) {
            String token = getToken(mobile);
            if (token != null) {
                saveTokenToFile(token);
            } else {
                log.error("{}", mobile);
            }
        }

    }


}

