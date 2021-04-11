package com.faxcoin.server.node;

import com.faxcoin.communication.messenger.MessengerAddress;
import com.faxcoin.communication.Message;
import com.faxcoin.communication.messenger.Messenger;
import com.faxcoin.communication.messenger.MessengerFactory;
import com.faxcoin.server.HttpRestClient;

import java.util.*;

public class SimpleServerNode implements Node {
  private static final int MAX_HISTORY_SIZE = 500;
  private final Collection<Node> neighbours = new LinkedList<>();
  private final Collection<Messenger> messengers = new LinkedList<>();
  private HttpRestClient client = new HttpRestClient();
  private MessengerFactory messengerFactory;
  private List<Message> messageHistory = new LinkedList<>();
  private NodeAddress address;

  public SimpleServerNode(NodeAddress address, MessengerFactory messengerFactory) {
    this.address = address;
    this.messengerFactory = messengerFactory;
  }

  @Override
  public void sendMessage(Message msg) {
    this.receiveMessage(msg);
  }

  @Override
  public void receiveMessage(Message msg) {
    if (this.messageHistory.contains(msg)) {
      return;
    }

    this.messageHistory.add(msg);
    if (this.messageHistory.size() > MAX_HISTORY_SIZE) {
      this.messageHistory.remove(0);
    }

    MessengerAddress receiver = msg.getReceiver();
    for (Messenger messenger : this.messengers) {
      if (messenger.getAddress().equals(receiver)) {
        messenger.addMessage(msg);
        return;
      }
    }

    sendMessageToNeighbours(msg);
  }

  @Override
  public NodeAddress getAddress() {
    return this.address;
  }

  private void sendMessageToNeighbours(Message msg) {
    for (Node neighbour : this.neighbours) {
      neighbour.receiveMessage(msg);
    }
  }

  @Override
  public Collection<Node> getNeighbours() {
    return this.neighbours;
  }

  @Override
  public void registerNeighbour(NodeAddress nodeAddress) {
    Node newNeighbour = new RemoteNode(nodeAddress);
    if (this.neighbours.contains(newNeighbour)) {
      return;
    }
    this.neighbours.add(new RemoteNode(nodeAddress));
    newNeighbour.registerNeighbour(this.address);
  }

  @Override
  public void registerMessenger(MessengerAddress address) {
    Messenger messenger = this.messengerFactory.buildMessenger(address);
    if (this.messengers.contains(messenger)) {
      return;
    }
    this.messengers.add(messenger);
  }

  @Override
  public List<Message> getMessageQueue(MessengerAddress messengerAddress) {
    for (Messenger messenger : this.messengers) {
      if (messenger.getAddress().equals(messengerAddress)) {
        return messenger.getMessageQueue();
      }
    }
    return new LinkedList<>();
  }
}
