from base64 import b64encode

from Crypto.Hash import SHA256
from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5


# yes darius, this word exists: https://www.lexico.com/en/definition/signator
class Signator:
    def __init__(self, key_path: str):
        with open(key_path, 'r') as f:
            self._private_key = RSA.importKey(f.read())
            self._pub_key = self._private_key.publickey()

            pub_key_string = self._pub_key.exportKey().decode('utf-8')
            pub_key_string = pub_key_string.replace('\n', '')
            pub_key_string = pub_key_string.replace('', '')
            pub_key_string = pub_key_string.replace('-----BEGIN PUBLIC KEY-----', '')
            pub_key_string = pub_key_string.replace('-----END PUBLIC KEY-----', '')

            self._clean_pub_key = pub_key_string

    def get_signature_for_content(self, content: str) -> str:
        signer = PKCS1_v1_5.new(self._private_key)
        content_binary = content.encode('utf-8')
        digest = SHA256.new()
        digest.update(content_binary)
        sig = signer.sign(digest)
        return b64encode(sig).decode('utf-8')

    def get_clean_pub_key(self) -> str:
        return self._clean_pub_key
