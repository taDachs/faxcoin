package com.faxcoin.server;

import com.faxcoin.communication.Message;

public interface Node {
    public void sendMessage(Message msg);
    public void receiveMessage(Message msg);
}
