package com.faxcoin.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMessageParams {
  @JsonProperty("content")
  private String content;

  @JsonProperty("sender")
  private String sender;

  public String getContent() {
    return this.content;
  }

  public String getSender() {
    return this.sender;
  }
}
