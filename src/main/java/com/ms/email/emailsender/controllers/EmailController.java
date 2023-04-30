package com.ms.email.emailsender.controllers;
import com.ms.email.emailsender.dto.EmailDTO;
import com.ms.email.emailsender.models.Email;
import com.ms.email.emailsender.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@RestController
public class EmailController {

    final
    EmailService EmailService;

    public EmailController(EmailService emailService) {
        this.EmailService = emailService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<Email> sendEmail(@RequestBody @Valid EmailDTO emailDto) {
        Email emailModel = new Email();
        BeanUtils.copyProperties(emailDto, emailModel);
        EmailService.sendEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @GetMapping("/get-emails")
    public ResponseEntity<Page<Email>> getAllEmails(@PageableDefault(size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(EmailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/get-email/{emailId}")
    public ResponseEntity<Object> getEmailById(@PathVariable UUID emailId) {
        Optional<Email> emailModelOptional = EmailService.findById(emailId);
        return emailModelOptional
                .<ResponseEntity<Object>>map(email -> ResponseEntity.status(HttpStatus.OK)
                .body(email))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found"));
    }
}
