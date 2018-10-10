package com.dsys.utils;


import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author dsys
 * @CreateTime 18-10-9-下午7:36
 * @Description
 **/
public class CommonUtils {
    /***
     * md5算法
     * @param salt
     * @param value
     * @return
     */
    public static String md5Salt(String salt,String value){
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder=new BASE64Encoder();
            return base64Encoder.encode(md5.digest((value+salt).getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
