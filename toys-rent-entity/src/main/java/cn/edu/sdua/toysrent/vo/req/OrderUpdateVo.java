package cn.edu.sdua.toysrent.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class OrderUpdateVo {
    @Schema(description = "订单ID")
    private long id;

    @Schema(description = "订单项")
    private List<OrderItemVo> orderItems;
}
