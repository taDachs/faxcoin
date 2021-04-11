package com.faxcoin.communication.messenger;

import com.faxcoin.communication.Message;

import java.util.LinkedList;
import java.util.List;

public class PersistentMessenger extends Messenger {
  private final MessengerAddress messengerAddress;
  private List<Message> queue = new LinkedList<>();
  private List<Message> history = new LinkedList<>();

  public PersistentMessenger(MessengerAddress messengerAddress) {
    this.messengerAddress = messengerAddress;
  }

  @Override
  public MessengerAddress getAddress() {
    return this.messengerAddress;
  }

  @Override
  public void addMessage(Message msg) {
    this.queue.add(msg);
    this.history.add(msg);
  }

  @Override
  public List<Message> getMessageQueue() {
    List<Message> returnQueue = this.queue;
    this.queue = new LinkedList<>();
    return returnQueue;
  }
}
