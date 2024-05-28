package com.tibame.group1.common.dto.web;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class NewsAddReqDTO {

    @Size(max = 40, message = "標題：請勿超過{max}字")
    @NotNull(message = "標題：請勿空白")
    private String title;

    @Size(max = 800, message = "內容：請勿超過{max}字")
    @NotNull(message = "內容：請勿空白")
    private String content;

    @NotNull
    private MultipartFile image;
}
