package com.github.meperry.security.otp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passwordless")
public class AuthenticationController {

  @PostMapping(value = "/start")
  public void startAuthentication() {
    // do nothing
  }

  @PostMapping(value = "/verify")
  public void verifyAuthentication() {
    // do nothing
  }
}
