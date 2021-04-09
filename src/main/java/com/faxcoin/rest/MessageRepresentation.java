package com.faxcoin.rest;

public class MessageRepresentation {
  private final String content;
  private final String sender;
  private final String receiver;

  public MessageRepresentation(String content, String sender, String receiver) {
    this.content = content;
    this.sender = sender;
    this.receiver = receiver;
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
}
