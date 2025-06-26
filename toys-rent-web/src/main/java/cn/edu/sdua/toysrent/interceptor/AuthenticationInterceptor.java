package cn.edu.sdua.toysrent.interceptor;

import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import cn.edu.sdua.toysrent.utils.JwtUtil;
import cn.edu.sdua.toysrent.utils.LoginUserHolder;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    // 需要管理员权限的接口路径
    private static final List<String> ADMIN_PATHS = List.of(
            "/admin/**"
    );

    // 登录拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        // token解析
        //  获取token
        String token = request.getHeader("login-token");
        log.info("token:{}", token);
        // 解析token
        Claims claimsJws = JwtUtil.parseToken(token);
        // 获取用户id和权限
        Long userId = claimsJws.get("userId", Long.class);
        String userLevel = claimsJws.get("userLevel", String.class);
        log.info("用户id:{},用户权限:{}", userId, userLevel);
        // 将用户信息存入ThreadLocal
        LoginUserHolder.setLoginUser(userId);


        // 访问管理员接口
        String path = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (antPathMatcher.match("/admin/**", path)) {
            // 判断用户权限
            if (!userLevel.equals("管理员")) {
                throw new BusinessException(ExceptionCodeEnum.ADMIN_REQUIRED);
            }
        }


        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserHolder.clear();
    }
}
