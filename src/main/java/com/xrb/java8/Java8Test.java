package com.xrb.java8;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.*;
import com.vdurmont.emoji.EmojiManager;
import com.xrb.common.HttpClientUtil;
import com.xrb.enumTest.MemberBuriedPointEnum;
import com.xrb.rabbitmqdemo.model.JsonResult;
import com.xrb.rabbitmqdemo.model.Student;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.channels.SelectionKey;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xieren8iao
 * @date 2021/8/3 9:12 上午
 */
@Slf4j
public class Java8Test {

    /**
     * Function接口函数复合
     * andThen
     * compose
     */
    @Test
    public void test1() {
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline
                = addHeader.andThen(Letter::checkSpelling)
                .andThen(Letter::addFooter);
        System.out.println(transformationPipeline.apply("labda牛牛"));
    }

    /**
     * 避免自动装箱
     */
    @Test
    public void test2() {
        IntPredicate intPredicate = x -> x > 0;
        System.out.println(intPredicate.test(3));

        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3);
        List<Integer> collect = integers.stream().map(a -> a + 1).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     *
     */
    @Test
    public void test3() {
        TimeInterval timeInterval = new TimeInterval();
        FileReader fileReader = new FileReader("/Users/xierenbiao/Desktop/zengliang.sql");
        System.out.println(fileReader.readString());
        System.out.println(timeInterval.intervalRestart());
    }

    /**
     * 无限流
     */
    @Test
    public void InfiniteStream() {
        /**
         * 生成斐波那契数列
         */
        Stream.iterate(new int[]{0, 1},
                t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

        /**
         * 生成随机数
         */
        Stream.generate(Math::random)
                .limit(5)

                .forEach(System.out::println);
    }

    @Test
    public void test5() {
        TestData data = new TestData();
        data.setValueFour("1,2,3");
        ArrayList<TestData> homePageChildInfoResDTOList = Lists.newArrayList(data);
        List<String> idsCollect = homePageChildInfoResDTOList
                .stream()
                .flatMap(a -> Arrays.stream(a.getValueFour().split(","))).collect(Collectors.toList());
        System.out.println(idsCollect);
    }

    @Test
    public void test6() {
        Student student = new Student();
        student.setAge(1);
        student.setId(1L);
        System.out.println(student);
        String studentJson = JSONObject.toJSONString(student);
        System.out.println(studentJson);
        JSONObject jsonObject = JSONObject.parseObject(studentJson);
        System.out.println(jsonObject);
        Student studentTrans = JSON.toJavaObject(jsonObject, Student.class);
        System.out.println(studentTrans);

    }

    @Test
    public void testEmoji() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            boolean b = EmojiManager.containsEmoji("😭");
        }
        System.out.println(System.currentTimeMillis() - start);
//        System.out.println(EmojiManager.isEmoji("😁"));
//        String s = EmojiFilterUtil.filterEmoji("聚光科技😁");
//        System.out.println(s);
//        System.out.println(EmojiFilterUtil.containsEmoji("聚光科技😁"));
    }

    @Test
    public void testGuavaSets() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3, 4);
        HashSet<Integer> set2 = Sets.newHashSet(3, 4);
        Sets.SetView<Integer> difference = Sets.difference(set1, set2);
        System.out.println(difference);
    }

    @Test
    public void leetcode1() throws UnsupportedEncodingException {
        String text = "束氏芦荟保湿%+凝胶300ml 92%(A)";
        int dataLength = text.length();
        int increment = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (increment < dataLength) {
            char charecterAt = text.charAt(increment);
            if (charecterAt == '%') {
                stringBuilder.append("<percent>");
            } else {
                stringBuilder.append(charecterAt);
            }
            increment++;
        }
//        String s = text.replaceAll("%(?![0-9a-fA-F]{2})", "<percent>");
//        String gbk = URLDecoder.decode(URLDecoder.decode(s, "GBK"), "UTF-8");
//        System.out.println(gbk);
//        System.out.println(gbk.replaceAll("<percent>","%"));
        System.out.println(stringBuilder.toString());

    }

    @Test
    public void testMd5() {
        int yesterdayVisitUv = 5;
        int theDayBeforeYesterdayVisitUv = 10;
        BigDecimal visitDayPercent = BigDecimal.ZERO;
        if (yesterdayVisitUv > theDayBeforeYesterdayVisitUv) {
            if (theDayBeforeYesterdayVisitUv > 0) {
                visitDayPercent = new BigDecimal(yesterdayVisitUv * 100).subtract(new BigDecimal(theDayBeforeYesterdayVisitUv * 100)).divide(new BigDecimal(theDayBeforeYesterdayVisitUv), 2, BigDecimal.ROUND_HALF_UP);
            } else {
                visitDayPercent = new BigDecimal("100");
            }
        } else {
            if (yesterdayVisitUv > 0) {
                visitDayPercent = new BigDecimal(theDayBeforeYesterdayVisitUv * 100).subtract(new BigDecimal(yesterdayVisitUv * 100)).divide(new BigDecimal(theDayBeforeYesterdayVisitUv), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("-1"));
            } else {
                visitDayPercent = new BigDecimal("-100");
            }
        }
        System.out.println(visitDayPercent);
    }

    /**
     * ①对 request参数值进行对称加密,再进行base64编码 对称加密,使用的算法:
     * Desede/CBC/ Pkcs5padding,iv向量位8字节的16进制0 密钥为 appsecret(base64编码)
     * ②所有请求参数(除sign外)按照字母先后顺序排列
     * 例如:将 access_ token,app_key, method, timestamp,v排序为 access_token, app_key, method, timestamp, V
     * ③把所有参数名和参数值进行拼装
     * yl tul: access_tokenxxxapp_keyxxxmethodxxxxxxtimestampxxxxxxvx
     * ④使用 rsa-sha1算法进行签名后,再进行base64编码
     *
     * @return
     */
    @Test
    public void testEncrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        String text = "requestJson";   //要加密的字符串
        String key = "appsecret"; //私钥   AES固定格式为128/192/256 bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。
        String iv = "00000000";//初始化向量参数，AES 为16bytes. DES 为8bytes.
        //
        byte[] appSecret = Base64.encodeBase64(key.getBytes(StandardCharsets.UTF_8));

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec keySpec = new SecretKeySpec(appSecret, "DES");

        SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(appSecret));
        Cipher cipher = Cipher.getInstance("/DESede/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

        System.out.println(new String(encrypted));
    }


    public static void az(String paramString1, String paramString2) {

        try {
// Create an array to hold the key
            byte[] encryptKey = "This is a test DESede key".getBytes();

// Create a DESede key spec from the key
            DESedeKeySpec spec = new DESedeKeySpec(encryptKey);

// Get the secret key factor for generating DESede keys
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(
                    "DESede");

// Generate a DESede SecretKey object
            SecretKey theKey = keyFactory.generateSecret(spec);

// Create a DESede Cipher
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

// Create an initialization vector (necessary for CBC mode)

            IvParameterSpec IvParameters = new IvParameterSpec(
                    new byte[]{12, 34, 56, 78, 90, 87, 65, 43});

// Initialize the cipher and put it into encrypt mode
            cipher.init(Cipher.ENCRYPT_MODE, theKey, IvParameters);

            byte[] plaintext =
                    "This is a sentence that has been encrypted".getBytes();

// Encrypt the data
            byte[] encrypted = cipher.doFinal(plaintext);

// Write the data out to a file
            FileOutputStream out = new FileOutputStream("encrypted.dat");
            out.write(encrypted);
            out.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void test11() {
        String url = "http://fp-saas.oss-cn-shenzhen.aliyuncs.com/%E4%B9%90%E8%B4%AD%E5%B2%9B%E4%BB%93%E5%82%A8%E4%BC%9A%E5%91%98%E4%B8%AD%E5%BF%83/%E5%95%86%E5%93%81/6973222960671_1.png_1634885892119?Expires=1950245892&OSSAccessKeyId=LTAI4FecJyeGQiB6TcLSkjtv&Signature=l2xTHYvl6ixi%2F8fAYX9tleCtxeU%3D";
        String s = changeOssUrlUtil(url, "100146");
        System.out.println(s);
        int lastIndexOf = s.lastIndexOf(".");
        String substring = s.substring(lastIndexOf);
        String[] splitStr = substring.split("_");
        if (splitStr.length == 2) {
            String contentType = splitStr[0];
            String timestamp = splitStr[1];
            String s1 = s.substring(0, lastIndexOf) + "_" + timestamp + contentType;
            System.out.println(s1);
        }
    }

    @Test
    public void test12() {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Thread thread : allStackTraces.keySet()) {
            
        }
    }
    private static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            System.out.println("MD5(" + sourceStr + ",32) = " + result);
            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
    /**
     * 获取token
     *
     * @return
     */
    public static String getRequestEncrypt(Map queryParamMap) {
        //TODO http://172.18.20.87:88/getToken
        //TODO 测试地址
        Map<String, String> queryMap = new HashMap<>();
        String s = JSONObject.toJSONString(queryParamMap);
        queryMap.put("string", s);
        String postStr = HttpClientUtil.doPost("http://218.56.157.10:88/DESede", queryMap);
        log.info("一卡通获取request接口返回值:{}", postStr);
//        if (ObjectUtil.isNotEmpty(postStr)) {
//            JSONObject jsonObject = JSON.parseObject(postStr);
//            String code = jsonObject.getString("code");
//        }
        return postStr;
    }

    @Test
    public void getBetweenMonth() {
        String validDateStr = "2021-10-13 00:00:00";
        String invalidDateStr = "2022-01-27 00:00:00";
        DateTime validDate = DateUtil.parseDateTime(validDateStr);
        DateTime invalidDate = DateUtil.parseDateTime(invalidDateStr);

        int validDateMonth = DateUtil.month(validDate);
        System.out.println(DateUtil.betweenDay(validDate, invalidDate, false));
    }

    /**
     * 替换前缀照片域名地址
     *
     * @return
     */
    public static String changeOssUrlUtil(String imgUrl, String companyId) {
        if ("100245".equals(companyId)) {
            if (ObjectUtil.isNotEmpty(imgUrl) && imgUrl.contains("http://fp-saas.oss-cn-shenzhen.aliyuncs.com")) {
                imgUrl = imgUrl.replace("http://fp-saas.oss-cn-shenzhen.aliyuncs.com", "https://oss.xyjpos.com");
                imgUrl = imgUrl.split("Expires")[0].substring(0, imgUrl.split("Expires")[0].length() - 1);
            }
            return imgUrl;
        }
        if (ObjectUtil.isNotEmpty(imgUrl)) {
            //阿里云OSS照片地址替换操作
            if (imgUrl.contains("http://fp-saas.oss-cn-shenzhen.aliyuncs.com")) {
                imgUrl = imgUrl.replace("http://fp-saas.oss-cn-shenzhen.aliyuncs.com", "https://loonyou.yun8848.com");
                imgUrl = imgUrl.split("Expires")[0].substring(0, imgUrl.split("Expires")[0].length() - 1);
            }
            return imgUrl;
        }
        return null;
    }

    //获取某段时间内的所有日期
    public static List<Date> findDates(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
        return dateList;
    }
}