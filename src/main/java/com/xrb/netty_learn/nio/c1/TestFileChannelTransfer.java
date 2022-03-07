package com.xrb.netty_learn.nio.c1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author xieren8iao
 * @date 2022/1/29 6:39 下午
 */
public class TestFileChannelTransfer {
    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("data.txt").getChannel();
                FileChannel to = new FileOutputStream("to.txt").getChannel();) {
            //零拷贝 最多传输2g数据
//            from.transferTo(0,from.size(),to);
//            to.transferFrom(from,0,from.size());
            long fileSize = from.size();
            for (long leftSize = fileSize; leftSize > 0; ) {
                leftSize -= from.transferTo(fileSize - leftSize, leftSize, to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}