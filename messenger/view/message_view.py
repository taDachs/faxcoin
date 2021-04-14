from messenger.text_box import TextBox
from view.buffer_line import BufferLine


class MessageView:
    _old_content = ''
    _old_cursor = -1
    _has_updated = True

    def __init__(self, text_box: TextBox):
        self._buffer: list = []
        self._text_box: TextBox = text_box

    def has_updated(self) -> bool:
        return self._has_updated

    def update_view(self, num_lines: int = 10, width: int = 20):
        new_content = self._text_box.get_buffer()
        cursor = self._text_box.get_cursor_position()
        if new_content == self._old_content and cursor == self._old_cursor:
            self._has_updated = False
            return

        self._old_content = new_content
        self._old_cursor = cursor

        new_content += ' '

        new_buffer = []
        line = BufferLine()
        line.add_segment('Send Message: ', color_id=0, bold=True)
        for i, character in enumerate(new_content):
            if len(line) > width:
                new_buffer.append(line)
                line = BufferLine()
            line.add_segment(character, marked=(i == cursor))
        new_buffer.append(line)

        self._buffer = new_buffer[-num_lines:]
        self._has_updated = True

    def get_buffer(self) -> list:
        return self._buffer
