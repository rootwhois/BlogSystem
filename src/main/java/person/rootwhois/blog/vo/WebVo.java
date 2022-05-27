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
@ApiModel(value = "WebVo", description = "")
public class WebVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("网站id")
    Integer webId;

    @ApiModelProperty("网站管理者id")
    Integer webUserId;

    @ApiModelProperty("网站是否开放 1开放 0不开放")
    Boolean webIsOpen;

    @ApiModelProperty("网站网址")
    String webDomain;

    @ApiModelProperty("网站名字")
    String webName;

}
