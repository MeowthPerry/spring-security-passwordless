package com.github.meperry.security.otp.config;

import com.github.meperry.security.otp.provider.impl.LogOtpProvider;
import com.github.meperry.security.otp.provider.impl.SimpleVerifyProvider;
import com.github.meperry.security.otp.service.OtpRememberService;
import com.github.meperry.security.otp.service.impl.MapRememberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    OtpRememberService otpRememberService = new MapRememberService();
    http
        .csrf().disable()
        .apply(new OtpConfigurer("/passwordless/start", "/passwordless/verify", List.of(new LogOtpProvider()), new SimpleVerifyProvider(otpRememberService)));
    return http.build();
  }
}
