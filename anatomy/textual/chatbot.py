from textual.app import App, ComposeResult
from textual.widgets import Header, Footer, Input, Static
from textual.containers import VerticalScroll
import os
import requests
import time

class ChatbotApp(App):
    """A Textual-based chatbot UI with Ollama integration."""

    CSS = """
    #chatbox {
        height: 100%;
        border: solid green;
    }
    """

    def compose(self) -> ComposeResult:
        yield Header()
        yield VerticalScroll(Static(id="chatbox"), id="chat_container")
        yield Input(placeholder="Type your message or a command (e.g., /quit, /attach <path>)...", id="message_input")
        yield Footer()

    def on_mount(self) -> None:
        self.query_one("#chatbox").update("[b]Chatbot:[/b] Hello! How can I help you today? (Use /quit to exit, /attach <path> to attach a file)")

    def on_input_submitted(self, event: Input.Submitted) -> None:
        input_widget = self.query_one("#message_input")
        user_input = input_widget.value.strip()
        if not user_input:
            return

        if user_input.startswith("/"):
            self.handle_command(user_input)
        else:
            self.send_message(user_input)

    def handle_command(self, command: str) -> None:
        """Handle commands like /quit or /attach."""
        chatbox = self.query_one("#chatbox")
        input_widget = self.query_one("#message_input")

        if command == "/quit":
            self.exit()
            return

        if command.startswith("/attach"):
            parts = command.split(" ", 1)
            if len(parts) < 2:
                chatbox.update(f"{chatbox.renderable}\n[b]Chatbot:[/b] Please provide a file path (e.g., /attach /path/to/file)!")
                input_widget.value = ""
                self.query_one("#chat_container").scroll_end(animate=False)
                return
            file_path = parts[1].strip()
            self.attach_file(file_path)
            return

        chatbox.update(f"{chatbox.renderable}\n[b]Chatbot:[/b] Unknown command: {command}")
        input_widget.value = ""
        self.query_one("#chat_container").scroll_end(animate=False)

    def send_message(self, user_message: str) -> None:
        """Send the user's message to Ollama and display the response."""
        input_widget = self.query_one("#message_input")
        chatbox = self.query_one("#chatbox")

        chatbox.update(f"{chatbox.renderable}\n[b]You:[/b] {user_message}")

        bot_response = self.get_ollama_response(user_message)
        chatbox.update(f"{chatbox.renderable}\n[b]Chatbot:[/b] {bot_response}")

        input_widget.value = ""
        self.query_one("#chat_container").scroll_end(animate=False)

    def get_ollama_response(self, message: str) -> str:
        """Send a message to the local Ollama API and get a response."""
        try:
            # Wait briefly to ensure Ollama is ready (optional)
            time.sleep(1)
            url = "http://localhost:11434/api/generate"
            payload = {
                "model": "mistral",  # Replace with your preferred model
                #"model": "deepseek-r1:1.5b",  # Replace with your preferred model
                "prompt": message,
                "stream": False
            }
            response = requests.post(url, json=payload, timeout=10)
            response.raise_for_status()

            result = response.json()
            return result.get("response", "Sorry, I couldn’t generate a response.")
        except requests.exceptions.ConnectionError:
            return "Error: Could not connect to Ollama. Is it running at http://localhost:11434?"
        except requests.exceptions.HTTPError as e:
            if e.response.status_code == 404:
                return "Error: API endpoint /api/generate not found. Check your Ollama version or endpoint."
            return f"Error: HTTP Error {e.response.status_code} - {str(e)}"
        except requests.exceptions.Timeout:
            return "Error: Connection to Ollama timed out."
        except Exception as e:
            return f"Error: Unexpected issue with Ollama: {str(e)}"

    def attach_file(self, file_path: str) -> None:
        chatbox = self.query_one("#chatbox")
        input_widget = self.query_one("#message_input")
        if not file_path:
            chatbox.update(f"{chatbox.renderable}\n[b]Chatbot:[/b] Please provide a file path!")
            return
        if os.path.exists(file_path):
            try:
                with open(file_path, "r", encoding="utf-8") as file:
                    file_content = file.read(500)
                    if len(file.read()) > 500:
                        file_content += "... (truncated)"
                    chatbox.update(f"{chatbox.renderable}\n[b]You (Attached):[/b] {file_path}\n{file_content}")
                    bot_response = self.get_ollama_response(f"I attached a file with this content:\n{file_content}")
                    chatbox.update(f"{chatbox.renderable}\n[b]Chatbot:[/b] {bot_response}")
            except Exception as e:
                chatbox.update(f"{chatbox.renderable}\n[b]Chatbot:[/b] Error reading file: {str(e)}")
        else:
            chatbox.update(f"{chatbox.renderable}\n[b]Chatbot:[/b] File not found: {file_path}")
        input_widget.value = ""
        self.query_one("#chat_container").scroll_end(animate=False)

if __name__ == "__main__":
    app = ChatbotApp()
    app.run()
