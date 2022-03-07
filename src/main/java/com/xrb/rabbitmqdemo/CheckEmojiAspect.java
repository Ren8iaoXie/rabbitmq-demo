package com.xrb.rabbitmqdemo;

import cn.hutool.core.util.ObjectUtil;
import com.vdurmont.emoji.EmojiManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 校验emoji表情切面类
 *
 * @author xieren8iao
 * @date 2020-11-23 20:44
 */
@Slf4j
@Aspect
@Component
public class CheckEmojiAspect {


//    @Around("execution(* com.xrb.rabbitmqdemo..*Controller.*(..)) && @annotation(nrs)")
    @Around("execution(* com.xrb.rabbitmqdemo..*Controller.*(..)) && within(com.xrb.rabbitmqdemo..*))")
//    public Object around(ProceedingJoinPoint pjp, CheckEmojiFlag nrs) {
    public Object around(ProceedingJoinPoint pjp) {
        try {
            Object[] args = pjp.getArgs();
            if (ObjectUtil.isNotEmpty(args)) {
                Object arg = args[0];
                Class<?> requestClass = arg.getClass();
                Field[] requestClassFields = requestClass.getDeclaredFields();
                for (Field declaredField : requestClassFields) {
                    boolean isStringClass = declaredField.getType().equals(String.class);
                    if (isStringClass) {
                        declaredField.setAccessible(true);
                        String value = (String) declaredField.get(arg);
                        if (ObjectUtil.isNotEmpty(value) && EmojiManager.containsEmoji(value)) {
                            return "失败";
                        }
                        System.out.println(value);
                    }
                    System.out.println(isStringClass);
                }
            }

            Object proceed = pjp.proceed();
            return proceed;
        } catch (Throwable e) {
        }
        return null;
    }
}