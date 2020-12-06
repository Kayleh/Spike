package com.kayleh.exception;

import com.kayleh.result.CodeMsg;
import com.kayleh.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常处理器
 *
 * @Author: Kayleh
 * @Date: 2020/12/6 14:16
 */
@ControllerAdvice//它是增强的Controller，能够实现全局异常处理和全局数据绑定
@ResponseBody//它能够实现对所有异常的接受，而在方法中，对不同的异常进行处理
public class GlobalExceptionHandler
{
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e)
    {
        if (e instanceof GlobalException)
        {
            GlobalException ge = (GlobalException) e;
            return Result.error(ge.getCm());
        } else if (e instanceof BindException)
        {
            //获取错误列表，拿取其中的第一个
            BindException exception = (BindException) e;
            List<ObjectError> allErrors = exception.getAllErrors();
            ObjectError error = allErrors.get(0);
            String message = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));
        } else
        {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
