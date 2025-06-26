package cn.edu.sdua.toysrent.controller;

import cn.edu.sdua.toysrent.common.R;
import cn.edu.sdua.toysrent.entity.Order;
import cn.edu.sdua.toysrent.entity.User;
import cn.edu.sdua.toysrent.vo.req.UserUpdateInfoVo;
import cn.edu.sdua.toysrent.vo.resp.OrderRespVo;
import cn.edu.sdua.toysrent.service.OrderService;
import cn.edu.sdua.toysrent.service.UserService;
import cn.edu.sdua.toysrent.utils.ConvertUtil;
import cn.edu.sdua.toysrent.utils.LoginUserHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "用户接口", description = "用户接口")
public class UserController {

    private final UserService  userService;

    private final OrderService orderService;

    // 添加用户
//    @PostMapping("/user")
//    @Operation(summary = "添加用户", description = "添加用户")
//    public R addUser(@RequestBody @Valid UserAddVo vo) {
//
//
//        // vo转为do
//        User user = new User();
//        BeanUtils.copyProperties(vo, user);
//
//        userService.addUser(user);
//        return R.ok();
//    }

    // 获取所有用户
//    @GetMapping("/users")
//    @Operation(summary = "获取所有用户", description = "获取所有用户")
//    public R getAllUsers() {
//        List<User> users = userService.getAllUsers();
//
//        // do转为vo
//        List<UserRespVo> vos = users.stream().map(user -> {
//            UserRespVo vo = new UserRespVo();
//            BeanUtils.copyProperties(user, vo);
//            return vo;
//        }).collect(Collectors.toList());
//
//        return R.ok(vos);
//    }

    // 分页获取用户
//    @GetMapping("/users/page")
//    @Parameters({
//            @Parameter(name = "page", description = "页码", required = true),
//            @Parameter(name = "size", description = "每页数量", required = true)
//    })
//    @Operation(summary = "分页获取用户", description = "分页获取用户")
//    public R getUsersByPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
//                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<User> userPage = userService.getUsersByPage(pageable);
//
//        Page<UserRespVo> voPage = userPage.map(user -> {
//            UserRespVo vo = new UserRespVo();
//            BeanUtils.copyProperties(user, vo);
//            return vo;
//        });
//        return R.ok(voPage);
//    }

    // 根据用户ID获取用户
//    @GetMapping("/user/{id}")
//    @Operation(summary = "根据用户ID获取用户")
//    public R getUserById(@PathVariable("id") long id) {
//
//        User user = userService.getUserById(id);
//
//        // do转为vo
//        UserRespVo vo = new UserRespVo();
//        BeanUtils.copyProperties(user, vo);
//
//        return R.ok(vo);
//    }

    // 根据用户名获取用户
//    @GetMapping("/user/username/{username}")
//    @Operation(summary = "根据用户名获取用户")
//    public R getUserByUsername(@PathVariable("username") String username) {
//
//        User user = userService.getUserByUsername(username);
//
//        // vo转为do
//        UserRespVo vo = new UserRespVo();
//        BeanUtils.copyProperties(user, vo);
//
//        return R.ok(vo);
//    }

    // 根据用户名模糊获取用户
//    @GetMapping("/user/username/like/{username}")
//    @Operation(summary = "根据用户名模糊分页获取用户")
//    public R getUserByUsernameLike(@PathVariable("username") String username,
//                                   @RequestParam(value = "page", defaultValue = "0") int page,
//                                   @RequestParam(value = "size", defaultValue = "10") int size) {
//        Page<User> users = userService.getUserByUsernameLike(username, PageRequest.of(page, size));
//        if (users.isEmpty()){
//            throw new BusinessException(ExceptionCodeEnum.USER_NOT_FOUND);
//        }
//        Page<Object> vos = users.map(user -> {
//            UserRespVo vo = new UserRespVo();
//            BeanUtils.copyProperties(user, vo);
//            return vo;
//        });
//        return R.ok(vos);
//    }

    // 根据用户等级获取用户
//    @GetMapping("/users/level/{level}")
//    @Operation(summary = "根据用户等级分页获取用户")
//    public R getUserByLevel(@PathVariable("level") String level,
//                            @RequestParam(value = "page", defaultValue = "0") int page,
//                            @RequestParam(value = "size", defaultValue = "10") int size) {
//
//        Page<User> users = userService.getUserByLevel(level, PageRequest.of(page, size));
//        if (users.isEmpty()){
//            throw new BusinessException(ExceptionCodeEnum.USER_NOT_FOUND);
//        }
//
//        // do转为vo
//        Page<Object> vos = users.map(user -> {
//            UserRespVo vo = new UserRespVo();
//            BeanUtils.copyProperties(user, vo);
//            return vo;
//        });
//
//        return R.ok(vos);
//    }

    // 查询用户订单
    @GetMapping("/user/orders")
    @Operation(summary = "分页查询用户订单")
    public R getOrdersByUserId(
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        Long id = LoginUserHolder.getLoginUser();
        Page<Order> orders = orderService.getOrdersByUserId(id,  PageRequest.of(page, size));
        Page<OrderRespVo> vos = orders.map(ConvertUtil::convertOrderToVo);
        return R.ok(vos);
    }

    // 修改电话或地址
    @PutMapping("/user")
    @Operation(summary = "更新用户电话和地址信息")
    public R updateUser(@RequestBody @Valid UserUpdateInfoVo vo) {

        // vo转为do
        User user = new User();
        BeanUtils.copyProperties(vo, user);

        userService.updateUser(user);

        return R.ok();
    }

    // 充值
    @PutMapping("/user/recharge")
    @Operation(summary = "充值")
    public R recharge(@RequestParam double amount) {
        Long id = LoginUserHolder.getLoginUser();
        User user = userService.getUserById(id);
        user.setBalance(user.getBalance() + amount);
        userService.updateUser(user);
        return R.ok();
    }

    // 删除用户
//    @DeleteMapping("/user/{id}")
//    @Operation(summary = "删除用户")
//    public R deleteUser(@PathVariable("id") long id) {
//
//        userService.deleteUser(id);
//        return R.ok();
//    }

    // 获得登录用户信息
    @GetMapping("/info")
    @Operation(summary = "获取登录用户信息")
    public R getLoginInfo() {
        // 获取登录用户
        Long userId = LoginUserHolder.getLoginUser();
        LoginUserHolder.clear();
        User user = userService.getUserById(userId);
        return R.ok(user);
    }

}
