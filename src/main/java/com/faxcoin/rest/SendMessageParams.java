package com.faxcoin.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageParams {
  @JsonProperty("content")
  private String content;
  @JsonProperty("receiver")
  private String receiver;

  public String getContent() {
    return this.content;
  }

  public String getReceiver() {
    return this.receiver;
  }
}
