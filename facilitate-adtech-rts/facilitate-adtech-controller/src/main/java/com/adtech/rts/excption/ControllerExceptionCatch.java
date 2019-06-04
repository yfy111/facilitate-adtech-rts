package com.adtech.rts.excption;

import com.adtech.rts.model.enums.CodeEnum;
import com.adtech.rts.model.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 抓取异常
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionCatch {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception ex) {
        ex.printStackTrace();
        return new Result(CodeEnum.FAIL_BUSINESS);
    }
}
