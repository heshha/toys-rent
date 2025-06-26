package cn.edu.sdua.toysrent.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderAddVo {

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private long userId;

    @Schema(description = "订单项")
    private List<OrderItemVo> orderItems;
}
