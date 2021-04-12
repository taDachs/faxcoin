package com.faxcoin.server.node;

import com.faxcoin.communication.Group;
import com.faxcoin.communication.messenger.MessengerAddress;
import com.faxcoin.communication.Message;
import com.faxcoin.communication.messenger.Messenger;
import com.faxcoin.communication.messenger.MessengerFactory;
import com.faxcoin.server.node.exceptions.InvalidSignatureException;

import java.util.*;

public class SimpleServerNode implements Node {
  private static final int MAX_HISTORY_SIZE = 500;
  private final Collection<Node> neighbours = new LinkedList<>();
  private final Collection<Messenger> messengers = new LinkedList<>();
  private final Collection<Group> groups = new LinkedList<>();
  private final MessengerFactory messengerFactory;
  private final List<Message> messageHistory = new LinkedList<>();
  private final NodeAddress address;

  public SimpleServerNode(NodeAddress address, MessengerFactory messengerFactory) {
    this.address = address;
    this.messengerFactory = messengerFactory;
  }

  @Override
  public void sendMessage(Message msg) throws InvalidSignatureException {
    if (!msg.isValid()) {
      throw new InvalidSignatureException();
    }

    this.receiveMessage(msg);
  }

  @Override
  public void receiveMessage(Message msg) throws InvalidSignatureException {
    if (!msg.isValid()) {
      throw new InvalidSignatureException();
    }

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

    for (Group group : this.groups) {
      if (group.getAddress().equals(receiver)) {
        group.receiveMessage(msg);
        return;
      }
    }

    sendMessageToNeighbours(msg);
  }

  @Override
  public NodeAddress getAddress() {
    return this.address;
  }

  private void sendMessageToNeighbours(Message msg) throws InvalidSignatureException {
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

  @Override
  public void addToGroup(MessengerAddress groupAddress, MessengerAddress messengerAddress) {
    this.registerMessenger(messengerAddress);
    Messenger messenger = this.getMessenger(messengerAddress);

    Group group = new Group(groupAddress);
    if (this.groups.contains(group)) {
      group = this.getGroup(groupAddress);
      group.addMember(messenger);
      return;
    }

    group.addMember(messenger);
    this.groups.add(group);
  }

  private Group getGroup(MessengerAddress groupAddress) {
    for (Group group : this.groups) {
      if (group.getAddress().equals(groupAddress)) {
        return group;
      }
    }

    return null;
  }

  private Messenger getMessenger(MessengerAddress address) {
    for (Messenger messenger : this.messengers) {
      if (messenger.getAddress().equals(address)) {
        return messenger;
      }
    }

    return null;
  }

}
