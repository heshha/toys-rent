package cn.edu.sdua.toysrent.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ToyRespVo {

    @Schema(description = "玩具ID")
    private long toyId;

    @Schema(description = "玩具名称")
    private String toyName;

    @Schema(description = "玩具分类ID")
    private long categoryId;

    @Schema(description = "玩具描述")
    private String description;

    @Schema(description = "玩具日租价")
    private double dailyRate;

    @Schema(description = "玩具库存")
    private long stock;

    @Schema(description = "玩具条件")
    private String toyCondition;

    @Schema(description = "玩具创建时间")
    private java.sql.Timestamp createdAt;
}
