package com.faxcoin.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteNodeRepresentation {
  public RemoteNodeRepresentation() {
  }

  public RemoteNodeRepresentation(String address) {
    this.address = address;
  }

  @JsonProperty("address")
  private String address;

  public String getAddress() {
    return this.address;
  }
}
