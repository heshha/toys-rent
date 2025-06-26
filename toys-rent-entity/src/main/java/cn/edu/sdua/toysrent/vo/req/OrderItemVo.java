package cn.edu.sdua.toysrent.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class OrderItemVo {

    @NotNull(message = "玩具ID不能为空")
    @Schema(description = "玩具ID")
    private long toyId;

    @NotNull(message = "数量不能为空")
    @Schema(description = "数量")
    private long quantity;

    @NotNull(message = "租借开始时间不能为空")
    @Schema(description = "租借开始时间")
    private Date rentalStart;

    @NotNull(message = "租借结束时间不能为空")
    @Schema(description = "租借结束时间")
    private Date rentalEnd;
}
