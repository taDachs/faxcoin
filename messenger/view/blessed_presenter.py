import blessed

from view.chat_view import ChatView
from view.message_view import MessageView


class BlessedPresenter:
    _term = blessed.Terminal()
    _CHAT_WIDTH = _term.width
    _MESSAGE_HEIGHT = 5
    _CHAT_HEIGHT = _term.height - _MESSAGE_HEIGHT - 2
    _colors = [_term.deeppink, _term.red, _term.blue, _term.brown]

    _line_color = _term.on_yellow

    def __init__(self, message_box: MessageView, chat: ChatView):
        self._message_box = message_box
        self._chat = chat

    def set_chat(self, chat: ChatView):
        self._chat = chat

    def draw(self):
        self._draw_chat()
        self._vline(self._term.height - self._MESSAGE_HEIGHT - 1)
        self._draw_message_box()

    def _vline(self, row):
        space = self._term.width * ' '
        print(self._term.move_xy(0, row) + self._line_color + space + self._term.normal, end='')

    def _hline(self, col):
        pass


    def _draw_chat(self):
        print(self._term.clear)
        self._chat.update_view(self._CHAT_WIDTH // 2)
        chat_buffer = self._chat.get_buffer(self._CHAT_HEIGHT)
        lines = []

        for line in chat_buffer:
            text = ''
            for segment in line.get_segments():
                color = segment.get_color_id()
                if color >= 0:
                    text += self._colors[color]
                if segment.is_bold():
                    text += self._term.bold
                text += segment.get_content()
                text += self._term.normal
            if line.get_alignment() == 'right':
                num_spaces = self._CHAT_WIDTH - len(line)
                text = num_spaces * ' ' + text
            lines.append(text)

        print('\n'.join(lines))

    def _draw_message_box(self):
        self._message_box.update_view(self._MESSAGE_HEIGHT, self._CHAT_WIDTH - 1)
        lines = []
        for line in self._message_box.get_buffer():
            text = ''
            for segment in line.get_segments():
                color = segment.get_color_id()
                if color >= 0:
                    text += self._colors[color]
                if segment.is_bold():
                    text += self._term.bold
                if segment.is_marked():
                    text += self._term.on_darkolivegreen
                text += segment.get_content()
                text += self._term.normal
            lines.append(text)

        for i, line in enumerate(lines):
            print(self._term.move_xy(0, self._term.height - self._MESSAGE_HEIGHT + i) + self._term.clear_eol + line)
