package com.xrb.netty_learn.nio;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xieren8iao
 * @date 2021/10/31 9:32 上午
 */
public class NioTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        System.out.println("开始连接服务端");
        while (true){
            final Socket socket = serverSocket.accept();
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }

    }

    private static void handler(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buf = new byte[1024];
            while (true) {
                int read = inputStream.read(buf);
                if (read != -1) {
                    System.out.println(new String(buf, 0, read));
                } else {
                    break;
                }
            }
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * nio写文件到本地
     *  1、底层还是使用outputstream，先new出来指定要写入到的文件路径
     *  2、根据输出流获得通道channel
     *  3、new一个缓冲区，将文字写入到缓冲区，写入完成后，需要反转读写，将写改为读
     *  4、使用通道将缓存区写入到通道
     *  5、关闭流
     * @throws Exception
     */
    @Test
    public void writeFile() throws Exception {
        FileOutputStream outputStream=new FileOutputStream("/Users/xierenbiao/Downloads/a.txt");

        FileChannel fileChannel = outputStream.getChannel();

        String str="你好，666";

        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());
        //TODO *关键 读写切换
        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        outputStream.close();
    }
    @Test
    public void readFile() throws Exception{
        File file=new File("/Users/xierenbiao/Downloads/a.txt");
        FileInputStream fileInputStream=new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);

        byte[] array = byteBuffer.array();
        String str = new String(array);

        System.out.println(str);

        fileInputStream.close();

    }
}