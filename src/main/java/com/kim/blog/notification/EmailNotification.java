package com.kim.blog.notification;

import com.kim.blog.util.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotification implements NotificationInterface{
    private final EmailService emailService;
    @Override
    public void notification(String target,String title,String content) {
        emailService.sendMail(target,title,content);
    }
}
