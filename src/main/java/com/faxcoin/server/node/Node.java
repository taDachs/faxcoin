package com.faxcoin.server.node;

import com.faxcoin.communication.Message;
import com.faxcoin.communication.messenger.MessengerAddress;
import com.faxcoin.server.node.exceptions.InvalidSignatureException;

import java.util.Collection;
import java.util.List;

public interface Node {
  void sendMessage(Message msg) throws InvalidSignatureException;
  void receiveMessage(Message msg) throws InvalidSignatureException;
  NodeAddress getAddress();
  Collection<Node> getNeighbours();
  void registerNeighbour(NodeAddress node);
  void registerMessenger(MessengerAddress messenger);
  List<Message> getMessageQueue(MessengerAddress address);
  void addToGroup(MessengerAddress group, MessengerAddress messengerAddress);
}

