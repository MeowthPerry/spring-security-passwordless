package com.github.meperry.security.otp.filter;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class OtpVerifyFilter extends GenericFilterBean {

  private final RequestMatcher requestMatcher;
  private final AuthenticationProvider authenticationProvider;

  public OtpVerifyFilter(RequestMatcher requestMatcher, AuthenticationProvider authenticationProvider) {
    this.requestMatcher = requestMatcher;
    this.authenticationProvider = authenticationProvider;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    authenticationProvider.authenticate(null);
  }
}
