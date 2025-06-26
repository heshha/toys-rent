package cn.edu.sdua.toysrent.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    @Schema(description = "用户ID")
    private Long  userId;
    @Schema(description = "用户名")
    private String name;
}
