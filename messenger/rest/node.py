import requests

from messenger.message import Message


class Node:
    def __init__(self, address: str):
        self._address = address

    def send_message(self, msg: Message):
        request_body = {
            'content': msg.get_content(),
            'sender': msg.get_sender(),
            'receiver': msg.get_receiver(),
            'signing': msg.get_signature()
        }

        requests.post(self._address + '/sendMessage', json=request_body)

    def register_messenger(self, messenger_address: str):
        request_body = {
            'address': messenger_address
        }

        requests.post(self._address + '/registerMessenger', json=request_body)

    def get_message_queue(self, messenger_address: str) -> list:
        request_body = {
            'address': messenger_address
        }

        resp = requests.get(self._address + '/getMessageQueue', json=request_body)
        queue = []
        for msg in resp.json():
            queue.append(Message.from_dict(msg))

        return queue

    def get_address(self) -> str:
        return self._address

    def set_address(self, address: str):
        self._address = address
