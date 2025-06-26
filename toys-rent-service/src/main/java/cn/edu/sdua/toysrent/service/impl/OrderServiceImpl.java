package cn.edu.sdua.toysrent.service.impl;

import cn.edu.sdua.toysrent.entity.*;
import cn.edu.sdua.toysrent.vo.req.OrderItemVo;
import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import cn.edu.sdua.toysrent.repository.*;
import cn.edu.sdua.toysrent.service.OrderService;
import cn.edu.sdua.toysrent.service.PaymentService;
import cn.edu.sdua.toysrent.vo.req.OrderUpdateVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ToyRepository toyRepository;

    private final OrderItemRepository orderItemRepository;

    private final PaymentRepository paymentRepository;

    private final PaymentService paymentService;

    // 添加订单
    @Override
    @Transactional
    public Long addOrder(Long userId, List<OrderItemVo> orderItems) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ExceptionCodeEnum.USER_NOT_FOUND)
        );
        // 创建订单
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(java.sql.Timestamp.from(java.time.Instant.now()));
        order.setStatus("待支付");
        // 设置订单过期时间
        order.setExpireTime(new Timestamp(
                System.currentTimeMillis() + 30 * 60 * 1000
        ));
        orderRepository.save(order);
        // 计算订单价格
        double totalPrice = 0;
        for (OrderItemVo orderItemVo : orderItems) {
            Toy toy = toyRepository.findById(orderItemVo.getToyId()).orElseThrow(
                    () -> new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND)
            );
            if (toy.getStock() < orderItemVo.getQuantity()) {
                throw new BusinessException(ExceptionCodeEnum.TOY_OUT_OF_STOCK);
            }
            // 添加订单项
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(orderItemVo, orderItem);
            orderItem.setOrder(order);
            orderItem.setToy(toy);
            orderItem.setPrice(toy.getDailyRate() * orderItemVo.getQuantity());
            orderItemRepository.save(orderItem);
            // 计算订单价格
            totalPrice += orderItem.getPrice();
            // 减库存
            toy.setStock(toy.getStock() - orderItemVo.getQuantity());
            toyRepository.save(toy);
        }
        //  创建支付信息
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentStatus("待支付");
        payment.setAmount(totalPrice);
        paymentService.addPayment(payment);
        order.setPayment(payment);
        orderRepository.save(order);
        return order.getOrderId();
    }

    // 获取所有订单
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 分页获取订单
    @Override
    public Page<Order> getOrdersByPage(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    // 根据ID获取订单
    @Override
    public Order getOrderById(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(ExceptionCodeEnum.ORDER_NOT_FOUND)
        );
        return order;
    }

    // 根据用户ID获取订单
    @Override
    public Page<Order> getOrdersByUserId(long userId,  Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ExceptionCodeEnum.USER_NOT_FOUND)
        );
        return orderRepository.findByUser(user,  pageable);
    }

    // 根据状态获取订单
    @Override
    public Page<Order> getOrdersByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatus(status,  pageable);
    }

    //  更新订单

    @Override
    public void updateOrder(Order order) {
        Order oldOrder = orderRepository.findById(order.getOrderId()).orElseThrow(
                () -> new BusinessException(ExceptionCodeEnum.ORDER_NOT_FOUND)
        );
        BeanUtils.copyProperties(order, oldOrder);
        orderRepository.save(oldOrder);
    }

    @Override
    public void updateOrder(OrderUpdateVo vo) {
        Order order = orderRepository.findById(vo.getId()).orElseThrow(
                () -> new BusinessException(ExceptionCodeEnum.ORDER_NOT_FOUND)
        );
        // 先恢复库存
        for (OrderItem orderItem : order.getOrderItems()) {
            Toy toy = orderItem.getToy();
            toy.setStock(toy.getStock() + orderItem.getQuantity());
            toyRepository.save(toy);
        }
        // 再减少库存并且重新计算价格
        double totalPrice = 0;
        for (OrderItemVo orderItemVo : vo.getOrderItems()) {
            Toy toy = toyRepository.findById(orderItemVo.getToyId()).orElseThrow(
                    () -> new BusinessException(ExceptionCodeEnum.TOY_NOT_FOUND)
            );
            if (toy.getStock() < orderItemVo.getQuantity()) {
                throw new BusinessException(ExceptionCodeEnum.TOY_OUT_OF_STOCK);
            }
            // 添加订单项
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(orderItemVo, orderItem);
            orderItem.setOrder(order);
            orderItem.setToy(toy);
            orderItem.setPrice(toy.getDailyRate() * orderItemVo.getQuantity());
            orderItemRepository.save(orderItem);
            // 减库存
            toy.setStock(toy.getStock() - orderItemVo.getQuantity());
            toyRepository.save(toy);
            totalPrice += orderItem.getPrice();

        }
        // 创建支付信息
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(totalPrice);
        paymentRepository.save(payment);
        order.setPayment(payment);
        orderRepository.save(order);

    }

    // 归还玩具
    @Override
    public void returnToy(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(ExceptionCodeEnum.ORDER_NOT_FOUND)
        );
        // 修改订单状态
        order.setStatus("已归还");
        orderRepository.save(order);
        // 恢复库存
        for (OrderItem orderItem : order.getOrderItems()) {
            Toy toy = orderItem.getToy();
            toy.setStock(toy.getStock() + orderItem.getQuantity());
            toyRepository.save(toy);
        }

    }

    //  删除订单
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(ExceptionCodeEnum.ORDER_NOT_FOUND)
        );
        System.out.println(order);
        // 删除支付记录
        Payment payment = order.getPayment();
        if (payment != null){
            // 解除支付记录和订单的关联
            payment.setOrder(null);
            order.setPayment(null);
            // 删除支付记录
            paymentRepository.deleteById(payment.getPaymentId());
        }
        // 删除订单项
        orderItemRepository.batchDeleteByOrderId(orderId);
        // 删除订单
        orderRepository.deleteById(orderId);
    }

    // 将订单转换为VO
//    @Override
//    public OrderRespVo convertOrderToVo(Order order) {
//        OrderRespVo vo = new OrderRespVo();
//        // 基础订单信息转换
//        BeanUtils.copyProperties(order, vo);
//        // 用户信息转换
//        UserSimpleVo userVo = new UserSimpleVo();
//        BeanUtils.copyProperties(order.getUser(), userVo);
//        vo.setUser(userVo);
//        // 支付信息转换
//        PaymentSimpleVo paymentVo = new PaymentSimpleVo();
//        if (order.getPayment() != null){
//            BeanUtils.copyProperties(order.getPayment(), paymentVo);
//        }
//        vo.setPayment(paymentVo);
//        // 订单项信息转换
//        List<OrderItemSimpleVo> orderItemVos = order.getOrderItems().stream().map(orderItem -> {
//            OrderItemSimpleVo orderItemVo = new OrderItemSimpleVo();
//            BeanUtils.copyProperties(orderItem, orderItemVo);
//            orderItemVo.setToyId(orderItem.getToy().getToyId());
//            orderItemVo.setToyName(orderItem.getToy().getToyName());
//            return orderItemVo;
//        }).collect(Collectors.toList());
//        vo.setOrderItems(orderItemVos);
//
//        return vo;
//    }
}
