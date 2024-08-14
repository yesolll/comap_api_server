package com.yesolll.comap_api_server.util.service.mailSender;

import com.yesolll.comap_api_server.common.dto.mail.MailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendTemplateMessage(MailDto mailDto) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        //메일 제목 설정
        helper.setFrom("comapbot@gmail.com");

        helper.setSubject(mailDto.getTitle());

        //수신자 설정
        helper.setTo(mailDto.getAddress());

        //템플릿에 전달할 데이터 설정
        HashMap<String, String> emailValues = new HashMap<>();
        emailValues.put("authId", "asdf");

        Context context = new Context();
        emailValues.forEach((key, value) -> {
            context.setVariable(key, value);
        });

        //메일 내용 설정 : 템플릿 프로세스
        String html = templateEngine.process(mailDto.getTemplate(), context);
        helper.setText(html);

        //템플릿에 들어가는 이미지 cid로 삽입
        helper.addInline("image", new ClassPathResource("static/images/image-1.jpeg"));

        //메일 보내기
        emailSender.send(message);
    }
}