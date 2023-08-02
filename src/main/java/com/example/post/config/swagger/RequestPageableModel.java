package com.example.post.config.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;

@Getter
@ApiModel
public class RequestPageableModel {

    @ApiModelProperty(value = "페이지 번호(0..N)")
    private Integer page;

    @ApiModelProperty(value = "페이지 크기 (Default:10)")
    private Integer size;

    @ApiModelProperty(value = "정렬 [양식 : 컬럼명,ASC(or DESC) ]")
    private List<String> sort;
}
