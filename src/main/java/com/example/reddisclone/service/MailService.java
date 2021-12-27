package com.example.reddisclone.service;

import com.example.reddisclone.entity.NotificationEmail;
import com.example.reddisclone.exception.RedditCloneExcption;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
     void  sendMail(NotificationEmail notificationEmail) {
         MimeMessagePreparator messagePreparator = mimeMessage -> {
             MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
             messageHelper.setFrom("springreddit@gmail.com");
             messageHelper.setTo(notificationEmail.getRecipient());
             messageHelper.setSubject(notificationEmail.getSubject());
             messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
         };
         try {
             mailSender.send(messagePreparator);
             log.info("Activiation email sent");
         }catch (MailException e) {
             throw new RedditCloneExcption("Exception occured when sending email to "+notificationEmail.getRecipient() +e.getMessage());
         }
    }
}
