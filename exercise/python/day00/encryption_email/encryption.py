from base64 import b64encode, b64decode

from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes


class Encryption:
    def __init__(self, key: str, iv: str):
        self.key = b64decode(key)
        self.iv = b64decode(iv)

    def encrypt(self, plain_text: str) -> str:
        plain_bytes = plain_text.encode('utf-8')
        cipher = Cipher(algorithms.AES(self.key), modes.CBC(self.iv), backend=default_backend())
        encryptor = cipher.encryptor()

        pad_len = 16 - (len(plain_bytes) % 16)
        padded_plaintext = plain_bytes + bytes([pad_len] * pad_len)

        encrypted_bytes = encryptor.update(padded_plaintext) + encryptor.finalize()

        return b64encode(encrypted_bytes).decode('utf-8')

    def decrypt(self, encrypted_text: str) -> str:
        encrypted_bytes = b64decode(encrypted_text)

        cipher = Cipher(algorithms.AES(self.key), modes.CBC(self.iv), backend=default_backend())
        decryptor = cipher.decryptor()

        decrypted_padded_bytes = decryptor.update(encrypted_bytes) + decryptor.finalize()
        pad_len = decrypted_padded_bytes[-1]
        decrypted_bytes = decrypted_padded_bytes[:-pad_len]

        return decrypted_bytes.decode('utf-8')
