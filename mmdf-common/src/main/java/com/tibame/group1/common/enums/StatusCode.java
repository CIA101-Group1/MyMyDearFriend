package com.tibame.group1.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    SUCCESS("0", "傳輸成功"),

    REQUEST_FORMAT_ERROR("-1", "request格式解析錯誤，請檢查格式是否正確"),

    REQUEST_KEY_ERROR("-2", "request鍵值缺少錯誤，請檢查是否有缺少鍵值"),

    HTTP_ERROR("-3", "無效的http url"),

    HTTP_METHOD_ERROR("-4", "http method格式錯誤，請檢查http method是否正確"),

    TIME_FORMAT_ERROR("-5", "時間或日期格式錯誤"),

    NOT_FOUND("-6", "查無資料"),

    AUTHORIZATION_ERROR("-7", "Authorization檢驗失敗"),

    NOT_LOGIN("-8", "尚未登入"),

    REQUEST_DATA_CHECK_FAIL("-9", "帶入資料不符合檢查規則"),

    PERMISSION_ERROR("-10", "權限不足無法進行操作"),

    CAPTCHA_ERROR("-11", "驗證碼驗證錯誤"),

    EXIST_ERROR("-12", "資料已存在"),

    SCHEDULE_ERROR("-13", "排程操作錯誤"),

    FILE_TOO_BIG("-996", "上傳檔案超過限制大小"),

    SQL_ERROR("-997", "資料庫處理錯誤"),

    CUSTOM_ERROR("-998", "處理錯誤"),

    UNKNOWN_ERROR("-999", "未知的錯誤");

    private final String code;

    private final String message;
}
