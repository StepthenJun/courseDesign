package com.foodhealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_user_follow_article")
@ApiModel("点赞收藏")
public class UserFollow {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long articleId;

    private Long userId;

    private Boolean isLike;

    private Boolean isCollect;
}
