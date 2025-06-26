package cn.edu.sdua.toysrent.service;

import cn.edu.sdua.toysrent.vo.req.LoginVo;
import cn.edu.sdua.toysrent.vo.resp.CaptchaVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo vo);
}
