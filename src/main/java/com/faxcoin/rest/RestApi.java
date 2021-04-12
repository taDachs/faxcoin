package com.faxcoin.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faxcoin.communication.Message;
import com.faxcoin.communication.messenger.MessengerAddress;
import com.faxcoin.communication.messenger.NamedMessengerAddress;
import com.faxcoin.rest.representation.MessageRepresentation;
import com.faxcoin.rest.representation.MessengerRepresentation;
import com.faxcoin.rest.representation.RemoteNodeRepresentation;
import com.faxcoin.server.node.Node;
import com.faxcoin.server.node.NodeAddress;
import com.faxcoin.server.node.UrlNodeAddress;
import com.faxcoin.server.node.exceptions.InvalidSignatureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
public class RestApi {
  private final Node node;

  public RestApi(Node node) {
    this.node = node;
  }

  @PostMapping(value = "/receiveMessage", consumes = "application/json")
  public void receiveMessage(@RequestBody String body) {
    ObjectMapper mapper = new ObjectMapper();
    MessageRepresentation params;

    try {
      params = mapper.readValue(body, MessageRepresentation.class);
    } catch (JsonProcessingException e) {
      throw customResponseStatusException(e);
    }

    Message msg = new Message(
        params.getContent(),
        new NamedMessengerAddress(params.getReceiver()),
        new NamedMessengerAddress(params.getSender()),
        params.getSigning(),
        UUID.fromString(params.getId())
    );

    try {
      this.node.receiveMessage(msg);
    } catch (InvalidSignatureException e) {
      throw customResponseStatusException(e);
    }
  }

  @PostMapping(value = "/sendMessage", consumes = "application/json")
  public void sendMessage(@RequestBody String body) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    MessageRepresentation params;

    try {
      params = mapper.readValue(body, MessageRepresentation.class);
    } catch (JsonProcessingException e) {
      throw customResponseStatusException(e);
    }

    Message msg = new Message(
        params.getContent(),
        new NamedMessengerAddress(params.getReceiver()),
        new NamedMessengerAddress(params.getSender()),
        params.getSigning()
    );
    try {
      this.node.sendMessage(msg);
    } catch (InvalidSignatureException e) {
      throw customResponseStatusException(e);
    }
  }

  @PostMapping(value = "/registerNeighbour", consumes = "application/json")
  public void registerNeighbour(@RequestBody String body) {
    ObjectMapper mapper = new ObjectMapper();

    RemoteNodeRepresentation params;
    try {
      params = mapper.readValue(body, RemoteNodeRepresentation.class);
    } catch (JsonProcessingException e) {
      throw customResponseStatusException(e);
    }

    NodeAddress address = new UrlNodeAddress(params.getAddress());
    this.node.registerNeighbour(address);
  }

  @PostMapping(value = "/registerMessenger", consumes = "application/json")
  public void registerMessenger(@RequestBody String body) {
    ObjectMapper mapper = new ObjectMapper();
    MessengerRepresentation params;
    try {
      params = mapper.readValue(body, MessengerRepresentation.class);
    } catch (JsonProcessingException e) {
      throw customResponseStatusException(e);
    }

    MessengerAddress address = new NamedMessengerAddress(params.getAddress());
    this.node.registerMessenger(address);
  }

  @PostMapping(value = "/addToGroup", consumes = "application/json")
  public void addToGroup(@RequestBody String body) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    GroupRegisterRepresentation params = mapper.readValue(body, GroupRegisterRepresentation.class);

    MessengerAddress groupAddress = new NamedMessengerAddress(params.getGroupAddress());
    MessengerAddress messengerAddress = new NamedMessengerAddress(params.getMessengerAddress());
    this.node.addToGroup(groupAddress, messengerAddress);
  }

  @GetMapping(value = "/getMessageQueue", consumes = "application/json", produces = "application/json")
  public List<MessageRepresentation> getMessageQueue(@RequestBody String body) {
    ObjectMapper mapper = new ObjectMapper();

    MessengerRepresentation params;
    try {
      params = mapper.readValue(body, MessengerRepresentation.class);
    } catch (JsonProcessingException e) {
      throw customResponseStatusException(e);
    }

    MessengerAddress address = new NamedMessengerAddress(params.getAddress());
    List<Message> messageQueue = this.node.getMessageQueue(address);

    List<MessageRepresentation> response = new LinkedList<>();

    for (Message msg : messageQueue) {
      response.add(
          new MessageRepresentation(
              msg.getContent(),
              msg.getSender().getAddress(),
              msg.getReceiver().getAddress(),
              msg.getSigning(),
              msg.getId().toString()
          )
      );
    }

    return response;
  }

  @GetMapping(value = "/getNeighbours", consumes = "application/json", produces = "application/json")
  public List<RemoteNodeRepresentation> getNeighbours() {
    Collection<Node> neighbours = this.node.getNeighbours();
    List<RemoteNodeRepresentation> response = new LinkedList<>();

    for (Node node : neighbours) {
      response.add(
          new RemoteNodeRepresentation(node.getAddress().toString())
      );
    }

    return response;
  }

  private ResponseStatusException customResponseStatusException(Throwable cause) {
    HttpErrorCustomResponse errorResponse = HttpErrorCustomResponse.getErrorResponse(cause.getClass());
    return new ResponseStatusException(
            errorResponse.getHttpErrorStatusCode(),
            errorResponse.getHttpErrorTitle(),
            cause
    );
  }
}
