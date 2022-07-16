package com.github.meperry.security.otp.config;

import com.github.meperry.security.otp.filter.StartFilter;
import com.github.meperry.security.otp.filter.VerifyFilter;
import com.github.meperry.security.otp.provider.OtpProvider;
import com.github.meperry.security.otp.provider.VerifyProvider;
import com.github.meperry.security.otp.service.OtpRememberService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

public class OtpConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final String startPattern;
  private final String verifyPattern;

  private final List<OtpProvider> providers;
  private final OtpRememberService otpRememberService;
  private final VerifyProvider verifyProvider;

  public OtpConfigurer(String startPattern, String tokenPattern, List<OtpProvider> providers, OtpRememberService otpRememberService, VerifyProvider verifyProvider) {
    this.startPattern = startPattern;
    this.verifyPattern = tokenPattern;
    this.providers = providers;
    this.otpRememberService = otpRememberService;
    this.verifyProvider = verifyProvider;
  }

  @Override
  public void configure(HttpSecurity builder) {
    builder
        .addFilterBefore(new StartFilter(startPattern, verifyPattern, providers, otpRememberService), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new VerifyFilter(verifyPattern, verifyProvider), UsernamePasswordAuthenticationFilter.class);
  }
}
