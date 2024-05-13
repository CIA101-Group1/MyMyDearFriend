package com.tibame.group1.common.utils;

import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 檔案處理共用
 *
 * @author Jimmy Kang, peihui
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

    public static boolean ImageFormatChecker(String base64String) throws IOException {
        // 創建一個 Tika 物件
        Tika tika = new Tika();

        // 將 Base64 字串解碼為原始的檔案數據
        byte[] fileData = ConvertUtils.base64ToBytes(base64String);

        // 將原始的檔案數據轉換為 InputStream
        InputStream inputStream = new ByteArrayInputStream(fileData);
        // 使用 Tika 檢測檔案類型
        String fileType = tika.detect(inputStream);
        // 檢查檔案類型是否為圖片格式（JPG、JPEG 或 PNG）
        return fileType.startsWith("image/jpeg")
                || fileType.startsWith("image/png")
                || fileType.equalsIgnoreCase("image/jpg");
    }
}
