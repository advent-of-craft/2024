import os
from datetime import datetime
import webbrowser


class Capsule:
    TEMPLATE = "timecapsule_template.html"
    FILE_PATH = "timecapsule.html"

    def __init__(self):
        self.past_message = None
        self.timestamp = None
        self.has_past_message = self.load_past_message()

    def load_past_message(self):
        if not os.path.exists(self.FILE_PATH):
            return False

        with open(self.FILE_PATH, "r", encoding="utf-8") as file:
            html_content = file.read()

        start_index = html_content.find("<!--MESSAGE_START-->")
        end_index = html_content.find("<!--MESSAGE_END-->")

        if start_index == -1 or end_index == -1:
            return False

        self.past_message = html_content[start_index + 18:end_index].strip()
        self.timestamp = datetime.fromtimestamp(os.path.getmtime(self.FILE_PATH))

        return True

    def save_message(self, message):
        if not os.path.exists(self.TEMPLATE):
            raise FileNotFoundError("HTML template file not found!")

        with open(self.TEMPLATE, "r", encoding="utf-8") as file:
            template_content = file.read()

        filled_content = template_content.replace("{{message}}", message).replace(
            "{{timestamp}}", datetime.now().strftime("%B %d, %Y %H:%M:%S")
        )

        with open(self.FILE_PATH, "w", encoding="utf-8") as file:
            file.write(filled_content)

        self.timestamp = datetime.now()
        self.past_message = message


def open_browser(file_path):
    """Open the HTML file in the default browser."""
    webbrowser.open(f"file://{os.path.abspath(file_path)}")
