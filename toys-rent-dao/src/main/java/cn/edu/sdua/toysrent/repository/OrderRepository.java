package cn.edu.sdua.toysrent.repository;

import cn.edu.sdua.toysrent.entity.Order;
import cn.edu.sdua.toysrent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);

    @Query("SELECT o FROM Order o JOIN o.orderItems oi " +
            "WHERE o.status = :status " +
            "AND oi.rentalEnd <= :currentDate")
    List<Order> findByStatusAndRentalEndBefore(
            @Param("status") String status,
            @Param("currentDate") Date currentDate
    );

    Page<Order> findByUser(User user,  Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.expireTime IS NOT NULL")
    List<Order> findByStatusAndExpireTimeIsNotNull(String status);

    Page<Order> findByStatus(String status, Pageable pageable);
}
