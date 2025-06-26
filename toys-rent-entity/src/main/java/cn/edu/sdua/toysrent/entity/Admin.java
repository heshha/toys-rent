package cn.edu.sdua.toysrent.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="admin")
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "admin_id")
  private long adminId;

  @Column(name = "name",unique = true)
  private String name;

  @Column(name = "password_hash")
  private String password;

  @Column(name = "created_at")
  private java.sql.Timestamp createdAt;

  @Column(name = "updated_at")
  private java.sql.Timestamp updatedAt;


}
