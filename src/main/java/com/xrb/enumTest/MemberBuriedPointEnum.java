package com.xrb.enumTest;

/**
 * @author xieren8iao
 * @date 2021/11/29 9:25 上午
 */
public enum MemberBuriedPointEnum {
    VISIT("访问小程序",1),
    ADD_BUCKET("加购物车",2),
    SEARCH_ITEM("搜索商品",3),
    SHARE("分享小程序",4),
    OPEN("打开小程序",5);


    private final String name;
    private final int index;

    MemberBuriedPointEnum(String name, int index) {
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
