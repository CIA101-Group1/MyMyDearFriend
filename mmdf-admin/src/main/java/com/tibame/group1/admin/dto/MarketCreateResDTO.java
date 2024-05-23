package com.tibame.group1.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MarketCreateResDTO {
    private String  marketId;

    private String authorization;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        CREATE_SUCCESS("1", "新增成功"),

        IMAGE_FORMAT_ERROR("-1", "上傳檔案格式錯誤");

        private final String code;

        private final String message;
    }
}
