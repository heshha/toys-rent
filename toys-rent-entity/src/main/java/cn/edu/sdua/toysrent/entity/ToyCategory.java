package cn.edu.sdua.toysrent.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "toy_categories")
public class ToyCategory {

  @Id
  @Column(name = "category_id")
  private long categoryId;

  @Column(name = "category_name", unique = true)
  private String categoryName;

  @Column(name = "age_range")
  private String ageRange;

  @Column(name = "created_at")
  private java.sql.Timestamp createdAt;

  @PrePersist
  public void prePersist() {
    createdAt = new java.sql.Timestamp(System.currentTimeMillis());
  }

}
