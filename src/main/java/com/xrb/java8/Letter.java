package com.xrb.java8;

/**
 * @author xieren8iao
 * @date 2021/8/3 9:11 上午
 */
public class Letter{
    public static String addHeader(String text){
        return "From xrb: \n " + text;
    }
    public static String addFooter(String text){
        return text + " \n Kind regards";
    }
    public static String checkSpelling(String text){
        return text.replaceAll("labda", "lambda");
    }
}