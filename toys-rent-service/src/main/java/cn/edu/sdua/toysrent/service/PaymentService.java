package cn.edu.sdua.toysrent.service;

import cn.edu.sdua.toysrent.entity.Payment;

public interface PaymentService {

    void addPayment(Payment payment);

    Payment getPaymentById(long paymentId);

    void paymentSuccess(long paymentId, String paymentMethod);
}
