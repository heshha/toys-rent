package cn.edu.sdua.toysrent.vo.resp;

import cn.edu.sdua.toysrent.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRespVo {

    @Schema(description = "用户ID")
    private long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户等级")
    private User.UserLevel userLevel;

    @Schema(description = "用户余额")
    private double balance;

    @Schema(description = "用户手机号")
    private String phone;

    @Schema(description = "用户地址")
    private String address;

    @Schema(description = "用户创建日期")
    private java.sql.Timestamp createdAt;

    @Schema(description = "用户更新日期")
    private java.sql.Timestamp updatedAt;
}
