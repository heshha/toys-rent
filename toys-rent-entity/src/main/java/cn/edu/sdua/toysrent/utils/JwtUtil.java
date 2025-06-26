package cn.edu.sdua.toysrent.utils;

import cn.edu.sdua.toysrent.entity.User;
import cn.edu.sdua.toysrent.exception.BusinessException;
import cn.edu.sdua.toysrent.exception.ExceptionCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "2323111909jzx2323111920sjx2323111926wxj";
    private static SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 生成token
    public static String createToken(Long userId, User.UserLevel userLevel){
        System.out.println("userid:"+userId+",userlevel:"+userLevel);
        // 设置jwt的body
        String jwt = Jwts.builder().
                signWith(secretKey).
                claim("userId", userId).
                claim("userLevel", userLevel).
                expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)).
                compact();
        return jwt;
    }

    //  解析token
    public static Claims parseToken(String token){
        if (token == null){
            throw new BusinessException(ExceptionCodeEnum.LOGIN_AUTH);
        }
        Jws<Claims> jwt = null;
        try {
            jwt = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            //  获取jwt的body
            Claims claims = jwt.getPayload();
//            System.out.println(claims);
            return claims;
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ExceptionCodeEnum.LOGIN_TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new BusinessException(ExceptionCodeEnum.LOGIN_AUTH);
        }


    }
}
