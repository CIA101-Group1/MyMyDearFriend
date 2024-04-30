package com.tibame.group1.common.utils;

import com.tibame.group1.common.exception.EmailException;
import com.tibame.group1.common.listener.EmailSentListener;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 信件發送共用方法
 *
 * @author Jimmy Kang
 */
@Slf4j
public class EmailUtils {

    /**
     * 初始化
     *
     * @param sendEmail 寄件人信箱
     * @param emailCid 寄件人信箱密碼(或金鑰)
     */
    public static EmailBuilder init(String sendEmail, String emailCid) {
        return new EmailBuilder(sendEmail, emailCid);
    }

    @Data
    public static class Attachment {

        private String fileName;

        private byte[] byteArray;
    }

    public static class EmailBuilder {
        // 標題
        private String title = "";
        // 寄件人mail
        private String senderEmail;
        // 寄件人
        private String senderName = "";
        // 登入mail
        private final String email;
        // 登入信箱密碼(或金鑰)
        private final String emailCid;
        // 內文
        private final StringBuilder content = new StringBuilder();
        // 收信人列表
        private final List<String> sendList = new ArrayList<>();
        // mailServer的host
        private String emailServerHost = "smtp.gmail.com";
        // mailServer的port
        private int emailServerPort = 587;
        // 內容是否為html
        private Boolean isHtml = false;
        // 附檔
        private List<Attachment> attachmentList = new ArrayList<>();
        // 設定檔
        private final Properties props = new Properties();

        /**
         * 初始化
         *
         * @param email 登入信箱
         * @param emailCid 登入信箱密碼(或金鑰)
         */
        public EmailBuilder(String email, String emailCid) {
            this.email = email;
            this.emailCid = emailCid;
            // 安全檢驗
            props.put("mail.smtp.auth", "false");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "false");
            // 協定版本
            props.put("mail.smtp.ssl.protocols", "TLSv1.3");
        }

        /**
         * 設定標題
         *
         * @param title 標題
         */
        public EmailBuilder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        /**
         * 設定內文
         *
         * @param content 內容文字
         */
        public EmailBuilder addContent(@NonNull String content) {
            this.content.append(content);
            return this;
        }

        /**
         * 新增多個收件人
         *
         * @param sendList 收件人Mail
         */
        public EmailBuilder addSends(List<String> sendList) {
            this.sendList.addAll(sendList);
            return this;
        }

        /**
         * 新增收件人
         *
         * @param sends 收件人Mail
         */
        public EmailBuilder addSends(String... sends) {
            this.sendList.addAll(Arrays.asList(sends));
            return this;
        }

        /**
         * 設定寄件人名稱
         *
         * @param senderName 寄件人名稱
         */
        public EmailBuilder setSenderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        /**
         * 設定寄件人信箱
         *
         * @param senderEmail 寄件人信箱
         */
        public EmailBuilder setSenderEmail(String senderEmail) {
            this.senderEmail = senderEmail;
            return this;
        }

        /**
         * 重新設定mailServer的host
         *
         * @param emailServerHost mailServer的host
         */
        public EmailBuilder setEmailServerHost(String emailServerHost) {
            if (StringUtils.isNotEmpty(emailServerHost)) this.emailServerHost = emailServerHost;
            if (this.emailServerHost.equals("smtp.gmail.com")) {
                // 如果mailHost是gmail開啟安全檢驗
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.starttls.required", "true");
            }
            return this;
        }

        /**
         * 重新設定mailServer的port
         *
         * @param emailServerPort mailServer的port
         */
        public EmailBuilder setEmailServerPort(Integer emailServerPort) {
            if (null != emailServerPort) this.emailServerPort = emailServerPort;
            return this;
        }

        /**
         * 設定email是否為html格式
         *
         * @param isHtml 是否為html格式
         */
        public EmailBuilder setIsHtml(Boolean isHtml) {
            this.isHtml = isHtml;
            return this;
        }

        /**
         * 新增附加檔案
         *
         * @param fileName 附檔名稱
         * @param file 附檔
         */
        public EmailBuilder addAttachment(String fileName, File file) throws IOException {
            return this.addAttachment(fileName, Files.readAllBytes(file.toPath()));
        }

        /**
         * 新增附加檔案
         *
         * @param fileName 附檔名稱
         * @param filepath 實體檔案路徑
         */
        public EmailBuilder addAttachment(String fileName, String filepath) throws IOException {
            return this.addAttachment(fileName, new File(filepath));
        }

        /**
         * 新增附加檔案
         *
         * @param fileName 附檔名稱
         * @param inputStream 附檔stream
         */
        public EmailBuilder addAttachment(String fileName, InputStream inputStream)
                throws IOException {
            return addAttachment(fileName, inputStream.readAllBytes());
        }

        /**
         * 新增附加檔案
         *
         * @param fileName 附檔名稱
         * @param byteArray byteArray
         */
        public EmailBuilder addAttachment(String fileName, byte[] byteArray) {
            Attachment attachment = new Attachment();
            attachment.setFileName(fileName);
            attachment.setByteArray(byteArray);
            attachmentList.add(attachment);
            return this;
        }

        /**
         * 設定附加檔案
         *
         * @param attachmentList 附檔陣列
         */
        public EmailBuilder setAttachment(List<Attachment> attachmentList) {
            this.attachmentList = attachmentList;
            return this;
        }

        /** 測試連線 */
        public boolean testConnection() {
            JavaMailSenderImpl jm = new JavaMailSenderImpl();
            // 編碼
            jm.setDefaultEncoding("UTF-8");
            // HOSTNAME
            jm.setHost(emailServerHost);
            // PORT
            jm.setPort(emailServerPort);
            // 登入信箱
            if (StringUtils.isNotEmpty(this.email)) jm.setUsername(this.email);
            // 登入密碼
            if (StringUtils.isNotEmpty(this.emailCid)) jm.setPassword(this.emailCid);
            // 安全參數
            jm.setJavaMailProperties(props);
            // 測試連線
            try {
                jm.testConnection();
                return true;
            } catch (MessagingException e) {
                log.warn(e.getMessage());
                return false;
            }
        }

        /** 執行發送 */
        public void doSend() throws EmailException {
            try {
                JavaMailSenderImpl jm = new JavaMailSenderImpl();
                // 編碼
                jm.setDefaultEncoding("UTF-8");
                // HOSTNAME
                jm.setHost(emailServerHost);
                // PORT
                jm.setPort(emailServerPort);
                // 登入信箱
                if (StringUtils.isNotEmpty(this.email)) jm.setUsername(this.email);
                // 登入密碼
                if (StringUtils.isNotEmpty(this.emailCid)) jm.setPassword(this.emailCid);
                // 安全參數
                jm.setJavaMailProperties(props);
                List<MimeMessage> messageList = new ArrayList<>();
                for (String email : sendList) {
                    MimeMessage message = jm.createMimeMessage();
                    MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
                    // 寄件人名稱
                    if (StringUtils.isNotEmpty(senderEmail) && StringUtils.isNotEmpty(senderName))
                        messageHelper.setFrom(senderEmail, senderName);
                    // 標題
                    messageHelper.setSubject(StringUtils.normalizeSpace(title));
                    // 內容
                    messageHelper.setText(content.toString(), isHtml);
                    // 設定收件人
                    messageHelper.setTo(email);
                    // 附加檔名轉碼
                    messageHelper.setEncodeFilenames(true);
                    // 設定附檔
                    for (Attachment attachment : attachmentList) {
                        messageHelper.addAttachment(
                                attachment.getFileName(),
                                new ByteArrayResource(attachment.getByteArray()));
                    }
                    messageList.add(message);
                }
                // 寄出
                jm.send(messageList.toArray(new MimeMessage[] {}));
            } catch (Exception e) {
                throw new EmailException(e.getMessage());
            }
        }

        /** 寄出通知信(另開執行緒) */
        public void doSendOnThread() {
            doSendOnThread(null);
        }

        /**
         * 寄出通知信(另開執行緒)
         *
         * @param listener 發送訊息結果監聽器
         */
        public void doSendOnThread(EmailSentListener listener) {
            new Thread(
                            () -> {
                                try {
                                    doSend();
                                    if (null != listener) listener.onSentSuccess();
                                } catch (EmailException e) {
                                    if (null != listener) listener.onSentError(e);
                                }
                            })
                    .start();
        }
    }
}
