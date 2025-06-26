package cn.edu.sdua.toysrent.controller;

import cn.edu.sdua.toysrent.common.R;
import cn.edu.sdua.toysrent.entity.User;
import cn.edu.sdua.toysrent.vo.req.LoginVo;
import cn.edu.sdua.toysrent.vo.req.UserAddVo;
import cn.edu.sdua.toysrent.vo.resp.CaptchaVo;
import cn.edu.sdua.toysrent.service.LoginService;
import cn.edu.sdua.toysrent.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
@Tag(name = "登录接口", description = "登录接口")
public class LoginController {

    private final LoginService loginService;

    private final UserService userService;


    // 获取验证码
    @GetMapping("/captcha")
    @Operation(summary = "获取图形验证码")
    public R<CaptchaVo> getCaptcha(HttpServletResponse  response) {
        // 设置禁用缓存，防止验证码不刷新
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        CaptchaVo captcha = loginService.getCaptcha();
        return R.ok(captcha);
    }

    // 登录
    @PostMapping
    @Operation(summary = "登录")
    public R login(@RequestBody @Valid LoginVo vo) {
//        System.out.println("登录");
        String token = loginService.login(vo);

        return R.ok(token);
    }

    // 注册
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public R register(@RequestBody @Valid UserAddVo vo) {
//        System.out.println("注册");
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        userService.addUser(user);

        return R.ok();
    }
//
//    // 获取登录用户信息
//    @GetMapping("/info")
//    @Operation(summary = "获取登录用户信息")
//    public R getLoginInfo() {
//        // 获取登录用户
//        LoginUser loginUser = LoginUserHolder.getLoginUser();
//        log.info("登录用户:"+loginUser);
//        LoginUserHolder.clear();
//        return R.ok(loginUser);
//    }
}
