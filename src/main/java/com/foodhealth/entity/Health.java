package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("tb_health")
public class Health {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private LocalDateTime createTime;
}
