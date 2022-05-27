package person.rootwhois.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("article")
@ApiModel(value = "Article对象", description = "")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章id")
    @TableId(value = "article_id", type = IdType.AUTO)
    Integer articleId;

    @ApiModelProperty("文章所属用户id")
    @TableField("article_user_id")
    Integer articleUserId;

    @ApiModelProperty("文章标题")
    @TableField("article_title")
    String articleTitle;

    @ApiModelProperty("文章描述")
    @TableField("article_description")
    String articleDescription;

    @ApiModelProperty("文章内容")
    @TableField("article_content")
    String articleContent;

    @ApiModelProperty("文章状态0发布1草稿")
    @TableField("article_status")
    Integer articleStatus;

    @ApiModelProperty("是否置顶0不置顶1置顶")
    @TableField("article_top")
    Integer articleTop;

    @ApiModelProperty("文章阅读数")
    @TableField("article_views_count")
    Integer articleViewsCount;

    @ApiModelProperty("文章点赞数")
    @TableField("article_like_count")
    Integer articleLikeCount;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE, update = "now()")
    LocalDateTime updateTime;

    @ApiModelProperty("是否已经删除")
    @TableField("deleted")
    @TableLogic
    Integer deleted;

    @ApiModelProperty("版本号")
    @TableField("version")
    @Version
    Integer version;


}
