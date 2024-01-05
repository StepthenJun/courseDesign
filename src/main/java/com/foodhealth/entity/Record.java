package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_record")
public class Record {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long skimNumber;
    private Long score;
}
