package com.github.meperry.security.otp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonRequestBodyParser {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static Object get(HttpServletRequest request, String key) throws IOException {
    String requestBody = IOUtils.toString(request.getReader());
    Map<String, Object> result =
        objectMapper.readValue(requestBody, HashMap.class);
    return result.get(key);
  }
}
