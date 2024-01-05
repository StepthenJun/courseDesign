package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_pst")
@ApiModel("科普的实体类")
public class Pst {
    @ApiModelProperty("科普的id")
    private Long id;
    @ApiModelProperty("科普的种类名")
    private String name;
    @ApiModelProperty("科普的内容")
    private String contents;
}
