package cn.edu.sdua.toysrent.controller.admin;

import cn.edu.sdua.toysrent.common.R;
import cn.edu.sdua.toysrent.entity.Order;
import cn.edu.sdua.toysrent.vo.req.OrderAddVo;
import cn.edu.sdua.toysrent.vo.resp.OrderRespVo;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import cn.edu.sdua.toysrent.service.OrderService;
import cn.edu.sdua.toysrent.service.PaymentService;
import cn.edu.sdua.toysrent.utils.ConvertUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "订单接口(管理员)", description = "订单接口(管理员)")
public class OrderControllerForAdmin {

    private final OrderService orderService;
    private final PaymentService paymentService;

    //添加订单
    @PostMapping("/order")
    @Operation(summary = "添加订单", description = "添加订单")
    public R createOrder(@RequestBody @Valid OrderAddVo vo){
        Long orderId = orderService.addOrder(vo.getUserId(), vo.getOrderItems());
        return R.ok(orderId);
    }

    //获取所有订单
//    @GetMapping("/orders")
//    public List<Order> getAllOrders(){
//        return orderService.getAllOrders();
//    }

    //分页获取订单
    @GetMapping("/orders/page")
    @Operation(summary = "分页获取订单", description = "分页获取订单")
    public R getOrdersByPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> ordersByPage = orderService.getOrdersByPage(pageable);
        Page<OrderRespVo> voPage = ordersByPage.map(ConvertUtil::convertOrderToVo);

        return R.ok(voPage);
    }

    //根据id获取订单
    @GetMapping("/order/{id}")
    @Operation(summary = "根据id获取订单", description = "根据id获取订单")
    public R getOrderById(@PathVariable("id") long id){
        Order orderById = orderService.getOrderById(id);
        OrderRespVo vo = ConvertUtil.convertOrderToVo(orderById);

        return R.ok(vo);
    }

    // 根据状态获取订单
    @GetMapping("/orders/status/{status}")
    @Operation(summary = "根据状态获取订单", description = "根据状态获取订单")
    public R getOrdersByStatus(@PathVariable("status") String status,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "0") int page){
        Page<Order> ordersByStatus = orderService.getOrdersByStatus(status,  PageRequest.of(page, size));
        Page<Object> voList = ordersByStatus.map(ConvertUtil::convertOrderToVo);

        return R.ok(voList);
    }

    //更新订单
//    @PutMapping("/order")
//    @Operation(summary = "更新订单", description = "更新订单")
//    public String updateOrder(@RequestBody Order order){
//        orderService.updateOrder(order);
//        return "ok";
//    }

    // 修改订单状态
    @PutMapping("/order/{id}/status")
    @Operation(summary = "修改订单状态", description = "修改订单状态")
    public R updateOrderStatus(@PathVariable("id") long id, @RequestParam String status){
        Order orderById = orderService.getOrderById(id);
        if (orderById == null){
            return R.error(ExceptionCodeEnum.ORDER_NOT_FOUND);
        }
        orderById.setStatus(status);
        orderService.updateOrder(orderById);
        return R.ok();
    }

    //删除订单
    @DeleteMapping("/order/{id}")
    @Operation(summary = "删除订单", description = "删除订单")
    public R deleteOrder(@PathVariable("id") long id){
        orderService.deleteOrder(id);
        return R.ok();
    }

    // 支付订单
//    @PutMapping("/order/{id}/pay")
//    @Operation(summary = "支付订单", description = "支付订单")
//    public R payOrder(@PathVariable("id") long id, @RequestParam String paymentMethod) {
//        Order orderById = orderService.getOrderById(id);
//        if (orderById == null){
//            return R.error(ExceptionCodeEnum.ORDER_NOT_FOUND);
//        } else if (orderById.getStatus().equals("已关闭")){
//            return R.error(ExceptionCodeEnum.ORDER_CLOSED);
//        }
//        paymentService.paymentSuccess(orderById.getPayment().getPaymentId(), paymentMethod);
//        return R.ok();
//    }

    // 根据订单归还玩具
//    @PutMapping("/order/{id}/return")
//    @Operation(summary = "根据订单归还玩具", description = "根据订单归还玩具")
//    public R returnOrder(@PathVariable("id") long orderId) {
//        Order orderById = orderService.getOrderById(orderId);
//        if (orderById == null){
//            return R.error(ExceptionCodeEnum.ORDER_NOT_FOUND);
//        }
//        orderService.returnToy(orderId);
//        return R.ok();
//    }


    // 转换订单为vo
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
