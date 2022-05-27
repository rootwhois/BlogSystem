package person.rootwhois.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 陈广生
 * @Date: 2022/02/22/2:36 PM
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "HistoryVo", description = "")
public class InfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("网站状态")
    boolean webIsOpen;

    @ApiModelProperty("网址")
    String webDomain;

    @ApiModelProperty("网站名")
    String webName;

    @ApiModelProperty("昵称")
    String userNickName;

    @ApiModelProperty("头像")
    String userAvatar;

    @ApiModelProperty("文章数")
    long articleCount;

    @ApiModelProperty("分类数")
    long sortCount;

    @ApiModelProperty("标签数")
    long tagCount;

    @ApiModelProperty("评论数")
    long commentCount;

}
