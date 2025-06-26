package cn.edu.sdua.toysrent.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserUpdateInfoVo {

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "地址")
    private String address;
}
