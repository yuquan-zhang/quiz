package com.zhang.yong.quit.programminglearning.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhang yong created on 2019/08/08
 */
public class Sha256Util {

    private static MessageDigest md; // 定义消息摘要对象

    private static int hashIterations = 13; // 定义加密次数

    private static byte[] salt = "*#$%".getBytes(); // 定义加密盐

    static {
        try {
            // 初始化消息摘要对象和加密算法
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            md = null;
        }
    }

    /**
     * 加密指定字符串，若加密失败，则返回null
     * @param text 指定字符串 不能为null
     * @return 加密后的字符串
     */
    public static String encrypt(String text) {
        if (null == md || null == text) return null;
        byte[] digest = md.digest(text.getBytes());
        int hi = hashIterations;
        while (--hi > 0) {
            joinSalt(digest,salt); // 加盐
            digest = md.digest(digest);
        }
        return toHexString(digest);
    }

    /**
     * 加盐算法
     * @param source 源数据
     * @param salt 加密盐
     */
    private static void joinSalt(byte[] source, byte[] salt) {
        int len = Math.min(source.length,salt.length); // 获取到两个数组中最小的长度值作为循环终止值
        int start = Math.abs(source[0]) % 2; // 根据源数据第一个字节跟2求余数得到循环条件的初始值
        for (int i = start; i < len; i+=2) { // 每间隔一个字节使用加密盐替换源数据
            source[i] = salt[i];
        }
    }

    /**
     * 将字节数组转为十六进制字符串
     * @param input 字节数组
     * @return 十六进制字符串
     */
    private static String toHexString(byte[] input) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : input) {
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

    /**
     * 测试加密方法
     * @param args 无
     */
    public static void main(String[] args) {
        // 加密 hello world
        // 加密后的字符串为 adef76715df8f6f5a868ace46ed5fbc9ea80334120a2ff5dbc53861c7fa4b736
        System.out.println(encrypt("hello world"));
    }
}
