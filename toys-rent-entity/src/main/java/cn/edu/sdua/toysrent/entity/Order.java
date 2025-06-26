package cn.edu.sdua.toysrent.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Data
@ToString(exclude = {"payment", "orderItems", "user"})
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private long orderId;

  @ManyToOne
  @JoinColumn(name = "user_id")
//  @JsonIgnore
  private User user;

  @Column(name = "order_date")
  private Timestamp orderDate;

  @Column(name = "status")
  private String status;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "payment_id")
//  @JsonIgnore
  private Payment payment;

  @Column(name = "expire_time")
  private Timestamp expireTime;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY,  orphanRemoval = true)
  private java.util.List<OrderItem> orderItems;

  @PrePersist
  public void prePersist() {
    if (orderDate == null) {
      orderDate = new Timestamp(System.currentTimeMillis());
    }
  }
}
