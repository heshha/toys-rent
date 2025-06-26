package cn.edu.sdua.toysrent.vo.resp;

import cn.edu.sdua.toysrent.vo.resp.simple.OrderItemSimpleVo;
import cn.edu.sdua.toysrent.vo.resp.simple.PaymentSimpleVo;
import cn.edu.sdua.toysrent.vo.resp.simple.UserSimpleVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderRespVo {

    @Schema(description = "订单ID")
    private long orderId;

    @Schema(description = "订单日期")
    private Timestamp orderDate;

    @Schema(description = "订单状态")
    private String status;

    @Schema(description = "用户信息")
    private UserSimpleVo user;

    @Schema(description = "订单项信息")
    private List<OrderItemSimpleVo> orderItems;

    @Schema(description = "支付信息")
    private PaymentSimpleVo payment;

}
