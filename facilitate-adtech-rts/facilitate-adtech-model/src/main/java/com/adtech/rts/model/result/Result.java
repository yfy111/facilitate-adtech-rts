package com.adtech.rts.model.result;

import com.adtech.rts.model.enums.CodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "响应码", required = true)
    private int code = CodeEnum.SUCCESS.getCode();

    @ApiModelProperty(value = "响应消息", required = true)
    private String msg = "";

    @ApiModelProperty(value = "结果对象", required = true)
    private T data = null;

    public Result() {
        super();
    }

    public Result(CodeEnum codeEnum) {
        this(codeEnum.getCode(), codeEnum.getMsg());
    }

    public Result(CodeEnum codeEnum, String msg) {
        this(codeEnum.getCode(), msg);
    }

    public Result(CodeEnum codeEnum, T data) {
        this(codeEnum.getCode(), codeEnum.getMsg(), data);
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
