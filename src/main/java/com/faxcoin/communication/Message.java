package com.faxcoin.communication;

public class Message {
    private final String content;
    private final Address receiver;
    private final Address sender;

    public Message(String content, Address receiver, Address sender) {
        this.content = content;
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getContent() {
        return this.content;
    }

    public Address getReceiver() {
        return this.receiver;
    }

    public Address getSender() {
        return this.sender;
    }

    public String toString() {
        String template = "Sender: %s \n Receiver: %s \n content: %s";
        return String.format(template, this.sender, this.receiver, this.content);
    }
}
