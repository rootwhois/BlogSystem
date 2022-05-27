package person.rootwhois.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@ApiModel(value = "TagVo", description = "")
public class TagVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签id")
    Integer tagId;

    @ApiModelProperty("标签名称")
    String tagName;

    @ApiModelProperty("文章数")
    String articleCount;
}
