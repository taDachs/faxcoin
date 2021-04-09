package com.faxcoin.server;

import com.faxcoin.communication.Message;

public class MockPrintService implements PrintService {
    private String printedString;

    public void printMessage(Message msg) {
        this.printedString = msg.getContent();
    }

    public String getPrintedString() {
        return this.printedString;
    }
}
