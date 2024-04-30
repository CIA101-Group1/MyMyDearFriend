package com.tibame.group1.common.utils;


import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 檔案處理共用
 *
 * @author Jimmy Kang
 */
public class FileUtils {

    public static final String sepSymbol = File.separator;


    /**
     * 讀取文字檔
     *
     * @param resource 檔案資源
     * @return 讀取資料
     */
    public static String readTextFile(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return readTextFile(inputStream);
        }
    }

    /**
     * 讀取文字檔
     *
     * @param inputStream 資料流
     * @return 讀取資料
     */
    public static String readTextFile(InputStream inputStream) throws IOException {
        InputStreamReader input = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(input);
        String result = IOUtils.toString(reader);
        reader.close();
        return result;
    }
}
