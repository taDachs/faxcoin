from abc import ABC, abstractmethod

from messenger.message import Message


class Chat(ABC):
    _messages: list = []

    def add_message(self, msg: Message):
        self._messages.append(msg)

    def get_messages(self) -> list:
        return self._messages

    @abstractmethod
    def get_name(self) -> str:
        pass


class PersonalChat(Chat):
    def get_name(self) -> str:
        last_message = self._messages[-1]
        return last_message.get_sender()


class GroupChat(Chat):
    def get_name(self) -> str:
        last_message = self._messages[-1]
        return last_message.get_receiver()
