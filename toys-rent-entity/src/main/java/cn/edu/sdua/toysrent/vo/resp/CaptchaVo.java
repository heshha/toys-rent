package cn.edu.sdua.toysrent.vo.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wf.captcha.base.Captcha;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.io.OutputStream;

@Schema(description = "验证码返回对象")
@AllArgsConstructor
@Data
public class CaptchaVo{

    @Schema(description = "验证码图片的base64编码")
    private String base64;

    @Schema(description = "验证码图片的key")
    private String key;



}
