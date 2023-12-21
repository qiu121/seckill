package com.github.qiu121.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public static Result success() {
        return new Result(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMessage(), null);
    }

    public static Result success(Object object) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), object);
    }

    public static Result error(ResultEnum resultEnum) {
        return new Result(ResultEnum.ERROR.getCode(), resultEnum.getMessage(), null);
    }

    public static Result error(ResultEnum resultEnum, Object object) {
        return new Result(ResultEnum.ERROR.getCode(), resultEnum.getMessage(), object);
    }
}
