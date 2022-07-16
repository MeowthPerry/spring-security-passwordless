package com.github.meperry.security.otp.filter;

import com.github.meperry.security.otp.provider.VerifyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyFilter extends GenericFilterBean {

  private Logger logger = LoggerFactory.getLogger(VerifyFilter.class);

  private final RequestMatcher verifyMatcher;

  private final VerifyProvider verifyProvider;

  public VerifyFilter(String verifyPattern, VerifyProvider verifyProvider) {
    this.verifyMatcher = new AntPathRequestMatcher(verifyPattern, HttpMethod.POST.name());
    this.verifyProvider = verifyProvider;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
  }

  private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    if (verifyMatcher.matches(request)) {
      verifyProvider.verify(request, response);
    } else {
      chain.doFilter(request, response);
    }
  }
}
