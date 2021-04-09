package com.faxcoin.server;

import static org.junit.jupiter.api.Assertions.*;

import com.faxcoin.communication.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleServerNodeTest {
    MockPrintService printService;
    Node node;
    @BeforeEach
    public void setup() {
        printService = new MockPrintService();
        node = new SimpleServerNode(null, printService);

    }

    @Test
    public void testReceiveMessage() {
        String testString = "test";
        Message msg = new Message(testString, null, null);
        this.node.receiveMessage(msg);
        System.out.println(testString);
        assertEquals(this.printService.getPrintedString(), testString);
    }
}