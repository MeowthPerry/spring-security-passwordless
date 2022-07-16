package com.github.meperry.security.otp.provider.impl;

import com.github.meperry.security.otp.filter.StartFilter;
import com.github.meperry.security.otp.service.OtpRememberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleVerifyProvider extends AbstractVerifyProvider {

  private Logger logger = LoggerFactory.getLogger(StartFilter.class);

  public SimpleVerifyProvider(OtpRememberService otpRememberService) {
    super(otpRememberService);
  }

  @Override
  protected void onSuccess(HttpServletRequest request, HttpServletResponse response) {
    // do nothing
  }
}
