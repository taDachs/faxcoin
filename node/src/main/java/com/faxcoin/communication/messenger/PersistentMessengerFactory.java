package com.faxcoin.communication.messenger;

public class PersistentMessengerFactory implements MessengerFactory {

  @Override
  public Messenger buildMessenger(MessengerAddress messengerAddress) {
    return new PersistentMessenger(messengerAddress);
  }
}
