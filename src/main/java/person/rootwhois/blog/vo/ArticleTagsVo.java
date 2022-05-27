package person.rootwhois.blog.vo;

import com.baomidou.mybatisplus.annotation.*;
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
@ApiModel(value = "ArticleTagsVo", description = "")
public class ArticleTagsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    Integer id;

    @ApiModelProperty("文章id")
    Integer articleId;

    @ApiModelProperty("标签id")
    Integer tagId;

}
