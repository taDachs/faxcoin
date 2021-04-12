package com.faxcoin.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupRegisterRepresentation {
  @JsonProperty("groupAddress")
  private String groupAddress;

  @JsonProperty("messengerAddress")
  private String messengerAddress;

  public String getGroupAddress() {
    return this.groupAddress;
  }

  public String getMessengerAddress() {
    return this.messengerAddress;
  }
}
