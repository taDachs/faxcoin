package com.faxcoin.communication;

import com.faxcoin.communication.authentification.EncrpytionHelper;
import com.faxcoin.communication.messenger.MessengerAddress;

import java.util.Objects;
import java.util.UUID;

public class Message {
  private final String content;
  private final MessengerAddress receiver;
  private final MessengerAddress sender;
  private final String signing;
  private final UUID id;

  public Message(String content, MessengerAddress receiver, MessengerAddress sender, String signing) {
    this.content = content;
    this.receiver = receiver;
    this.sender = sender;
    this.signing = signing;
    this.id = UUID.randomUUID();
  }

  public Message(String content, MessengerAddress receiver, MessengerAddress sender, String signing, UUID id) {
    this.content = content;
    this.receiver = receiver;
    this.sender = sender;
    this.signing = signing;
    this.id = id;
  }

  public String getContent() {
    return this.content;
  }

  public MessengerAddress getReceiver() {
    return this.receiver;
  }

  public MessengerAddress getSender() {
    return this.sender;
  }

  public String getSigning() {
    return this.signing;
  }

  public UUID getId() {
    return this.id;
  }

  public String toString() {
    String template = "Sender: %s\nReceiver: %s\nID: %s\ncontent: %s";
    return String.format(template, this.sender, this.receiver, this.id.toString(), this.content);
  }

  public boolean isValid() {
    return EncrpytionHelper.verifySignature(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Message message = (Message) o;
    return id.equals(message.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
