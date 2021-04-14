from messenger.address_book import AddressBook
from messenger.chat import PersonalChat, GroupChat
from messenger.message import Message
from messenger.signator import Signator
from rest.node import Node


class Messenger:
    _chats: dict = {}
    _address_book: AddressBook = None

    def __init__(self, signator: Signator, node: Node, address_book: AddressBook = None):
        self._signator = signator
        self._address: str = signator.get_clean_pub_key()
        self._node = node
        self._node.register_messenger(self._address)
        self._address_book = address_book if address_book else AddressBook()

    def get_address(self) -> str:
        return self._address

    def send_message(self, receiver: str, content: str):
        msg = Message(
            receiver=receiver,
            sender=self._address,
            content=content,
            signature=self._signator.get_signature_for_content(content)
        )

        chat = self._get_or_create_chat(msg)
        chat.add_message(msg)
        self._node.send_message(msg)

    def update_messages(self):
        queue = self._node.get_message_queue(self._address)
        for msg in queue:
            chat = self._get_or_create_chat(msg)
            chat.add_message(msg)

    def _get_or_create_chat(self, msg):
        sender = msg.get_sender()
        receiver = msg.get_receiver()
        if receiver == self._address:
            if sender not in self._chats:
                self._chats[sender] = PersonalChat()
            return self._chats[sender]
        else:
            if receiver not in self._chats:
                self._chats[receiver] = GroupChat()
            return self._chats[receiver]

    def get_chats(self) -> list:
        return list(self._chats.values())

    def get_clean_name_from_address(self, address: str):
        return self._address_book.get_name(address)
