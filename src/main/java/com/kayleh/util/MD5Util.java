package com.kayleh.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: Kayleh
 * @Date: 2020/12/5 16:07
 */
public class MD5Util
{
    public static String md5(String src)
    {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt1 = "1a2b3c4d";

    //输入加密
    public static String inputPassToFormPass(String inputPass)
    {
        String str = "" + salt1.charAt(0) + salt1.charAt(2) + inputPass + salt1.charAt(5) + salt1.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    public static String formPassToDbPass(String formPass, String salt)
    {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB)
    {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDbPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args)
    {
//        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
    }
}
