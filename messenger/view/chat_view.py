from messenger.messenger import Messenger


class ChatView:
    _messenger: Messenger = None
    _selected_chat: str = ''

    def __init__(self, messenger: Messenger, selected_chat: str):
        self._chat_buffer: list = []
        self._current_history: list = []
        self._color_mappings: dict = {}
        self._colors = ['pink', 'red', 'green', 'blue', 'brown']
        self._has_updated = True
        self._messenger = messenger
        self._selected_chat = selected_chat

    def update_view(self, num_lines: int = 10, width: int = 20):
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
        for msg in self._current_history:
            line = BufferLine()
            clean_name = self._messenger.get_clean_name_from_address(msg.get_sender())[:width - 4]

            if clean_name not in self._color_mappings:
                self._color_mappings[clean_name] = self._colors.pop(0)

            line.add_segment(f'{clean_name}: ', self._color_mappings[clean_name])

            text = msg.get_content()
            for character in text:
                if len(line) > width:
                    chat_history.append(line)
                    line = BufferLine()
                line.add_segment(character)
            chat_history.append(line)

        self._chat_buffer = chat_history[-num_lines:]

        self._has_updated = True

    def has_updated(self) -> bool:
        return self._has_updated

    def get_title(self):
        return self._selected_chat

    def get_buffer(self) -> list:
        return self._chat_buffer


class BufferLine:
    def __init__(self):
        self._segments = []
        self._segment_colors = {}

    def get_segments(self) -> list:
        return self._segments

    def add_segment(self, segment, color: str = None):
        if color:
            self._segment_colors[segment] = color

        self._segments.append(segment)

    def get_segment_color(self, segment) -> str:
        if segment not in self._segment_colors:
            return ''
        return self._segment_colors[segment]

    def __len__(self):
        return len("".join(self._segments))

    def __str__(self):
        return "".join(self._segments)
