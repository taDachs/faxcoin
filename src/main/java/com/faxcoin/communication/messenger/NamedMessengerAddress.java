package com.faxcoin.communication.messenger;

public class NamedMessengerAddress extends MessengerAddress {
  private final String name;

  public NamedMessengerAddress(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    NamedMessengerAddress other = (NamedMessengerAddress) o;
    return other.name.equals(this.name);
  }

  @Override
  public String getAddress() {
    return this.name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
