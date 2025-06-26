package cn.edu.sdua.toysrent.repository;

import cn.edu.sdua.toysrent.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Modifying
    @Query(nativeQuery = true,
        value = "DELETE FROM payments WHERE order_id = :orderId")
    void deleteByOrderIdNative(@Param("orderId") long orderId);
}
