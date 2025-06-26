package cn.edu.sdua.toysrent.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAddVo {

    @NotBlank(message = "姓名不能为空")
    @Schema(description = "姓名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;

    @Size(min = 11, max = 11, message = "手机号长度为11位")
    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号")
    private String phone;

    @NotBlank(message = "地址不能为空")
    @Schema(description = "地址")
    private String address;


}
