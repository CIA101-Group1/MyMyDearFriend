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

    @Size(max = 40)
    @NotNull
    private String title;

    @Size(max = 800)
    @NotNull
    private String content;

    @NotNull
    private MultipartFile image;
}
