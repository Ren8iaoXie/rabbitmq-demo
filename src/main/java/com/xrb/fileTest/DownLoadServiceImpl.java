package com.xrb.fileTest;

/**
 * @author xieren8iao
 * @date 2021/8/3 7:53 下午
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author simons.fan
 * @version 1.0
 * @date 2019/7/9
 * @description 文件下载service
 **/
@Service
public class DownLoadServiceImpl implements DownLoadService {

    private static final Logger logger = LoggerFactory.getLogger(DownLoadServiceImpl.class);

    private final static String FILE_ROOT_PATH="/Users/xierenbiao/Pictures/";

    /**
     * 单个文件下载
     *
     * @param fileName 单个文件名
     * @throws Exception
     */
    @Override
    public void downOneFile(String fileName) throws Exception {
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        File file = new File(FILE_ROOT_PATH + fileName);
        if (file.exists()) {
            resp.setContentType("application/x-msdownload");
            resp.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
            InputStream inputStream = new FileInputStream(file);
            ServletOutputStream ouputStream = resp.getOutputStream();
            byte b[] = new byte[1024];
            int n;
            while ((n = inputStream.read(b)) != -1) {
                ouputStream.write(b, 0, n);
            }
            ouputStream.close();
            inputStream.close();
        }
    }

    /**
     * 文件批量打包下载
     *
     * @param fileNameList 多个文件名，用英文逗号分隔开
     * @throws IOException
     */
    @Override
    public void downTogetherAndZip(List<String> fileNameList) throws IOException {
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        resp.setContentType("application/x-msdownload");
        //暂时设定压缩下载后的文件名字为test.zip
        resp.setHeader("Content-Disposition", "attachment;filename=test.zip");
        String str = "";
        String rt = "\r\n";
        ZipOutputStream zos = new ZipOutputStream(resp.getOutputStream());
        for (String filename : fileNameList) {
            str += filename + rt;
            File file = new File(FILE_ROOT_PATH + filename);
            zos.putNextEntry(new ZipEntry(filename));
            FileInputStream fis = new FileInputStream(file);
            byte b[] = new byte[1024];
            int n = 0;
            while ((n = fis.read(b)) != -1) {
                zos.write(b, 0, n);
            }
//            zos.flush();
            fis.close();
        }
        //设置解压文件后的注释内容
        zos.setComment("download success:" + rt + str);
        zos.flush();
        zos.close();
    }
}