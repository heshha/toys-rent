package cn.edu.sdua.toysrent.service.impl;

import cn.edu.sdua.toysrent.entity.Order;
import cn.edu.sdua.toysrent.entity.Payment;
import cn.edu.sdua.toysrent.entity.User;
import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.repository.OrderRepository;
import cn.edu.sdua.toysrent.repository.PaymentRepository;
import cn.edu.sdua.toysrent.repository.UserRepository;
import cn.edu.sdua.toysrent.service.PaymentService;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Override
    public void addPayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(ExceptionCodeEnum.PAYMENT_NOT_FOUND));
    }

    @Override
    public void paymentSuccess(long paymentId, String paymentMethod) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(ExceptionCodeEnum.PAYMENT_NOT_FOUND));
        // 支付状态修改
        payment.setPaymentStatus("已完成");
        payment.setPaymentMethod(paymentMethod);
        paymentRepository.save(payment);
        Order order = payment.getOrder();
        // 账户余额支付
        if (paymentMethod.equals("账户余额")){
            User user = order.getUser();
            if (user.getBalance() < payment.getAmount()){
                throw new BusinessException(ExceptionCodeEnum.INSUFFICIENT_BALANCE);
            }
            user.setBalance(user.getBalance() - payment.getAmount());
            userRepository.save(user);
        }
        //  订单状态修改
        order.setStatus("待发货");
        orderRepository.save(order);

    }


}
