package cn.edu.sdua.toysrent.vo.req;

import cn.edu.sdua.toysrent.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateVo {

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户等级：普通、白银、黄金、钻石")
    private User.UserLevel userLevel;

    @Schema(description = "余额")
    private double balance;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "地址")
    private String address;

}
