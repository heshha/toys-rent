package cn.edu.sdua.toysrent.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Data
@ToString(exclude = {"orders"})
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private long userId;

  @Column(name = "username", unique = true)
  private String username;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "ENUM('普通','白银','黄金','钻石','管理员') default '普通'")
  private UserLevel userLevel;

  @Column(name = "password_hash")
  private String password;

  @Column(name = "balance")
  private double balance;

  @Column(name = "phone")
  private String phone;

  @Column(name = "address")
  private String address;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private java.util.List<Order> orders;

  public enum UserLevel {
    普通,
    白银,
    黄金,
    钻石,
    管理员
  }

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
