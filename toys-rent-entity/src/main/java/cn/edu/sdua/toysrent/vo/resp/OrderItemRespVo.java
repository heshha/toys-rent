package cn.edu.sdua.toysrent.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrderItemRespVo {

    @Schema(description = "订单项ID")
    private long itemId;

    @Schema(description = "玩具ID")
    private long toyId;

    @Schema(description = "玩具名称")
    private String toyName;

    @Schema(description = "玩具图片")
    private long quantity;

    @Schema(description = "玩具价格")
    private double price;

    @Schema(description = "玩具租借开始日期")
    private java.sql.Date rentalStart;

    @Schema(description = "玩具租借结束日期")
    private java.sql.Date rentalEnd;




}
