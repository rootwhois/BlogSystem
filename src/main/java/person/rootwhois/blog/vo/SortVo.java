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
@ApiModel(value = "SortVo", description = "")
public class SortVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
    Integer sortId;

    @ApiModelProperty("分类名称")
    String sortName;

    @ApiModelProperty("文章数")
    String articleCount;
}
