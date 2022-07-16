package com.github.meperry.security.otp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Specifies that the class can remember a one-time password in a specific way, and then check it.
 *
 * @author  Islam Khabibullin
 */
public interface OtpRememberService {

  /**
   * Remember a one-time password in a specific way.
   *
   * @return  result of saving a one-time password,
   *          true if the saving was successful
   */
  boolean remember(HttpServletRequest request, HttpServletResponse response, String otp);

  /**
   * Checks a one-time password.
   *
   * @param request initial request from the user
   * @return        the result of checking the one-time password,
   *                true if the one-time password is correct for this request
   */
  boolean check(HttpServletRequest request);
}
