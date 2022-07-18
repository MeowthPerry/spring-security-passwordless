package com.github.meperry.security.otp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.meperry.security.otp.service.OtpRememberService;
import com.github.meperry.security.otp.util.JsonRequestBodyParser;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryRememberService implements OtpRememberService {

  private Logger logger = LoggerFactory.getLogger(InMemoryRememberService.class);

  Map<UUID, String> map = new HashMap<>();

  @Override
  public boolean remember(HttpServletRequest request, HttpServletResponse response, String otp) {
    UUID operationId = UUID.randomUUID();
    map.put(operationId, otp);
    try {
      Map<String, Object> responseBody = new LinkedHashMap<>();
      responseBody.put("operationId", operationId);
      response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    } catch (IOException e) {
      logger.error("Error when writing to the response body", e);
      return false;
    }
    return true;
  }

  @Override
  public boolean check(HttpServletRequest request) {
    try {
      UUID operationId = getOperationId(request);
      String otp = getOtp(request);
      if (!map.containsKey(operationId)) {
        return false;
      }
      if (map.get(operationId).equals(otp)) {
        map.remove(operationId);
        return true;
      }
    } catch (IOException e) {
      logger.error("Error", e);
    }
    return false;
  }

  private String getOtp(HttpServletRequest request) throws IOException {
    return (String) JsonRequestBodyParser.get(request, "otp");
  }

  private UUID getOperationId(HttpServletRequest request) throws IOException {
    return UUID.fromString((String) JsonRequestBodyParser.get(request, "operationId"));
  }
}
