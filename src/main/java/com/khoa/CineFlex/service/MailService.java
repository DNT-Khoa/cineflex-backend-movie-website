package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.InviteAdminEmail;
import com.khoa.CineFlex.DTO.ResetPasswordEmail;
import com.khoa.CineFlex.exception.CineFlexException;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    void sendMail(InviteAdminEmail inviteAdminEmail) throws MailException{
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("cineflexteam@gmail.com");
            messageHelper.setTo(inviteAdminEmail.getRecipient());
            messageHelper.setSubject(inviteAdminEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(inviteAdminEmail.getBody(), inviteAdminEmail.getJoinLink()), true);
        };

        mailSender.send(messagePreparator);
    }

    @Async
    void sendMail(ResetPasswordEmail resetPasswordEmail) throws MailException{
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("cineflexteam@gmail.com");
            messageHelper.setTo(resetPasswordEmail.getRecipient());
            messageHelper.setSubject(resetPasswordEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(resetPasswordEmail.getBody(), resetPasswordEmail.getJoinLink()), true);
        };

        mailSender.send(messagePreparator);
    }
}
