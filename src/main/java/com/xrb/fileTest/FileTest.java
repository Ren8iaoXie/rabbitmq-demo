package com.xrb.fileTest;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.util.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author xieren8iao
 * @date 2022/3/29 2:33 下午
 */
public class FileTest {
    private static final String PATH = "/Users/xierenbiao/Downloads";

    public static void main(String[] args) {
        File file = new File(PATH + "/a.txt");

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            FileChannel channel = outputStream.getChannel();
            String writeStr = "testDemo111";
            ByteBuffer buf=ByteBuffer.allocate(1024);
            buf.put(writeStr.getBytes(StandardCharsets.UTF_8));
            buf.flip();
            channel.write(buf);
//            outputStream.write(writeStr.getBytes(StandardCharsets.UTF_8), 0, writeStr.length());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}