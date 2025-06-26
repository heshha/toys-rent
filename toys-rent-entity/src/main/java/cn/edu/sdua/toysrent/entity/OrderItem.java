package cn.edu.sdua.toysrent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = {"order", "toy"})
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long itemId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "rental_start")
    private java.sql.Date rentalStart;

    @Column(name = "rental_end")
    private java.sql.Date rentalEnd;

    @ManyToOne
    @JoinColumn(name = "toy_id")
//    @JsonIgnore
    private Toy toy;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
}
