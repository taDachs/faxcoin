package com.faxcoin.server.node;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlNodeAddress implements NodeAddress {
  private final String address;

  public UrlNodeAddress(String address) {
    this.address = address;
  }


  @Override
  public String getUrlAsString() {
    return this.address;
  }

  @Override
  public URL getUrl() {
    try {
      return new URL(this.address);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    UrlNodeAddress other = (UrlNodeAddress) o;
    return other.address.equals(this.address);
  }
}
