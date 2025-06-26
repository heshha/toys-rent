package cn.edu.sdua.toysrent.vo.resp.simple;

import lombok.Data;

@Data
public class PaymentSimpleVo {

    private long paymentId;

    private double amount;

    private String paymentMethod;

    private String paymentStatus;

    private java.sql.Timestamp createdAt;
}
