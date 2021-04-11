package com.faxcoin.communication;

public class Address {
  private final String address;
  private final String name;

  public Address(String address, String name) {
    this.address = address;
    this.name = name;
  }

  public String getAddress() {
    return this.address;
  }

  public String getName() {
    return this.name;
  }

  public String toString() {
    return this.name;
  }
}
