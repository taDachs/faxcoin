#!/usr/bin/env python3
import sys
import argparse

from messenger.address_book import AddressBook
from messenger.messenger import Messenger
from messenger.signator import Signator
from messenger.text_box import TextBox
from rest.node import Node
from view.blessed_presenter import BlessedPresenter
from view.chat_view import ChatView

import blessed

from view.message_view import MessageView

term = blessed.Terminal()

colors = [term.deeppink, term.red, term.blue, term.brown]

CHAT_WIDTH = term.width
CHAT_HEIGHT = term.height - 4


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
    chat_view = ChatView(messenger, 'test')
    text_box = TextBox()
    message_view = MessageView(text_box)

    presenter = BlessedPresenter(message_view, chat_view)

    print(term.clear)
    while True:
        with term.cbreak():
            key = term.inkey(0.3)
            if not key.is_sequence:
                if len(key) != 0:
                    text_box.add_char(key)
            elif key.name == 'KEY_ENTER':
                if len(text_box.get_buffer()) != 0:
                    message_content = text_box.get_buffer()
                    text_box.clear_buffer()
                    messenger.send_message('test', message_content)
            elif key.name == 'KEY_BACKSPACE':
                text_box.delete_character()
            elif key.name == 'KEY_LEFT':
                text_box.move_cursor_left()
            elif key.name == 'KEY_RIGHT':
                text_box.move_cursor_right()
            elif key.name == 'KEY_UP':
                chat_view.increase_shift()
            elif key.name == 'KEY_DOWN':
                chat_view.decrease_shift()
            elif key.name == 'KEY_ESCAPE':
                break

        messenger.update_messages()
        presenter.draw()

    # address_book.save(address_file)
    sys.exit(0)


if __name__ == '__main__':
    with term.fullscreen() and term.hidden_cursor():
        main()
