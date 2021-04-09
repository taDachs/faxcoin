package com.faxcoin.server;

import com.faxcoin.communication.Address;
import com.faxcoin.communication.Message;

import java.util.ArrayList;
import java.util.List;

public class SimpleServerNode implements Node {
    private PrintService printService;
    private Address address;
    private List<Message> messages;

    public SimpleServerNode(Address address, PrintService printService) {
        this.address = address;
        this.printService = printService;
        this.messages = new ArrayList<Message>();
    }

    public void sendMessage(Message msg) {

    }

    public void receiveMessage(Message msg) {
        this.printService.printMessage(msg);

    }
}
