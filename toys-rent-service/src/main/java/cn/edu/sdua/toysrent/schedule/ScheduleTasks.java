package cn.edu.sdua.toysrent.schedule;

import cn.edu.sdua.toysrent.entity.Order;
import cn.edu.sdua.toysrent.entity.Payment;
import cn.edu.sdua.toysrent.repository.OrderRepository;
import cn.edu.sdua.toysrent.repository.PaymentRepository;
import cn.edu.sdua.toysrent.repository.ToyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleTasks {


    private final ToyRepository toyRepository;

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;


    // 每日0点检查订单是否逾期
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkLeaseStatus() {
        Date currentDate = Date.valueOf(LocalDate.now());
        // 查询已到截止日期且状态是已签收的订单
        List<Order> orders = orderRepository.findByStatusAndRentalEndBefore("已签收", currentDate);
        // 更新状态
        for (Order order : orders) {
            order.setStatus("已到期");
            orderRepository.save(order);
        }
    }

    // 每分钟检查订单是否超时
    @Scheduled(cron = "0 * * * * ?")
    public void checkOrderTimeout() {
        Date currentDate = Date.valueOf(LocalDate.now());
        // 获取所有未完成订单
        List<Order> orders = orderRepository.findByStatusAndExpireTimeIsNotNull("待支付");
        // 遍历订单，检查是否超时
        for (Order order : orders) {
            if (order.getExpireTime().before(currentDate)) {
                // 关闭订单
                order.setStatus("已关闭");
                orderRepository.save(order);
                // 恢复库存
                order.getOrderItems().forEach(item -> {
                    item.getToy().setStock(item.getToy().getStock() + item.getQuantity());
                    toyRepository.save(item.getToy());
                });
                // 取消关联支付
                Payment payment = order.getPayment();
                if (payment != null) {
                    payment.setPaymentStatus("已取消");
                    paymentRepository.save(payment);
                }
            }
        }
    }
}
