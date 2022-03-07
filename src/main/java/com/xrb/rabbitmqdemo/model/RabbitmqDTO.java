package com.xrb.rabbitmqdemo.model;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xieren8iao
 * @date 2021/6/25 3:49 下午
 */
@Data
@AllArgsConstructor
public class RabbitmqDTO {
    public RabbitmqDTO(){

    }
    private Long id;

    private Integer seconds;

    private Long msgId;

    private String msg;
}