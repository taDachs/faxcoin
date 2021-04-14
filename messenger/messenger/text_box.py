class TextBox:
    _cursor = 0

    def __init__(self):
        self._buffer = []

    def add_char(self, character: str):
        self._buffer.insert(self._cursor, character)
        self._cursor += 1

    def get_buffer(self) -> str:
        return ''.join(self._buffer)

    def get_cursor_position(self) -> int:
        return self._cursor

    def delete_character(self):
        if len(self._buffer) == 0 or self._cursor == 0:
            return
        self._buffer.pop(self._cursor - 1)
        self._cursor -= 1

    def clear_buffer(self):
        self._buffer.clear()
        self._cursor = 0

    def move_cursor_right(self):
        self._cursor = min(len(self._buffer), self._cursor + 1)

    def move_cursor_left(self):
        self._cursor = max(0, self._cursor - 1)

    def get_character_under_cursor(self) -> str:
        if self._cursor >= len(self._buffer):
            return ' '
        return self._buffer[self._cursor]
