package com.xrb.designMode.strategy;

/**
 * @author xieren8iao
 * @date 2022/3/10 8:21 下午
 */
public enum PayTypeEnum {
    WX_PAY("微信支付",1),
    STORED_PAY("储值支付",2);


    private final String name;

    private final Integer value;

    PayTypeEnum(String name,Integer value){
        this.name=name;
        this.value=value;
    }

    public static PayTypeEnum valueOf(Integer value){
        PayTypeEnum[] values = PayTypeEnum.values();
        for (PayTypeEnum payTypeEnum : values) {
            if (payTypeEnum.value.equals(value)){
                return payTypeEnum;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}