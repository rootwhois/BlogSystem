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
@ApiModel(value = "HistoryVo", description = "")
public class HistoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("操作人员")
    private String operator;

    @ApiModelProperty("操作地址")
    private String operateMethod;

    @ApiModelProperty("操作类型")
    private String operateType;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("返回状态")
    private String operateResultStatus;

    @ApiModelProperty("返回结果")
    private String operateResult;

    @ApiModelProperty("操作ip地址")
    private String operateIp;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;

}
