package com.github.meperry.security.otp.provider;

import javax.servlet.http.HttpServletRequest;

public interface OtpProvider {
  
  boolean supports(HttpServletRequest request);

  String start(HttpServletRequest request);
}
