package cn.edu.sdua.toysrent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Data
@ToString(exclude = {"order"})
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long paymentId;

    @OneToOne
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    private Order order;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "amount")
    private double amount;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}
