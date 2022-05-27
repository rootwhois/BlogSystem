package person.rootwhois.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

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
@ApiModel(value = "AdminVo", description = "")
public class AdminVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    Integer userId;

    @ApiModelProperty("用户名")
    String userName;

    @ApiModelProperty("昵称")
    String userNickname;

    @ApiModelProperty("头像地址")
    String userAvatar;

    @ApiModelProperty("邮箱")
    String userEmail;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate userBirthday;


}
