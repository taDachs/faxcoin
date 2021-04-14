class Segment:
    def __init__(self, content: str, color_id: int = -1, bold: bool = False, marked: bool = False, italic: bool = False):
        self._content = content
        self._color_id = color_id
        self._bold = bold
        self._marked = marked
        self._italic = italic

    def __str__(self):
        return self._content

    def get_content(self) -> str:
        return self._content

    def get_color_id(self) -> int:
        return self._color_id

    def is_bold(self) -> bool:
        return self._bold

    def is_marked(self) -> bool:
        return self._marked

    def is_italic(self) -> bool:
        return self._italic


class BufferLine:
    def __init__(self, alignment: str = 'left'):
        self._segments = []
        self._alignment = alignment

    def get_segments(self) -> list:
        return self._segments

    def add_segment(self, content, color_id: int = -1, bold: bool = False, marked: bool = False):
        segment = Segment(content, color_id, bold, marked)
        self._segments.append(segment)

    def get_alignment(self) -> str:
        return self._alignment

    def __len__(self):
        return len("".join(map(str, self._segments)))

    def __str__(self):
        return "".join(map(str, self._segments))

