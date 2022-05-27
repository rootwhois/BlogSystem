package person.rootwhois.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ArticleVo", description = "")
public class ArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章id")
    Integer articleId;

    @ApiModelProperty("文章所属用户id")
    Integer articleUserId;

    @ApiModelProperty("文章标题")
    String articleTitle;

    @ApiModelProperty("文章描述")
    String articleDescription;

    @ApiModelProperty("文章内容")
    String articleContent;

    @ApiModelProperty("是否置顶0不置顶1置顶")
    Integer articleTop;

    @ApiModelProperty("文章阅读数")
    Integer articleViewsCount;

    @ApiModelProperty("文章点赞数")
    Integer articleLikeCount;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;

    @ApiModelProperty("文章分类列表")
    List<Integer> articleSorts;

    @ApiModelProperty("文章标签列表")
    List<Integer> articleTags;

    @ApiModelProperty("评论数")
    Long commentCount;

}
