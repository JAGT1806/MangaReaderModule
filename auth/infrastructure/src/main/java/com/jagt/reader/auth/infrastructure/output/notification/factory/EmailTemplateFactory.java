package com.jagt.reader.auth.infrastructure.output.notification.factory;

import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class EmailTemplateFactory {
    private final MessageProvider messageProvider;

    public String getSubject(CodeType codeType) {
        return switch (codeType) {
            case ACTIVATION -> messageProvider.getMessage("email.activation");
            case RECOVERING -> messageProvider.getMessage("email.recovery");
        };
    }

    public String generateHtml(CodeType codeType, String code, LocalDateTime expiration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String date = expiration.format(formatter);
        String body = getBody(codeType);
        String footer = getFooter(codeType);

        return getHtmlTemplate(date, body, footer, code);
    }

    private String getBody(CodeType codeType) {
        return switch (codeType) {
            case ACTIVATION -> messageProvider.getMessage("email.activation.body");
            case RECOVERING -> messageProvider.getMessage("email.recovery.body");
        };
    }

    private String getFooter(CodeType codeType) {
        return switch (codeType) {
            case ACTIVATION -> messageProvider.getMessage("email.activation.footer");
            case RECOVERING -> messageProvider.getMessage("email.recovery.footer");
        };
    }

    private String getHtmlTemplate(String date, String body, String footer, String code) {
        return String.format("""
                <!DOCTYPE html>
                <html lang='en'>
                <head>
                  <meta charset='UTF-8'>
                  <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                  <style>
                    body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }
                    .container { max-width: 600px; margin: 40px auto; background: #fff; border-radius: 8px;
                                 box-shadow: 0 0 10px rgba(0,0,0,0.1); overflow: hidden; }
                    .header { background-color: #2c2c54; color: #fff; padding: 20px; text-align: center; }
                    .body { padding: 30px; color: #333; line-height: 1.6; font-size: 16px; text-align: center; }
                    .code { display: inline-block; background: #2c2c54; color: #fff; padding: 12px 24px;
                            border-radius: 6px; font-size: 20px; letter-spacing: 2px; margin: 20px 0; }
                    .footer { background: #f1f1f1; padding: 10px; text-align: center; font-size: 12px; color: #777; }
                  </style>
                </head>
                <body>
                  <div class="container">
                    <div class="header">
                      <h1>MangaReader</h1>
                    </div>
                    <div class="body">
                      <p>%s</p>
                      <div class="code">%s</div>
                      <p><small>%s</small></p>
                    </div>
                    <div class="footer">
                      <p>%s</p>
                      <p>&copy; 2025 MangaReader</p>
                    </div>
                  </div>
                </body>
                </html>
                """, body, code, messageProvider.getMessage("email.valid.until", new Object[]{date}), footer);
    }

}
