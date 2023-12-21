package com.github.qiu121.exception;

import com.github.qiu121.common.Result;
import com.github.qiu121.common.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author to_Geek
 * @version 1.0
 * @date 2023/12/16
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public Result handleException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        // 多个校验条件触发，只获取第一个的message
        String msg = bindingResult.getAllErrors().get(0).getDefaultMessage();
        return new Result(ResultEnum.BIND_ERROR.getCode(), msg, null);
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {

        String message = ResultEnum.ERROR.getMessage();
        log.info("异常类型： {}", e.getMessage());
        e.printStackTrace();
        // return new Result(ResultEnum.ERROR.getCode(), message, null);
        return new Result(ResultEnum.ERROR.getCode(), e.getMessage(), null);
    }
}
