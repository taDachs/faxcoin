package com.faxcoin.server;

import com.faxcoin.communication.Address;
import com.faxcoin.communication.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleServerNode implements Node {
  private final PrintService printService;
  private final Address address;
  private final List<Message> messages = new ArrayList<>();
  private final HttpRestClient client = new HttpRestClient();

  public SimpleServerNode(Address address, PrintService printService) {
    this.address = address;
    this.printService = printService;
  }

  public void sendMessage(Message msg) {
    HashMap<String, String> values = new HashMap<>() {{
          put("content", msg.getContent());
          put("sender", msg.getSender().getName());
        }
      };

    String address = String.format("http://%s/getMessage", msg.getReceiver().getAddress());
    client.sendMessage(values, address);
  }

  public void receiveMessage(Message msg) {
    this.printService.printMessage(msg);
    messages.add(msg);
  }

  public Address getAddress() {
    return this.address;
  }
}
