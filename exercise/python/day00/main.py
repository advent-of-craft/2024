from exercise.python.day00.encryption_email.encryption import Encryption
from exercise.python.day00.tests.utils import load_file, convert_key, convert_iv

if __name__ == '__main__':
    contents = load_file("EncryptedEmail.txt")
    key = convert_key('Advent Of Craft')
    iv = convert_iv('2024')
    print(Encryption(key, iv).decrypt(contents))
