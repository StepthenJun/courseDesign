package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("tb_community_type")
public class CommunityType {

    @TableId(value = "topic_id", type = IdType.AUTO)
    private Long topicId;

    private String topicType;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
