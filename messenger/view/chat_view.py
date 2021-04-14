from messenger.messenger import Messenger
from view.buffer_line import BufferLine


class ChatView:
    _messenger: Messenger = None
    _selected_chat: str = ''

    def __init__(self, messenger: Messenger, selected_chat: str):
        self._chat_buffer: list = []
        self._current_history: list = []
        self._color_mappings: dict = {}
        self._last_color_id = 0
        self._has_updated = True
        self._messenger = messenger
        self._selected_chat = selected_chat
        self._shift = 0

    def increase_shift(self):
        self._shift = min(self._shift + 1, len(self._chat_buffer))

    def decrease_shift(self):
        self._shift = max(self._shift - 1, 0)

    def update_view(self, width: int = 20):
        chats = self._messenger.get_chats()

        selected_chat = None
        for chat in chats:
            if chat.get_name() == self._selected_chat:
                selected_chat = chat

        if selected_chat is None:
            self._has_updated = False
            return

        if len(self._current_history) == len(selected_chat.get_messages()):
            self._has_updated = False
            return

        self._current_history = selected_chat.get_messages()[:]

        chat_history = []
        last_msg = None
        for msg in self._current_history:
            is_own_message = msg.get_sender() == self._messenger.get_address()
            alignment = 'right' if is_own_message else 'left'
            if (last_msg is None) or (last_msg.get_sender() != msg.get_sender()):
                if last_msg is not None:
                    empty_line = BufferLine()
                    empty_line.add_segment('')
                    chat_history.append(empty_line)
                name_line = BufferLine(alignment=alignment)
                clean_name = 'You'
                if not is_own_message:
                    clean_name = self._messenger.get_clean_name_from_address(msg.get_sender())[:width - 4]
                if clean_name not in self._color_mappings:
                    self._color_mappings[clean_name] = self._last_color_id
                    self._last_color_id += 1
                name_line.add_segment(clean_name, self._color_mappings[clean_name], bold=True)
                chat_history.append(name_line)

            last_msg = msg

            line = BufferLine(alignment=alignment)
            text = msg.get_content()
            for character in text:
                if len(line) > width:
                    chat_history.append(line)
                    line = BufferLine(alignment=alignment)
                line.add_segment(character)
            chat_history.append(line)

        self._chat_buffer = chat_history

        self._has_updated = True

    def has_updated(self) -> bool:
        return self._has_updated

    def get_title(self):
        return self._selected_chat

    def get_buffer(self, num_lines: int) -> list:
        self._shift = min(max(len(self._chat_buffer) - num_lines, 0), self._shift)
        output = self._chat_buffer[-num_lines - self._shift:len(self._chat_buffer) - self._shift]
        if len(output) < num_lines:
            return self._chat_buffer
        return output
