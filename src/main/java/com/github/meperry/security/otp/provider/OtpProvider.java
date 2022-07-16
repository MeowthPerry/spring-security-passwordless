package com.github.meperry.security.otp.provider;

import javax.servlet.http.HttpServletRequest;

/**
 * Specifies that the class can provide the user with a one-time password in a specific way.
 *
 * @author  Islam Khabibullin
 */
public interface OtpProvider {

  /**
   * Determines whether the provider supports this request
   *
   * @param request initial request from the user
   * @return        returns true if the provider supports this request
   */
  boolean supports(HttpServletRequest request);

  /**
   * Provides one-time password to user
   *
   * @param request initial request from the user
   * @return        one-time password provided to the user
   */
  String start(HttpServletRequest request);
}
