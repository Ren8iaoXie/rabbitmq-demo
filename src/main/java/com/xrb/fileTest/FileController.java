package com.xrb.fileTest;

/**
 * @author xieren8iao
 * @date 2021/8/3 7:50 下午
 */

import cn.hutool.core.io.IoUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author simons.fan
 * @version 1.0
 * @date 2019/7/9
 * @description 文件下载controller
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private DownLoadService downLoadService;

    /**
     * 单个文件下载
     *
     * @param fileName 真实存在的文件名
     */
    @GetMapping("/down-one")
    public void downOneFile(@RequestParam(value = "filename", required = false) String fileName) {
        logger.info("单个文件下载接口入参:[filename={}]", fileName);
        if (fileName.isEmpty()) {
            return;
        }
        try {
            downLoadService.downOneFile(fileName);
        } catch (Exception e) {
            logger.error("单个文件下载接口异常:{fileName={},ex={}}", fileName, e);
        }
    }

    /**
     * 批量打包下载文件
     *
     * @param fileName 文件名，多个用英文逗号分隔
     */
    @GetMapping("/down-together")
    public void downTogether(@RequestParam(value = "filename", required = false) String fileName) {
        logger.info("批量打包文件下载接口入参:[filename={}]", fileName);
        if (fileName.isEmpty()) return;
        List<String> fileNameList = Arrays.asList(fileName.split(","));
        if (CollectionUtils.isEmpty(fileNameList)) return;
        try {
            downLoadService.downTogetherAndZip(fileNameList);
        } catch (Exception e) {
            logger.error("批量打包文件下载接口异常:{fileName={},ex={}}", fileName, e);
        }
    }

    /**
     * 批量打包下载文件
     */
    @GetMapping("/down-dowlandImg")
    public void dowlandImg(HttpServletResponse response, String[] urlArr) {
        try {
            String downloadFilename = "打包图片.zip";// 文件的名称
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");// 转换中文否则可能会产生乱码
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            String[] fileArray = {"绿地.jpg", "青山绿水.jpg","海岸.jpeg","猫头鹰.png","4.jpg","两只老虎.jpg"};
            for (String fileName : fileArray) {
                zos.putNextEntry(new ZipEntry(fileName));//在压缩文件中建立名字为XXX的文件
                FileInputStream fileInputStream = new FileInputStream(new File("/Users/xierenbiao/Pictures/" + fileName));

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = fileInputStream.read(b)) != -1) {
                    bos.write(b, 0, len);
                }
                byte[] bytes = bos.toByteArray();
//                zos.write(bytes, 0, bytes.length);
//            bos.writeTo(zos);

                InputStream is = new ByteArrayInputStream(bytes);
                byte[] buffer = new byte[1024];
                int r = 0;
                while ((r = is.read(buffer)) != -1) {
                    zos.write(buffer, 0, r);
                }

//            byte[] buffer = new byte[1024];
//            int r = 0;
//            while ((r = fileInputStream.read(buffer)) != -1) {
//                zos.write(buffer, 0, r);
//            }
                fileInputStream.close();
            }


            zos.flush();
            zos.close();
        } catch (Exception e) {
            logger.error("下载XXX错误", e);
        }
    }
}