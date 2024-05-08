package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Immutable;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "news")
@Immutable
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", nullable = false)
    private String bidId;

    @Column(name = "title", nullable = false)
    private Integer title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "active", nullable = false)
    private String active;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Column(name = "image", columnDefinition = "mediumblob")
    private byte[] image;
}
