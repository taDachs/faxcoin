class Message:
    def __init__(self, sender: str, receiver: str, content: str, signature: str):
        self._sender = sender
        self._receiver = receiver
        self._content = content
        self._signature = signature

    @staticmethod
    def from_dict(msg: dict):
        return Message(
            msg['sender'],
            msg['receiver'],
            msg['content'],
            msg['signing']
        )

    def get_sender(self) -> str:
        return self._sender

    def get_receiver(self) -> str:
        return self._receiver

    def get_content(self) -> str:
        return self._content

    def get_signature(self) -> str:
        return self._signature

    def __str__(self):
        return f'From: {self._sender}\nTo: {self._receiver}\nContent: {self._content}'
