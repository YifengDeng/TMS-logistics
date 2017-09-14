package com.yydscm.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class MD5 {
    public static String md5(String plainText) {
        String str = null;

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(plainText.getBytes());
            byte[] b = e.digest();
            StringBuffer buf = new StringBuffer("");

            for (int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            str = buf.toString();
        } catch (NoSuchAlgorithmException arg6) {
            arg6.printStackTrace();
        }

        return str;
    }

    public static String string2MD5(String inStr) {
        StringBuffer sb = new StringBuffer(32);

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            byte[] array = e.digest(inStr.getBytes("utf-8"));

            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString(array[i] & 255 | 256).toUpperCase().substring(1, 3));
            }
        } catch (Exception arg4) {
            return null;
        }

        return sb.toString();
    }

    public static String convertMD5(String inStr) {
        return convertMD5_single(convertMD5_single(inStr));
    }

    private static String convertMD5_single(String inStr) {
        char[] a = inStr.toCharArray();

        for (int s = 0; s < a.length; ++s) {
            a[s] = (char) (a[s] ^ 116);
        }

        String arg2 = new String(a);
        return arg2;
    }
}