package cn.edu.sdua.toysrent.utils;

import cn.edu.sdua.toysrent.vo.resp.LoginUser;
import org.springframework.stereotype.Component;

@Component
public class LoginUserHolder {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setLoginUser(long userId) {
        threadLocal.set(userId);
    }

    public static Long getLoginUser() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
