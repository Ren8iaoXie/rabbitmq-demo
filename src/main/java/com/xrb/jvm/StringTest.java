package com.xrb.jvm;

import com.google.common.collect.Sets;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author xieren8iao
 * @date 2022/4/24 9:11 上午
 */
public class StringTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.vm.name"));
        System.out.println(System.getProperty("java.ext.dirs"));

        HashSet<Integer> integers = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> integers1 = Sets.newHashSet(1, 2);
        Sets.SetView<Integer> difference = Sets.difference(integers, integers1);
        System.out.println(difference);

    }
}
 class SoftRefenenceDemo {
    public static void main(String[] args) {
        softRefMemoryEnough();
        System.out.println("------内存不够用的情况------");
        softRefMemoryNotEnough();
    }

    private static void softRefMemoryEnough() {
        Object o1 = new Object();
        SoftReference<Object> s1 = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(s1.get());
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(s1.get());
    }

    /**
     * JVM配置`-Xms5m -Xmx5m` ，然后故意new一个一个大对象，使内存不足产生 OOM，看软引用回收情况
     */
    private static void softRefMemoryNotEnough() {
        Object o1 = new Object();
        SoftReference<Object> s1 = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(s1.get());
        o1 = null;
        byte[] bytes = new byte[10 * 1024 * 1024];
        System.out.println(o1);
        System.out.println(s1.get());
    }
}