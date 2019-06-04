package com.adtech.rts.model.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 分页参数传递
 */
@Data
public class Page<T> {

    //每页多少条
    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    public void setPageNum(Integer pageNum) {
        if(StringUtils.isEmpty(pageNum)) pageNum=0;
        this.pageNum = pageNum;
    }

    //页码
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageNum;

    //总条数
    @ApiModelProperty(value = "总条数", required = true)
    private Long totalCount;

    @ApiModelProperty(value = "数据", required = true)
    private List<T> list;

}