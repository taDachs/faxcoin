package com.faxcoin.communication;

import com.faxcoin.communication.messenger.Messenger;
import com.faxcoin.communication.messenger.MessengerAddress;

import java.util.Collection;
import java.util.LinkedList;

public class Group {
  private final MessengerAddress address;
  private final Collection<Messenger> members = new LinkedList<>();

  public Group(MessengerAddress address) {
    this.address = address;
  }

  public MessengerAddress getAddress() {
    return this.address;
  }

  public void receiveMessage(Message msg) {
    for (Messenger member : this.members) {
      if (!msg.getSender().equals(member.getAddress())) {
        member.addMessage(msg);
      }
    }
  }

  public void addMember(Messenger member) {
    if (this.members.contains(member)) {
      return;
    }

    this.members.add(member);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    Group other = (Group) o;
    return this.address.equals(other.address);
  }
}
