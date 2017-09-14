package com.yydscm.Service;

import com.google.common.base.Strings;
import com.yydscm.Enum.Const;
import com.yydscm.Utils.RedisUtil;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

/**
 * Created by chenzhaopeng on 2017/6/20.
 */
@Component
public class TokenManager {
    @Value("${tokentimeout}")
    private long tokentimeout;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 生产token
     *
     * @param user
     * @return
     */
    public String createToken(Map<String, Object> user) {
        SecretKey key = generalKey();
        long nowMillis = System.currentTimeMillis();
        long timeoutMillis = tokentimeout * 1000;
        String token = Jwts.builder()
                .setSubject((String) user.get("user_name"))
                .claim("userid", user.get("uuid"))
                .setIssuedAt(new Date(nowMillis))
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(new Date(nowMillis + timeoutMillis))
                .compact();
        return token;
    }


    /**
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        SecretKey key = generalKey();
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {

        }
        return claims;
    }

    /**
     * 验证token是否有效
     */
    public boolean isVaild(String token) {
        SecretKey key = generalKey();
        Claims claims = null;
        String uuid = "";
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            uuid = claims.get("userid") + "";
            if (!Strings.isNullOrEmpty(uuid) && redisUtil.exists(uuid)) {
                return true;
            }
            return false;
        } catch (SignatureException | ExpiredJwtException e) {
            redisUtil.remove(uuid);
            return false;
        }
    }


    /**
     * 生成key
     *
     * @return
     */
    public SecretKey generalKey() {
        String stringKey = Const.JWT_SECRET.getValue();
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


}
