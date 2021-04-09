package com.faxcoin.server;

import com.faxcoin.communication.Address;
import com.faxcoin.communication.Message;

public interface Node {
    public void sendMessage(Message msg);
    public void receiveMessage(Message msg);
    public Address getAddress();
}
