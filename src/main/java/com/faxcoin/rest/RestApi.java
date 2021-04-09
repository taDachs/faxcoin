package com.faxcoin.rest;

import com.faxcoin.communication.Address;
import com.faxcoin.communication.Message;
import com.faxcoin.server.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApi {
    @Autowired
    Node node;


    @PostMapping("/sendMessage")
    public void sendMessage(@RequestParam(value = "content") String content,
                               @RequestParam(value = "sender") String sender) {
        Message msg = new Message(content, this.node.getAddress(), new Address(sender));
        node.receiveMessage(msg);
    }
}
