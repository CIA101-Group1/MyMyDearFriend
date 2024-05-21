package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateResDTO {
    private String memberId;

    private String authorization;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        CREATE_SUCCESS("1", "新增成功"),

        IMAGE_FORMAT_ERROR("-1", "上傳檔案格式錯誤"),

        EXIST_ACCOUNT("-2", "該帳號已存在"),

        EXIST_EMAIL("-3", "該信箱已存在");

        private final String code;

        private final String message;
    }
}
