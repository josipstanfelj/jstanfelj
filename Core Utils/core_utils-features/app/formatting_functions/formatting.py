import customtkinter as ctk
from tkinter import filedialog
import os


class Formatter:
    selected_file = ""
    formatted_content = ""

    @staticmethod
    def make_screen(frame):
       
        for widget in frame.winfo_children():
            widget.destroy()

        
        label = ctk.CTkLabel(frame, text="Text Formatter", font=("Arial", 30), text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=3, sticky="nsew", pady=(20, 10))

        
        select_button = ctk.CTkButton(frame, text="Upload Text File", font=("Arial", 20),
                                      command=lambda: Formatter.select_text_file(frame))
        select_button.grid(row=1, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        
        format_label = ctk.CTkLabel(frame, text="Select Formatting Option:", font=("Arial", 20))
        format_label.grid(row=2, column=0, columnspan=1, sticky="e", padx=(10, 5), pady=(10, 10))

        format_var = ctk.StringVar(value="Title Case")
        format_dropdown = ctk.CTkComboBox(frame, values=["Title Case", "UPPERCASE", "lowercase"],
                                          variable=format_var, font=("Arial", 20))
        format_dropdown.grid(row=2, column=1, columnspan=2, sticky="w", padx=(5, 10), pady=(10, 10))

        
        format_button = ctk.CTkButton(frame, text="Format", font=("Arial", 20),
                                      command=lambda: Formatter.format_text(format_var.get(), frame))
        format_button.grid(row=3, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        
        Formatter.result_label = ctk.CTkLabel(frame, text="", font=("Arial", 20), text_color="#dfe6e9")
        Formatter.result_label.grid(row=4, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        
        Formatter.download_button = ctk.CTkButton(frame, text="Save Formatted File", font=("Arial", 20),
                                                         state="disabled",
                                                         command=lambda: Formatter.save_formatted_file())
        Formatter.download_button.grid(row=5, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        
        frame.grid_columnconfigure(0, weight=1)
        frame.grid_columnconfigure(1, weight=1)
        frame.grid_columnconfigure(2, weight=1)

    @staticmethod
    def select_text_file(frame):
        
        file = filedialog.askopenfilename(filetypes=[("Text Files", "*.txt")])
        Formatter.selected_file = file

        if file:
            Formatter.result_label.configure(text=f"Selected file: {os.path.basename(file)}")
        else:
            Formatter.result_label.configure(text="No file selected.")

    @staticmethod
    def format_text(format_option, frame):
        
        if not Formatter.selected_file:
            Formatter.result_label.configure(text="Please upload a text file first.")
            return

        try:
            
            with open(Formatter.selected_file, "r", encoding="utf-8") as file:
                content = file.read()

            if format_option == "Title Case":
                Formatter.formatted_content = content.title()
            elif format_option == "UPPERCASE":
                Formatter.formatted_content = content.upper()
            elif format_option == "lowercase":
                Formatter.formatted_content = content.lower()

            Formatter.result_label.configure(text="Formatting successful! Ready to save.")
            Formatter.download_button.configure(state="normal")
        except Exception as e:
            Formatter.result_label.configure(text=f"Error during formatting: {str(e)}")

    @staticmethod
    def save_formatted_file():
        
        if not Formatter.formatted_content:
            Formatter.result_label.configure(text="No formatted content to save.")
            return

        try:
            
            output_dir = "output"
            os.makedirs(output_dir, exist_ok=True)
            output_file = os.path.join(output_dir, "formatted_text.txt")

            with open(output_file, "w", encoding="utf-8") as file:
                file.write(Formatter.formatted_content)

            Formatter.result_label.configure(
                text=f"File saved successfully to {os.path.abspath(output_file)}."
            )
        except Exception as e:
            Formatter.result_label.configure(text=f"Error saving file: {str(e)}")
