package com.github.meperry.security.otp.provider.impl;

import com.github.meperry.security.otp.provider.OtpProvider;
import com.github.meperry.security.otp.util.JsonRequestBodyParser;
import com.github.meperry.security.otp.util.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class EmailOtpProvider implements OtpProvider {

  private Logger logger = LoggerFactory.getLogger(EmailOtpProvider.class);

  private final JavaMailSender emailSender;

  public EmailOtpProvider(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public boolean supports(HttpServletRequest request) {
    return true;
  }

  @Override
  public String start(HttpServletRequest request) {
    PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
        .useDigits(true)
        .useLower(true)
        .useUpper(true)
        .build();
    String password = passwordGenerator.generate(8);
    try {
      String email = getEmail(request);
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(Optional.ofNullable(((JavaMailSenderImpl) emailSender).getUsername())
          .orElseThrow(() -> new RuntimeException("Username not specified")));
      message.setTo(email);
      message.setSubject("One time password");
      message.setText(password);
      emailSender.send(message);
    } catch (IOException e) {
      logger.error("Error", e);
      throw new RuntimeException(e);
    }
    return password;
  }

  private String getEmail(HttpServletRequest request) throws IOException {
    return (String) JsonRequestBodyParser.get(request, "email");
  }
}
