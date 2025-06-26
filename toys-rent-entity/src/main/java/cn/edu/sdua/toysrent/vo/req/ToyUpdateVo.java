package cn.edu.sdua.toysrent.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ToyUpdateVo {

    @NotNull(message = "玩具ID不能为空")
    @Schema(description = "玩具ID")
    private long toyId;

    @Schema(description = "玩具名称")
    private String toyName;

    @Schema(description = "分类ID")
    private long categoryId;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "每日租金")
    private double dailyRate;

    @Schema(description = "库存")
    private long stock;

    @Schema(description = "玩具状态:全新，九九新，九五新，九新，八新")
    private String toyCondition;

}
