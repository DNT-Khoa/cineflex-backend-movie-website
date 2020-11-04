package com.khoa.CineFlex.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@AllArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    String build(String message, String joinLink) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("joinLink", joinLink);
        return templateEngine.process("inviteAdmin", context);
    }
}
