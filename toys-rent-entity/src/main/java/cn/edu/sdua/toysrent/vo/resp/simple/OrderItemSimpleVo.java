package cn.edu.sdua.toysrent.vo.resp.simple;

import lombok.Data;

@Data
public class OrderItemSimpleVo {

    private long itemId;

    private long toyId;

    private String toyName;

    private long quantity;

    private double price;

    private java.sql.Date rentalStart;

    private java.sql.Date rentalEnd;


}
