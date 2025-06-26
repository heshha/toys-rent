package cn.edu.sdua.toysrent.utils;

import cn.edu.sdua.toysrent.entity.Order;
import cn.edu.sdua.toysrent.vo.resp.OrderRespVo;
import cn.edu.sdua.toysrent.vo.resp.simple.OrderItemSimpleVo;
import cn.edu.sdua.toysrent.vo.resp.simple.PaymentSimpleVo;
import cn.edu.sdua.toysrent.vo.resp.simple.UserSimpleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtil {

    public static OrderRespVo convertOrderToVo(Order order) {
        OrderRespVo vo = new OrderRespVo();
        // 基础订单信息转换
        BeanUtils.copyProperties(order, vo);
        // 用户信息转换
        UserSimpleVo userVo = new UserSimpleVo();
        BeanUtils.copyProperties(order.getUser(), userVo);
        vo.setUser(userVo);
        // 支付信息转换
        PaymentSimpleVo paymentVo = new PaymentSimpleVo();
        if (order.getPayment() != null){
            BeanUtils.copyProperties(order.getPayment(), paymentVo);
        }
        vo.setPayment(paymentVo);
        // 订单项信息转换
        List<OrderItemSimpleVo> orderItemVos = order.getOrderItems().stream().map(orderItem -> {
            OrderItemSimpleVo orderItemVo = new OrderItemSimpleVo();
            BeanUtils.copyProperties(orderItem, orderItemVo);
            orderItemVo.setToyId(orderItem.getToy().getToyId());
            orderItemVo.setToyName(orderItem.getToy().getToyName());
            return orderItemVo;
        }).collect(Collectors.toList());
        vo.setOrderItems(orderItemVos);

        return vo;
    }
}
