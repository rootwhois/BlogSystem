package person.rootwhois.blog.vo;

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
@ApiModel(value = "Comment对象", description = "")
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论id")
    Integer commentId;

    @ApiModelProperty("评论父id")
    Integer commentSubId;

    @ApiModelProperty("评论的文章id")
    Integer commentArticleId;

    @ApiModelProperty("评论内容")
    String commentContent;

    @ApiModelProperty("评论用户的id")
    Integer commentUserId;

    @ApiModelProperty("评论者昵称")
    String commentNickname;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;


}
