package cn.edu.sdua.toysrent.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Data
@ToString(exclude = {"orderItems"})
@Table(name = "toys")
public class Toy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "toy_id")
  private long toyId;

  @Column(name = "toy_name",  unique = true)
  private String toyName;

  @Column(name = "category_id")
  private long categoryId;

  @Column(name = "description")
  private String description;

  @Column(name = "daily_rate")
  private double dailyRate;

  @Column(name = "stock")
  private long stock;

  @Column(name = "toy_condition")
  private String toyCondition;

  @Column(name = "image_url")
  private String image;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @OneToMany(mappedBy = "toy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private java.util.List<OrderItem> orderItems;

  @PrePersist
  protected void onCreate() {
    createdAt = new Timestamp(System.currentTimeMillis());
    updatedAt = createdAt;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = new Timestamp(System.currentTimeMillis());
  }

}
