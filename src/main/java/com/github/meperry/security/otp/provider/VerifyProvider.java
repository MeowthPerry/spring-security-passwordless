package com.github.meperry.security.otp.provider;

import com.github.meperry.security.otp.service.OtpRememberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VerifyProvider {

  boolean verify(HttpServletRequest request, HttpServletResponse response);

  OtpRememberService getOtpRememberService();
}
