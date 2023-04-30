package com.ms.email.emailsender.repositories;

import com.ms.email.emailsender.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<Email, UUID> {


}
