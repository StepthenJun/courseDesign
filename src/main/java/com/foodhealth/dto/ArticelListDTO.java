package com.foodhealth.dto;

import com.foodhealth.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("文章的实体类")
public class ArticelListDTO {
    @ApiModelProperty("推荐文章")
    private List<Article> recomendAticle;
    @ApiModelProperty("喜欢的文章")
    private List<Article> followArticle;
}
