package com.github.meperry.security.otp.provider.impl;

import com.github.meperry.security.otp.provider.VerifyProvider;
import com.github.meperry.security.otp.service.OtpRememberService;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractVerifyProvider implements VerifyProvider {

  private final OtpRememberService otpRememberService;

  protected AbstractVerifyProvider(OtpRememberService otpRememberService) {
    this.otpRememberService = otpRememberService;
  }

  @Override
  public boolean verify(HttpServletRequest request, HttpServletResponse response) {
    if (otpRememberService.check(request)) {
      onSuccess(request, response);
      return true;
    }
    onFail(request, response);
    return false;
  }

  protected void onFail(HttpServletRequest request, HttpServletResponse response) {
    response.setStatus(HttpStatus.FORBIDDEN.value());
  }

  protected abstract void onSuccess(HttpServletRequest request, HttpServletResponse response);

  @Override
  public OtpRememberService getOtpRememberService() {
    return otpRememberService;
  }
}
