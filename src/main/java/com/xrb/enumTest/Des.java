package com.xrb.enumTest;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

public class Des {
    public static String encryptKey = "6eGicG6U";

    public static void main(String[] args) {
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("a",123);
        resultMap.put("b",2);
        resultMap.put("c",1233);
        String s1 = JSON.toJSONString(resultMap);
        String result = encryptDES(s1, encryptKey);
        System.out.println(result);

        String s = decryptDES("6rOSq8Dz2O+OnWaJq6DgaCk6u7sJdyw2Ka13qDWTpho=", encryptKey);
        System.out.println(s);
    }

    /**
     * 加密 这个iv偏移量是数组！！
     */
    private static byte[] iv = {0,0,0,0,0,0,0,0};

    public static String encryptDES(String encryptString, String encryptKey) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            System.out.println("key: "+encryptKey.getBytes().toString());
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            return Base64.encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解密
     */
    public static String decryptDES(String decryptString, String decryptKey) {
        try {
            byte[] byteMi = Base64.decode(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);

            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

