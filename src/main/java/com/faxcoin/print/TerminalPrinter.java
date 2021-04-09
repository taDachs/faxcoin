package com.faxcoin.print;

import com.faxcoin.server.PrintService;
import com.faxcoin.communication.Message;

public class TerminalPrinter implements PrintService {
    public void printMessage(Message msg) {
        System.out.println(msg.toString());
    }
}
