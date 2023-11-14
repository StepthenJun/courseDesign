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
@TableName("tb_community")
@ApiModel("社区交流文章属性的实体类")
public class Community {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty("用户id")
  private Long userId;

  @ApiModelProperty("社区主题的id")
  private Long communityTopicId;

  @ApiModelProperty("标题")
  private String title;

  @ApiModelProperty("文章内容")
  private String content;

  @ApiModelProperty("发表的图片（如果需要的话）")
  private String image;
}
