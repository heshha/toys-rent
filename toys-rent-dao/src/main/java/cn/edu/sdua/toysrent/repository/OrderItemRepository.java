package cn.edu.sdua.toysrent.repository;

import cn.edu.sdua.toysrent.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Modifying
    @Query(nativeQuery = true,
        value = "DELETE FROM order_items WHERE order_id = :orderId LIMIT 1000")
    void batchDeleteByOrderId(@Param("orderId") long orderId);
}
