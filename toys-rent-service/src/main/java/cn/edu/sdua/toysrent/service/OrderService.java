package cn.edu.sdua.toysrent.service;

import cn.edu.sdua.toysrent.entity.Order;
import cn.edu.sdua.toysrent.vo.req.OrderItemVo;
import cn.edu.sdua.toysrent.vo.req.OrderUpdateVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Long addOrder(Long userId, List<OrderItemVo> orderItems);

    List<Order> getAllOrders();

    Order getOrderById(long orderId);

    void updateOrder(OrderUpdateVo vo);

    void updateOrder(Order order);

    void deleteOrder(long orderId);

    Page<Order> getOrdersByPage(Pageable pageable);

//    OrderRespVo convertOrderToVo(Order order);

    Page<Order> getOrdersByUserId(long id,  Pageable pageable);

    Page<Order> getOrdersByStatus(String status, Pageable pageable);

    void returnToy(long orderId);
}
