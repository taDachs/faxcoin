import json


class AddressBook:
    def __init__(self, address_to_name: dict = None):
        self._name_to_address: dict = {}
        self._address_to_name: dict = {}
        if address_to_name is not None:
            self._address_to_name = address_to_name

    @staticmethod
    def from_json(path: str):
        with open(path, 'r') as f:
            address_to_name = json.load(f)
        return AddressBook(address_to_name)

    def add_contact(self, name: str, address: str):
        self._name_to_address[name] = address
        self._address_to_name[address] = name

    def get_name(self, address: str) -> str:
        if address not in self._address_to_name:
            return address

        return self._address_to_name[address]

    def save(self, path: str):
        with open(path, 'w+') as f:
            json.dump(self._address_to_name, f)
