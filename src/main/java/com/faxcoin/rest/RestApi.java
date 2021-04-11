package com.faxcoin.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faxcoin.communication.Address;
import com.faxcoin.communication.Message;
import com.faxcoin.server.Node;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RestApi {
  private final Node node;

  public RestApi(Node node) {
    this.node = node;
  }

  @PostMapping(value = "/getMessage", consumes = "application/json")
  public void getMessage(@RequestBody String body, HttpServletRequest request) throws JsonProcessingException {
    System.out.println(body);
    ObjectMapper mapper = new ObjectMapper();
    GetMessageParams params = mapper.readValue(body, GetMessageParams.class);
    Message msg = new Message(
        params.getContent(),
        this.node.getAddress(),
        new Address(request.getRemoteAddr(), params.getSender())
    );
    node.receiveMessage(msg);
  }

  @PostMapping(value = "/sendMessage", consumes = "application/json")
  public void sendMessage(@RequestBody String body) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    SendMessageParams params = mapper.readValue(body, SendMessageParams.class);
    Message msg = new Message(
        params.getContent(),
        new Address(params.getReceiver(), ""),
        this.node.getAddress()
    );
    node.sendMessage(msg);
  }
}
