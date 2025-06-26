package cn.edu.sdua.toysrent.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "用户登录响应对象")
public class AdminLoginVo {

    @Schema(description = "用户信息")
    private AdminRespVo admin;

    @Schema(description = "token")
    private String token;


}
