package com.jagt.reader.auth.infrastructure.output.notification.adapter;

import com.jagt.reader.auth.domain.exception.SendEmailException;
import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.auth.domain.port.output.EmailSenderPort;
import com.jagt.reader.auth.infrastructure.output.notification.factory.EmailTemplateFactory;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailSenderAdapter implements EmailSenderPort {
    private final JavaMailSender mailSender;
    private final EmailTemplateFactory emailTemplateFactory;
    private final MessageProvider messageProvider;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendCode(String to, String code, CodeType codeType, LocalDateTime expiration) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String subject = emailTemplateFactory.getSubject(codeType);
            String content = emailTemplateFactory.generateHtml(codeType, code, expiration);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new SendEmailException(messageProvider.getMessage("email.send.error"));
        }
    }
}
