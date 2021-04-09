package com.faxcoin.server;

import com.faxcoin.communication.Address;
import com.faxcoin.communication.Message;

public interface Node {
  void sendMessage(Message msg);

  void receiveMessage(Message msg);

  Address getAddress();
}
