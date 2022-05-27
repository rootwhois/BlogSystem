package person.rootwhois.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈广生
 * @since 2022-03-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("history")
@ApiModel(value = "History对象", description = "")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("操作人员")
    @TableField("operator")
    private String operator;

    @ApiModelProperty("操作地址")
    @TableField("operate_method")
    private String operateMethod;

    @ApiModelProperty("操作类型")
    @TableField("operate_type")
    private String operateType;

    @ApiModelProperty("请求参数")
    @TableField("params")
    private String params;

    @ApiModelProperty("返回状态")
    @TableField("operate_result_status")
    private String operateResultStatus;

    @ApiModelProperty("返回结果")
    @TableField("operate_result")
    private String operateResult;

    @ApiModelProperty("操作ip地址")
    @TableField("operate_ip")
    private String operateIp;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE, update = "now()")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否已经删除")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty("版本号")
    @TableField("version")
    @Version
    private Integer version;


}