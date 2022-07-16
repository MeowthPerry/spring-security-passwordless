package com.github.meperry.security.otp.provider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Specifies that the class is used to verify a one-time password
 *
 * @author  Islam Khabibullin
 */
public interface VerifyProvider {

  boolean verify(HttpServletRequest request, HttpServletResponse response);
}
