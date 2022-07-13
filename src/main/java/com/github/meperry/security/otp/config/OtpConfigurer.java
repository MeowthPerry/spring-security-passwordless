package com.github.meperry.security.otp.config;

import com.github.meperry.security.otp.filter.OtpAuthenticationStartFilter;
import com.github.meperry.security.otp.filter.OtpVerifyFilter;
import com.github.meperry.security.otp.provider.OtpVerifyProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        .addFilterBefore(new OtpAuthenticationStartFilter(startPattern, verifyPattern), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new OtpVerifyFilter(new AntPathRequestMatcher(verifyPattern, HttpMethod.POST.name()), new OtpVerifyProvider()), UsernamePasswordAuthenticationFilter.class);
  }
}
