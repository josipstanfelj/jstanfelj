import customtkinter as ctk
import math

class ScientificCalculator:
    @staticmethod
    def make_screen(frame):
        for widget in frame.winfo_children():
            widget.destroy()

        label = ctk.CTkLabel(frame,
                             text="Scientific Calculator",
                             font=("Arial", 30),
                             text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=5, sticky="nsew", pady=(10, 20))

        entry = ctk.CTkEntry(frame, font=("Arial", 20), height=50)
        entry.grid(row=1, column=0, columnspan=5, sticky="nsew", padx=10, pady=10)

        def append_to_entry(value):
            entry.insert("end", value)

        def calculate_result():
            try:
                result = eval(entry.get())
                entry.delete(0, "end")
                entry.insert("end", str(result))
            except Exception as e:
                entry.delete(0, "end")
                entry.insert("end", "Error")

        def clear_entry():
            entry.delete(0, "end")

        buttons = [
            ("7", 2, 0), ("8", 2, 1), ("9", 2, 2), ("/", 2, 3), ("sqrt", 2, 4),
            ("4", 3, 0), ("5", 3, 1), ("6", 3, 2), ("*", 3, 3), ("sin", 3, 4),
            ("1", 4, 0), ("2", 4, 1), ("3", 4, 2), ("-", 4, 3), ("cos", 4, 4),
            ("C", 5, 0), ("0", 5, 1), ("=", 5, 2), ("+", 5, 3), ("tan", 5, 4),
            ("(", 6, 0), (")", 6, 1), ("log", 6, 2), ("pi", 6, 3), ("e", 6, 4)
        ]

        for (text, row, col) in buttons:
            if text == "=":
                btn = ctk.CTkButton(frame, text=text, font=("Arial", 20),
                                    command=calculate_result, text_color="#dfe6e9", fg_color="#6c5ce7",
                                    border_color="#a29bfe", hover_color="#a29bfe")
            elif text == "C":
                btn = ctk.CTkButton(frame, text=text, font=("Arial", 20),
                                    command=clear_entry, text_color="#dfe6e9", fg_color="#d63031",
                                    border_color="#fab1a0", hover_color="#fab1a0")
            else:
                btn = ctk.CTkButton(frame, text=text, font=("Arial", 20),
                                    command=lambda t=text: append_to_entry(t), text_color="#dfe6e9",
                                    fg_color="#6c5ce7", border_color="#a29bfe", hover_color="#a29bfe")
            btn.grid(row=row, column=col, sticky="nsew", padx=5, pady=5)

    
        def append_to_entry(value):
            if value == "sqrt":
                entry.insert("end", "math.sqrt(")
            elif value == "sin":
                entry.insert("end", "math.sin(")
            elif value == "cos":
                entry.insert("end", "math.cos(")
            elif value == "tan":
                entry.insert("end", "math.tan(")
            elif value == "log":
                entry.insert("end", "math.log(")
            elif value == "pi":
                entry.insert("end", str(math.pi))
            elif value == "e":
                entry.insert("end", str(math.e))
            else:
                entry.insert("end", value)

        for i in range(5):
            frame.grid_columnconfigure(i, weight=1)
        for i in range(7):
            frame.grid_rowconfigure(i, weight=1)
