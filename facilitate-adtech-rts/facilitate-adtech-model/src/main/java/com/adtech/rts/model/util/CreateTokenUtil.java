package com.adtech.rts.model.util;


import cn.hutool.crypto.SecureUtil;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 创建token
 */
public class CreateTokenUtil {


    private static final int INDEX = 5;

    /**
     * 创建token
     *
     * @param val
     * @return
     */
    public static String createToken(String val) {
        return encodeSalt(sha1encode(val), INDEX);
    }

    /**
     * 解密token
     *
     * @param val
     * @return
     */
    public static String decodeToken(String val) {
        String s = null;
        try {
            s = decodeBase64(decodeSalt(encodeSalt(val, INDEX), INDEX));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * sha1加密
     *
     * @param str
     * @return
     */
    public static String sha1encode(String str) {
        return SecureUtil.sha1(str);
    }


    /**
     * base64加密
     *
     * @param str
     * @return
     */
    public static String encodeBase64String(String str) {
        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * base64解密
     *
     * @param str
     * @return
     */
    public static String decodeBase64(String str) throws UnsupportedEncodingException {
        return new String(Base64.decodeBase64(str.getBytes()), "utf-8");
    }

    /**
     * 对存入内容base64撒盐
     *
     * @param text
     * @return
     */
    public static String encodeContent(String text) {
        return encodeSalt(encodeBase64String(text), INDEX);
    }

    /**
     * 对存入内容解密
     *
     * @param text
     * @return
     */
    public static String decodeContent(String text) {
        String t = null;
        try {
            t =  decodeBase64(decodeSalt(text, INDEX));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return t;
    }

    /***
     * 传入密文
     * 然后撒盐加密
     * @param str 加密后的报文
     * @return
     */
    public static String encodeSalt(String str, int index) {
        int i = str.charAt(str.length() - 1) + index;
        char c = (char) (i);
        return c+str.substring(0, str.length() - 1) ;
    }


    /**
     * 撒盐取出
     *
     * @param token
     * @param index
     * @return
     */
    public static String decodeSalt(String token, int index) {
        int i = token.charAt(0);
        char c = (char) (i - index);
        return token.substring(1)+c ;
    }

//    public static void main(String[] args) {
//        String s = "123456";
//        System.err.println(s);
//        System.err.println(createToken(s));
//        String text = encodeContent(s);
//        System.err.println(text);
//        System.err.println(decodeContent(text));
//    }
}
