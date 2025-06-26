package cn.edu.sdua.toysrent.service.impl;

import cn.edu.sdua.toysrent.entity.User;
import cn.edu.sdua.toysrent.vo.req.LoginVo;
import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import cn.edu.sdua.toysrent.repository.UserRepository;
import cn.edu.sdua.toysrent.service.LoginService;
import cn.edu.sdua.toysrent.utils.JwtUtil;
import cn.edu.sdua.toysrent.vo.resp.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final StringRedisTemplate redisTemplate;

    private final UserRepository userRepository;

    //  生成验证码
    @Override
    public CaptchaVo getCaptcha() {

        // 创建验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        // 获取验证码字符和密钥
        String code = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 缓存验证码
        redisTemplate.opsForValue().set(key, code, 30, TimeUnit.MINUTES);

        return new CaptchaVo(specCaptcha.toBase64(), key);
    }

    // 登录
    @Override
    public String login(LoginVo vo) {


        String code = redisTemplate.opsForValue().get(vo.getCaptchaKey());
        // 验证码过期
        if (code == null) {
            throw new BusinessException(ExceptionCodeEnum.LOGIN_CAPTCHA_CODE_EXPIRED);
        }
        //  验证码错误
        if (!code.equals(vo.getCaptcha().toLowerCase())) {
            throw new BusinessException(ExceptionCodeEnum.LOGIN_CAPTCHA_CODE_ERROR);
        }
        // 获取登录用户
        String username = vo.getUsername();
        String password = vo.getPassword();
//        System.out.println(username+password);
        User user = userRepository.findByUsernameAndPassword(username, password);
        // 判断登录人身份
//        if ("admin".equals(vo.getIdentity())){
//            Admin admin = adminRepository.findByNameAndPassword(username, password);
//            if (admin == null) {
//                throw new BusinessException(ExceptionCodeEnum.LOGIN_USERNAME_PASSWORD_ERROR);
//            }
//            user.setUserId(admin.getAdminId());
//            user.setUsername(admin.getName());
//        } else if ("user".equals(vo.getIdentity())){
//            user = userRepository.findByUsernameAndPassword(username, password);
//        } else {
//            throw new BusinessException(ExceptionCodeEnum.LOGIN_USERNAME_PASSWORD_ERROR);
//        }
        // 用户名或密码错误
        if (user == null) {
            throw new BusinessException(ExceptionCodeEnum.LOGIN_USERNAME_PASSWORD_ERROR);
        }

        // 生成token
        String token = "login-token: " + JwtUtil.createToken(user.getUserId(), user.getUserLevel());

        //  返回用户信息和token
//        AdminRespVo adminRespVo = new AdminRespVo();
//        BeanUtils.copyProperties(admin, adminRespVo);
//        AdminLoginVo userLoginVo = new AdminLoginVo(adminRespVo, token);


        return token;
    }
}
