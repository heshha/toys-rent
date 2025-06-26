package cn.edu.sdua.toysrent.vo.resp;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminRespVo {

    @Schema(description = "管理员ID")
    private long adminId;

    @Schema(description = "管理员姓名")
    private String name;

    @Schema(description = "管理员创建日期")
    private java.sql.Timestamp createdAt;

    @Schema(description = "管理员更新日期")
    private java.sql.Timestamp updatedAt;
}
