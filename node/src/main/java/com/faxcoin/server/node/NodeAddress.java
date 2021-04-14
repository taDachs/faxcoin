package com.faxcoin.server.node;

import java.net.URL;

public interface NodeAddress {
  String getUrlAsString();

  URL getUrl();
}