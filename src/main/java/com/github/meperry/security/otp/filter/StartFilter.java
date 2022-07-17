package com.github.meperry.security.otp.filter;

import com.github.meperry.security.otp.provider.OtpProvider;
import com.github.meperry.security.otp.service.OtpRememberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

public class StartFilter extends GenericFilterBean {

  private Logger logger = LoggerFactory.getLogger(StartFilter.class);

  private final RequestMatcher startMatcher;
  private final String verifyPattern;

  private final List<OtpProvider> providers;

  private final OtpRememberService otpRememberService;

  public StartFilter(String startPattern, String verifyPattern, List<OtpProvider> providers, OtpRememberService otpRememberService) {
    this.startMatcher = new AntPathRequestMatcher(startPattern, HttpMethod.POST.name());
    this.verifyPattern = verifyPattern;
    this.providers = providers;
    this.otpRememberService = otpRememberService;
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    this.doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
  }

  private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    if (startMatcher.matches(request)) {
      String otp = providers.stream().filter(provider -> provider.supports(request)).findFirst()
          .map(provider -> provider.start(request)).orElseThrow(() -> new RuntimeException("No request supporting providers"));
      if (otpRememberService.remember(request, response, otp)) {
        response.addHeader(HttpHeaders.LOCATION, verifyPattern);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      } else {
        logger.error("Failed to remember otp");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      }
    }
    chain.doFilter(request, response);
  }
}
