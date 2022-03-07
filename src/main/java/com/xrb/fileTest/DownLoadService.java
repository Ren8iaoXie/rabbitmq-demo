package com.xrb.fileTest;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * @author xieren8iao
 * @date 2021/8/3 7:51 下午
 */
public interface DownLoadService {
    public void downOneFile(String fileName) throws Exception;

    public void downTogetherAndZip(List<String> fileName) throws IOException;


    }
