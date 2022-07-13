package com.github.meperry.security.otp.filter;

import com.github.meperry.security.otp.provider.OtpProvider;
import com.github.meperry.security.otp.service.OtpRememberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import java.util.List;

public class OtpAuthenticationStartFilter extends GenericFilterBean {

  private Logger logger = LoggerFactory.getLogger(OtpAuthenticationStartFilter.class);

  private final RequestMatcher startMatcher;
  private final String verifyPattern;

  @Autowired
  private List<OtpProvider> providers;

  @Autowired
  private OtpRememberService otpRememberService;

  public OtpAuthenticationStartFilter(String startPattern, String verifyPattern) {
    this.startMatcher = new AntPathRequestMatcher(startPattern, HttpMethod.POST.name());
    this.verifyPattern = verifyPattern;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    this.doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
  }

  private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    if (startMatcher.matches(request)) {
      String otp = providers.stream().filter(provider -> provider.supports(request)).findFirst()
          .map(provider -> provider.start(request)).orElseThrow(() -> new RuntimeException("No request supporting providers"));
      if (otpRememberService.remember(request, response, otp)) {
        response.setStatus(HttpStatus.CONTINUE.value());
        response.addHeader(HttpHeaders.LOCATION, verifyPattern);
      } else {
        logger.error("Failed to remember otp");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      }
    } else {
      chain.doFilter(request, response);
    }
  }
}
