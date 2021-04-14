package com.faxcoin.communication.messenger;

import com.faxcoin.communication.Message;

import java.util.List;

public class TerminalMessenger extends Messenger {
  private MessengerAddress address;

  public TerminalMessenger(MessengerAddress address) {
    this.address = address;
  }

  @Override
  public MessengerAddress getAddress() {
    return this.address;
  }

  @Override
  public void addMessage(Message msg) {
    System.out.println(msg.toString());
  }

  @Override
  public List<Message> getMessageQueue() {
    return null;
  }
}
