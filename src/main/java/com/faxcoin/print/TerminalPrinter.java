package com.faxcoin.print;

import com.faxcoin.communication.Message;
import com.faxcoin.server.PrintService;

public class TerminalPrinter implements PrintService {
  public void printMessage(Message msg) {
    System.out.println(msg.toString());
  }
}
