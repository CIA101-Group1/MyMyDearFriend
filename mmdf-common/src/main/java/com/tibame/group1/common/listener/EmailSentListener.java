package com.tibame.group1.common.listener;


import com.tibame.group1.common.exception.EmailException;

public interface EmailSentListener {

    void onSentSuccess();

    void onSentError(EmailException e);
}
