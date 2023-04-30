package com.ms.email.emailsender.consumers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ms.email.emailsender.dto.EmailDTO;
import com.ms.email.emailsender.models.Email;
import com.ms.email.emailsender.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    @Autowired
    EmailService EmailService;
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDTO emailDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDto, email);
        EmailService.sendEmail(email);
    }
}
