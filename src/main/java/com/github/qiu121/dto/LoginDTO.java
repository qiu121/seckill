package com.github.qiu121.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank(message = "手机号不可为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式有误")
    private String mobile;

    @Length(min = 32,max = 32)// MD5加密后的长度
    @NotBlank(message = "用户密码不可为空")
    private String password;
}
