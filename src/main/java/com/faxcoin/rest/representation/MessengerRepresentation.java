package com.faxcoin.rest.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessengerRepresentation {
  @JsonProperty("address")
  private String address;

  public String getAddress() {
    return this.address;
  }
}
