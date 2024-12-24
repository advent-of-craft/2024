class Error:
    def __init__(self, message: str):
        self.message = message

    def __str__(self):
        return self.message

    @staticmethod
    def an_error(message: str) -> 'Error':
        return Error(message)
