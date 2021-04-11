package com.faxcoin.communication.messenger;

public interface MessengerFactory {
  Messenger buildMessenger(MessengerAddress messengerAddress);
}
