package com.faxcoin.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRepresentation {
  @JsonProperty("content")
  private String content;

  @JsonProperty("sender")
  private String sender;

  @JsonProperty("receiver")
  private String receiver;

  @JsonProperty("id")
  private String id;

  public MessageRepresentation() {
  }

  public MessageRepresentation(String content, String sender, String receiver, String id) {
    this.content = content;
    this.sender = sender;
    this.receiver = receiver;
    this.id = id;
  }

  public String getContent() {
    return this.content;
  }

  public String getSender() {
    return this.sender;
  }

  public String getReceiver() {
    return this.receiver;
  }

  public String getId() {
    return this.id;
  }
}
