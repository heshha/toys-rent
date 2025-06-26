package cn.edu.sdua.toysrent.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(description = "登录请求对象")
@Data
public class LoginVo {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码key不能为空")
    @Schema(description = "验证码key")
    private String captchaKey;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码")
    private String captcha;

//    @NotBlank(message = "身份不能为空")
//    @Pattern(regexp = "^(admin|user)$", message = "身份只能是admin或user")
//    @Schema(description = "身份:admin或user")
//    private String identity;
}
