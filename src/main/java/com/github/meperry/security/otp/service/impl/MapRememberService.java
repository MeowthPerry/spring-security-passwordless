package com.github.meperry.security.otp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.meperry.security.otp.service.OtpRememberService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MapRememberService implements OtpRememberService {

  private Logger logger = LoggerFactory.getLogger(MapRememberService.class);

  Map<UUID, String> map = new HashMap<>();

  private final ObjectMapper objectMapper = new ObjectMapper();

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
      if (!map.containsKey(getOperationId(request))) {
        return false;
      }
      return map.get(getOperationId(request)).equals(getOtp(request));
    } catch (IOException e) {
      logger.error("Error", e);
      return false;
    }
  }

  private String getOtp(HttpServletRequest request) throws IOException {
    Map<String, Object> result =
        objectMapper.readValue(IOUtils.toString(request.getReader()), HashMap.class);
    return (String) result.get("otp");
  }

  private UUID getOperationId(HttpServletRequest request) throws IOException {
    Map<String, Object> result =
        objectMapper.readValue(IOUtils.toString(request.getReader()), HashMap.class);
    return UUID.fromString((String) result.get("operationId"));
  }
}
