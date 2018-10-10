package com.dsys.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * @Author dsys
 * @CreateTime 18-10-8-下午7:57
 * @Description
 **/
public class JwtHelper {
    static final long expiredTime=1000*60*60*24*7;
    static final byte[] signByte="qwertyuiop1234567!@#$%^!@#!@#$dsys".getBytes();
    public static String createJwt(String account){
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        Key signKey=new SecretKeySpec(signByte,signatureAlgorithm.getJcaName());
        JwtBuilder jwtBuilder= Jwts.builder()
                .setHeaderParam("typ","JWT")
                .claim("account",account)
                .signWith(signatureAlgorithm,signKey);
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);
        Date expired=new Date(nowMillis+expiredTime);
        jwtBuilder.setExpiration(expired).setNotBefore(now);
        return jwtBuilder.compact();
    }
    public static Claims parseJwt(String token){
        Claims claims=Jwts.parser().
                setSigningKey(signByte)//验证私钥一致
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
