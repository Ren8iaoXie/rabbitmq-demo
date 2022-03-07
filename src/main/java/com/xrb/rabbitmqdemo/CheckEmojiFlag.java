package com.xrb.rabbitmqdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 校验emoji表情注解
 * @author xieren8iao
 * @date 2020-11-23 20:26
 */
@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface CheckEmojiFlag {
}