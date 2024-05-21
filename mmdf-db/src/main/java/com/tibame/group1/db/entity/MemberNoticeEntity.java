package com.tibame.group1.db.entity;

import com.tibame.group1.common.exception.EnumCodeNotFoundException;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import org.springframework.data.annotation.Immutable;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "member_notice")
@Immutable
public class MemberNoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_notice_id", nullable = false)
    private Integer memberNoticeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Column(name = "notice_title", nullable = false, length = 50)
    private String noticeTitle;

    @Column(name = "notice_content", nullable = false, length = 1000)
    private String noticeContent;

    @Column(name = "notice_category", nullable = false, length = 50)
    @Convert(converter = NoticeCategory.NoticeCategoryConverter.class)
    private NoticeCategory noticeCategory;

    @Column(name = "sending_time", columnDefinition = "datetime")
    private Date sendingTime;

    @Column(name = "last_reading_time", columnDefinition = "datetime")
    private Date lastReadingTime;

    @AllArgsConstructor
    @Getter
    public enum NoticeCategory {
        GENERAL_PRODUCT("0", "一般商城通知"),

        BID_PRODUCT("1", "競標商城通知"),

        CHATROOM("2", "聊天室通知"),

        SERVICE("3", "客服通知"),

        NEWS("4", "最新消息"),

        MARKET("5", "市集通知"),

        SYSTEM("6", "系統通知");

        private final String code;

        private final String message;

        public static NoticeCategory toNoticeCategory(String code)
                throws EnumCodeNotFoundException {
            for (NoticeCategory noticeCategory : NoticeCategory.values()) {
                if (code.equals(noticeCategory.getCode())) {
                    return noticeCategory;
                }
            }
            throw new EnumCodeNotFoundException("通知類別代碼有誤");
        }

        public static class NoticeCategoryConverter
                implements AttributeConverter<NoticeCategory, String> {

            @Override
            public String convertToDatabaseColumn(NoticeCategory noticeCategory) {
                return noticeCategory.getCode();
            }

            @Override
            @SneakyThrows
            public NoticeCategory convertToEntityAttribute(String s) {
                return toNoticeCategory(s);
            }
        }
    }
}
