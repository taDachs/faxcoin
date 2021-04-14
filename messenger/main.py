#!/usr/bin/env python3
import sys
import argparse

from messenger.address_book import AddressBook
from messenger.messenger import Messenger
from messenger.signator import Signator
from messenger.text_box import TextBox
from rest.node import Node
from view.chat_view import ChatView

import blessed

term = blessed.Terminal()

colors = {
    'pink': term.deeppink,
    'red': term.red,
    'green': term.blue,
    'brown': term.brown
}


def main():
    parser = argparse.ArgumentParser(description='Messenger for faxcoin')
    parser.add_argument('-n', '--node', required=True, type=str, help='address of node')
    parser.add_argument('-c', '--contacts', default=None, type=str, help='address book json file')
    parser.add_argument('-k', '--key', required=True, type=str, help='private key file')

    args = parser.parse_args()
    address_file = args.contacts
    private_key_file = args.key
    url = args.node

    node = Node(url)
    signator = Signator(private_key_file)
    if address_file:
        address_book = AddressBook.from_json(address_file)
    else:
        address_book = None

    messenger = Messenger(signator, node, address_book)
    view = ChatView(messenger, 'test')
    text_box = TextBox()

    print(term.clear)
    while True:
        with term.cbreak():
            key = term.inkey(0.3)
            if not key.is_sequence:
                if len(key) != 0:
                    text_box.add_char(key)
            elif key.name == 'KEY_ENTER':
                message_content = text_box.get_buffer()
                text_box.clear_buffer()
                messenger.send_message('test', message_content)
            elif key.name == 'KEY_BACKSPACE':
                text_box.delete_character()
            elif key.name == 'KEY_LEFT':
                text_box.move_cursor_left()
            elif key.name == 'KEY_RIGHT':
                text_box.move_cursor_right()
            elif key.name == 'KEY_ESCAPE':
                break

        messenger.update_messages()
        view.update_view(term.height - 4, term.width - 1)
        if view.has_updated():
            print(term.clear)
            chat_buffer = view.get_buffer()
            lines = []

            for line in chat_buffer:
                text = ''
                for segment in line.get_segments():
                    color = line.get_segment_color(segment)
                    if color in colors:
                        text += colors[color]
                    text += segment
                    text += term.normal
                lines.append(text)

            print('\n'.join(lines))

        buffer = text_box.get_buffer()

        print(term.move_xy(0, term.height - 2) + term.blue_bold('Send Message: ') + term.clear_eol + buffer)
        print(
            term.move_xy(text_box.get_cursor_position() + len('Send Message: '), term.height - 2)
            + term.on_darkolivegreen(text_box.get_character_under_cursor())
        )

    # address_book.save(address_file)
    sys.exit(0)


if __name__ == '__main__':
    with term.fullscreen() and term.hidden_cursor():
        main()
