package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", nullable = false)
    private Integer id;

    @Size(max = 40)
    @NotNull
    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Size(max = 800)
    @NotNull
    @Column(name = "content", nullable = false, length = 800)
    private String content;

    @NotNull
    @Column(name = "image", nullable = false)
    private byte[] image;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;
}
