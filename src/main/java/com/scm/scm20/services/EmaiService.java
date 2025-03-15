package com.scm.scm20.services;

public interface EmaiService {

    void sendEmail(String to,String subject,String body);

    void sendEmailWithHtml();

    void sendEmailWithAttachment();
}
