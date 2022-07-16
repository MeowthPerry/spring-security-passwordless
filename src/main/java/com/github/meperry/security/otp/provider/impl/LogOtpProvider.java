package com.github.meperry.security.otp.provider.impl;

import com.github.meperry.security.otp.provider.OtpProvider;
import com.github.meperry.security.otp.util.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class LogOtpProvider implements OtpProvider {

  private Logger logger = LoggerFactory.getLogger(LogOtpProvider.class);

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
    logger.info("Generated one time password: {}", password);
    return password;
  }
}
