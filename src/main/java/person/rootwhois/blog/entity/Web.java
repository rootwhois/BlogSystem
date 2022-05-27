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
@TableName("web")
@ApiModel(value = "Web对象", description = "")
public class Web implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("网站id")
    @TableId(value = "web_id", type = IdType.AUTO)
    Integer webId;

    @ApiModelProperty("网站管理者id")
    @TableField("web_user_id")
    Integer webUserId;

    @ApiModelProperty("网站是否开放 1开放 0不开放")
    @TableField("web_is_open")
    Boolean webIsOpen;

    @ApiModelProperty("网站网址")
    @TableField("web_domain")
    String webDomain;

    @ApiModelProperty("网站名字")
    @TableField("web_name")
    String webName;

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
