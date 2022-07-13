package com.github.meperry.security.otp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OtpRememberService {

  boolean remember(HttpServletRequest request, HttpServletResponse response, String otp);

  boolean check(HttpServletRequest request);
}
