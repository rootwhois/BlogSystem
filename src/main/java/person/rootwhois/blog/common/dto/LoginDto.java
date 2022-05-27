package person.rootwhois.blog.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: 陈广生
 * @Date: 2022/01/11/12:37 AM
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    String password;

}
