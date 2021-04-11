package com.faxcoin.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HttpRestClient {
  public void post(HashMap<String, String> data, String address) {
    String requestBody = "";

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      requestBody = objectMapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    URL url;
    HttpURLConnection con;
    try {
      url = new URL(address);
      con = (HttpURLConnection)  url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      con.setRequestProperty("Accept", "application/json");
      con.setDoOutput(true);
      con.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));
      con.getOutputStream().close();
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      con.getResponseCode();
      con.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
