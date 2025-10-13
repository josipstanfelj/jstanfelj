import customtkinter as ctk
from tkinter import filedialog
from PIL import Image
import pytesseract
import os


pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'


class ImageToTextConverterApp:
    @staticmethod
    def make_screen(frame):
        for widget in frame.winfo_children():
            widget.destroy()

        label = ctk.CTkLabel(frame, text="Image to Text Converter", font=("Arial", 30), text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=3, sticky="nsew", pady=(20, 10))

        select_button = ctk.CTkButton(frame, text="Select Images", font=("Arial", 20), 
                                      command=lambda: ImageToTextConverterApp.select_images(frame))
        select_button.grid(row=1, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        convert_button = ctk.CTkButton(frame, text="Convert to Text", font=("Arial", 20), 
                                       command=lambda: ImageToTextConverterApp.convert_to_text(frame))
        convert_button.grid(row=2, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        ImageToTextConverterApp.result_label = ctk.CTkLabel(frame, text="", font=("Arial", 20), text_color="#dfe6e9")
        ImageToTextConverterApp.result_label.grid(row=3, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        frame.grid_columnconfigure(0, weight=1)
        frame.grid_columnconfigure(1, weight=1)
        frame.grid_columnconfigure(2, weight=1)

    selected_files = []

    @staticmethod
    def select_images(frame):
        files = filedialog.askopenfilenames(filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.bmp;*.gif;*.tiff")])
        ImageToTextConverterApp.selected_files = files

        if files:
            ImageToTextConverterApp.result_label.configure(text=f"Selected {len(files)} images.")
        else:
            ImageToTextConverterApp.result_label.configure(text="No images selected.")

    @staticmethod
    def convert_to_text(frame):
        if not ImageToTextConverterApp.selected_files:
            ImageToTextConverterApp.result_label.configure(text="Please select images first.")
            return

        pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe' 

        downloads_folder = os.path.join(os.path.expanduser("~"), "Downloads")
        if not os.path.exists(downloads_folder):
            os.makedirs(downloads_folder)

        for file in ImageToTextConverterApp.selected_files:
            try:
                with Image.open(file) as img:
                    print(f"Processing image: {file}")
                    text = pytesseract.image_to_string(img)
                    print(f"Extracted text: {text}")

                    base_name = os.path.splitext(os.path.basename(file))[0]
                    output_path = os.path.join(downloads_folder, f"{base_name}.txt")

                    with open(output_path, "w", encoding="utf-8") as text_file:
                        text_file.write(text)
                        print(f"Saved to: {output_path}")

            except Exception as e:
                print(f"Error processing {file}: {e}")
                ImageToTextConverterApp.result_label.configure(text=f"Error processing: {file}")
                return

        ImageToTextConverterApp.result_label.configure(text="Conversion successful! Text files saved to Downloads.")
        os.startfile(downloads_folder)
