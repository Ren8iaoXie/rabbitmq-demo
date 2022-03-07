package com.xrb.rabbitmqdemo.model;

/** 更新会员枚举类
 * @author xieren8iao
 * @date 2021/7/16 2:23 下午
 */
public enum UpdateMemberEnum {
    BIRTHDAY("更新生日",1),
    VIP_ADVISER("更新顾问",2);



    private  String name;
    private  int index;
    UpdateMemberEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
