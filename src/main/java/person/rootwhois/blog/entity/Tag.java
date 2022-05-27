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
@TableName("tag")
@ApiModel(value = "Tag对象", description = "")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签id")
    @TableId(value = "tag_id", type = IdType.AUTO)
    @OrderBy
    Integer tagId;

    @ApiModelProperty("标签名称")
    @TableField("tag_name")
    String tagName;

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
