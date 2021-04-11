package com.faxcoin.server.node;

import com.faxcoin.communication.Message;
import com.faxcoin.communication.messenger.MessengerAddress;
import com.faxcoin.server.HttpRestClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class RemoteNode implements Node {
  NodeAddress address;
  HttpRestClient client = new HttpRestClient();
  public RemoteNode(NodeAddress address) {
    this.address = address;
  }

  @Override
  public void sendMessage(Message msg) {
    HashMap<String, String> data = new HashMap<>();
    data.put("sender", msg.getSender().toString());
    data.put("receiver", msg.getReceiver().toString());
    data.put("content", msg.getContent());

    String address = String.format("%s/sendMessage", this.address.getUrlAsString());
    this.client.post(data, address);
  }

  @Override
  public void receiveMessage(Message msg) {
    HashMap<String, String> data = new HashMap<>();
    data.put("id", msg.getId().toString());
    data.put("sender", msg.getSender().toString());
    data.put("receiver", msg.getReceiver().toString());
    data.put("content", msg.getContent());

    String address = String.format("%s/receiveMessage", this.address.getUrlAsString());
    this.client.post(data, address);
  }

  @Override
  public NodeAddress getAddress() {
    return this.address;
  }

  @Override
  public Collection<Node> getNeighbours() {
    return null;
  }

  @Override
  public void registerNeighbour(NodeAddress node) {
    HashMap<String, String> data = new HashMap<>();
    data.put("address", node.getUrlAsString());

    String address = String.format("%s/registerNeighbour", this.address.getUrlAsString());
    this.client.post(data, address);
  }

  @Override
  public void registerMessenger(MessengerAddress messenger) {
  }

  @Override
  public List<Message> getMessageQueue(MessengerAddress address) {
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    RemoteNode other = (RemoteNode) o;
    return this.address.equals(other.address);
  }
}
