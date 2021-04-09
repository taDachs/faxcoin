package com.faxcoin.server;

import com.faxcoin.communication.Message;

public interface PrintService {
    void printMessage(Message msg);
}
