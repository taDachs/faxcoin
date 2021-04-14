package com.faxcoin.communication.messenger;

public class TerminalMessengerFactory implements MessengerFactory {

  @Override
  public Messenger buildMessenger(MessengerAddress messengerAddress) {
    return new TerminalMessenger(messengerAddress);
  }
}
