import hashlib
import os
from base64 import b64encode


def convert_key(key: str) -> str:
    sha256 = hashlib.sha256()
    sha256.update(key.encode('utf-8'))
    return b64encode(sha256.digest()).decode('utf-8')


def convert_iv(iv: str) -> str:
    md5 = hashlib.md5()
    md5.update(iv.encode('utf-8'))
    return b64encode(md5.digest()).decode('utf-8')


def load_file(filename):
    path = os.path.join(os.path.dirname(__file__), 'resources', filename)
    if not os.path.exists(path):
        raise FileNotFoundError(f"File not found: {path}")

    with open(path, 'r', encoding='utf-8') as file:
        return file.read()
