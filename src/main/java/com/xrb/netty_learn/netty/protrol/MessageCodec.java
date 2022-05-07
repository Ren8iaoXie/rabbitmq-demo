package com.xrb.netty_learn.netty.protrol;

import io.netty.handler.codec.ByteToMessageCodec;

/**
 * @author xieren8iao
 * @date 2022/4/17 8:14 下午
 */
//自定义协议
//组成要素
//魔数：用来在第一时间判定接收的数据是否为无效数据包
//版本号：可以支持协议的升级
//序列化算法：消息正文到底采用哪种序列化反序列化方式
//如：json、protobuf、hessian、jdk
//指令类型：是登录、注册、单聊、群聊… 跟业务相关
//请求序号：为了双工通信，提供异步能力
//正文长度
//消息正文
//public class MessageCodec extends ByteToMessageCodec<Message> {
//}