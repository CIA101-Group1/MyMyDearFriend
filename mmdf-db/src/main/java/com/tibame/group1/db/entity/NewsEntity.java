package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", nullable = false)
    private Integer newsId;

    @Size(max = 40)
    @NotNull
    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Size(max = 800)
    @NotNull
    @Column(name = "content", nullable = false, length = 800)
    private String content;

    @NotNull
    @Lob
    @Column(name = "image", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @Transient private String imageBase64;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Date lastModified;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    public String getStatusMessage() {
        if (this.status == 0) {
            return "下架中";
        } else {
            return "上架中";
        }
    }
}
