package com.xrb.netty_learn.nio.c1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xieren8iao
 * @date 2022/1/29 9:54 下午
 */
public class TestWalkFileTree {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("/Users/xierenbiao/Movies/学习视频");
        AtomicInteger dirCount=new AtomicInteger();
        AtomicInteger fileCount=new AtomicInteger();
        Files.walkFileTree(path,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("dir======>"+dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("file======>"+file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("dirCount"+dirCount);
        System.out.println("fileCount"+fileCount);
    }
}