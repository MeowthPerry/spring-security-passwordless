package com.github.meperry.security.otp.config;

import com.github.meperry.security.otp.filter.StartFilter;
import com.github.meperry.security.otp.filter.VerifyFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class OtpConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private String startPattern;
  private String verifyPattern;

  public OtpConfigurer(String startPattern, String tokenPattern) {
    this.startPattern = startPattern;
    this.verifyPattern = tokenPattern;
  }

  @Override
  public void configure(HttpSecurity builder) {
    builder
        .addFilterBefore(new StartFilter(startPattern, verifyPattern), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new VerifyFilter(verifyPattern), UsernamePasswordAuthenticationFilter.class);
  }
}
