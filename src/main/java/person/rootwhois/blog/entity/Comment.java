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
@TableName("comment")
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论id")
    @TableId(value = "comment_id", type = IdType.AUTO)
    @OrderBy
    Integer commentId;

    @ApiModelProperty("评论父id")
    @TableField("comment_sub_id")
    Integer commentSubId;

    @ApiModelProperty("评论的文章id")
    @TableField("comment_article_id")
    Integer commentArticleId;

    @ApiModelProperty("评论内容")
    @TableField("comment_content")
    String commentContent;

    @ApiModelProperty("评论用户的id")
    @TableField("comment_user_id")
    Integer commentUserId;

    @ApiModelProperty("评论者昵称")
    @TableField("comment_nickname")
    String commentNickname;

    @ApiModelProperty("评论者的邮箱")
    @TableField("comment_email")
    String commentEmail;

    @ApiModelProperty("评论者的IP地址")
    @TableField("comment_ip")
    String commentIp;

    @ApiModelProperty("评论的状态0审核通过1审核中2审核不通过")
    @TableField("comment_status")
    Integer commentStatus;

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
