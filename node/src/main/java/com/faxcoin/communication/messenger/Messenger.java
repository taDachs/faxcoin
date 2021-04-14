package com.faxcoin.communication.messenger;

import com.faxcoin.communication.Message;

import java.util.List;

public abstract class Messenger {
  public abstract MessengerAddress getAddress();

  public abstract void addMessage(Message msg);

  public abstract List<Message> getMessageQueue();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Messenger other = (Messenger) o;
    return this.getAddress() == other.getAddress();
  }
}
