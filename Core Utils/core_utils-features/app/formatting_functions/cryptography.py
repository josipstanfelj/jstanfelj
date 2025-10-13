import os
import base64
import customtkinter as ctk
from tkinter import filedialog

class Cryptography:
    @staticmethod
    def make_screen(frame):
        for widget in frame.winfo_children():
            widget.destroy()

        
        label = ctk.CTkLabel(
            frame,
            text="Text Encoder/Decoder",
            font=("Arial", 30),
            text_color="#dfe6e9",
        )
        label.grid(row=0, column=0, columnspan=3, sticky="nsew", pady=(20, 10))

        
        select_button = ctk.CTkButton(
            frame,
            text="Upload .txt File",
            font=("Arial", 20),
            command=lambda: Cryptography.select_file(frame),
        )
        select_button.grid(row=1, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

       
        method_label = ctk.CTkLabel(
            frame, text="Select Method:", font=("Arial", 20)
        )
        method_label.grid(row=2, column=0, sticky="e", padx=(10, 5), pady=(10, 10))

        
        method_var = ctk.StringVar(value="Base64 Encode")
        method_dropdown = ctk.CTkComboBox(
            frame,
            values=["Base64 Encode", "Base64 Decode", "Caesar Encode", "Caesar Decode"],
            variable=method_var,
            font=("Arial", 20),
        )
        method_dropdown.grid(row=2, column=1, columnspan=2, sticky="w", padx=(5, 10), pady=(10, 10))

        
        shift_label = ctk.CTkLabel(
            frame, text="Caesar Shift:", font=("Arial", 20)
        )
        shift_label.grid(row=3, column=0, sticky="e", padx=(10, 5), pady=(10, 10))

        shift_entry = ctk.CTkEntry(frame, font=("Arial", 20))
        shift_entry.grid(row=3, column=1, columnspan=2, sticky="w", padx=(5, 10), pady=(10, 10))

        
        convert_button = ctk.CTkButton(
            frame,
            text="Convert",
            font=("Arial", 20),
            command=lambda: Cryptography.convert_text(
                method_var.get(), shift_entry.get(), frame
            ),
        )
        convert_button.grid(row=4, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        
        Cryptography.result_label = ctk.CTkLabel(
            frame, text="", font=("Arial", 20), text_color="#dfe6e9"
        )
        Cryptography.result_label.grid(
            row=5, column=0, columnspan=3, sticky="nsew", pady=(10, 10)
        )

        frame.grid_columnconfigure(0, weight=1)
        frame.grid_columnconfigure(1, weight=1)
        frame.grid_columnconfigure(2, weight=1)

    selected_file = ""

    @staticmethod
    def select_file(frame):
        file = filedialog.askopenfilename(filetypes=[("Text files", "*.txt")])
        Cryptography.selected_file = file

        if file:
            Cryptography.result_label.configure(
                text=f"Selected file: {os.path.basename(file)}"
            )
        else:
            Cryptography.result_label.configure(text="No file selected.")

    @staticmethod
    def convert_text(method, shift, frame):
        if not Cryptography.selected_file:
            Cryptography.result_label.configure(
                text="Please select a file first."
            )
            return

        try:
            with open(Cryptography.selected_file, "r", encoding="utf-8") as file:
                content = file.read()

            if method == "Base64 Encode":
                result = base64.b64encode(content.encode("utf-8")).decode("utf-8")

            elif method == "Base64 Decode":
                result = base64.b64decode(content.encode("utf-8")).decode("utf-8")

            elif method == "Caesar Encode":
                shift = int(shift) if shift.isdigit() else 0
                result = Cryptography.caesar_cipher(content, shift, encode=True)

            elif method == "Caesar Decode":
                shift = int(shift) if shift.isdigit() else 0
                result = Cryptography.caesar_cipher(content, shift, encode=False)

            else:
                result = "Invalid method."

            output_file = os.path.join(
                os.path.dirname(Cryptography.selected_file), "output.txt"
            )
            with open(output_file, "w", encoding="utf-8") as file:
                file.write(result)

            Cryptography.result_label.configure(
                text=f"Conversion successful! Output saved to: {output_file}"
            )

        except Exception as e:
            Cryptography.result_label.configure(
                text=f"Error: {str(e)}"
            )

    @staticmethod
    def caesar_cipher(text, shift, encode=True):
        result = ""
        for char in text:
            if char.isalpha():
                shift_dir = shift if encode else -shift
                base = ord("A") if char.isupper() else ord("a")
                result += chr((ord(char) - base + shift_dir) % 26 + base)
            else:
                result += char
        return result
