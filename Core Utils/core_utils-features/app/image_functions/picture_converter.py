import customtkinter as ctk
from tkinter import filedialog
from PIL import Image
import os

class ImageConverter:
    @staticmethod
    def make_screen(frame):
       
        for widget in frame.winfo_children():
            widget.destroy()

        
        label = ctk.CTkLabel(frame, 
                             text="Image Converter", 
                             font=("Arial", 30), 
                             text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=3, sticky="nsew", pady=(20, 10))

       
        select_button = ctk.CTkButton(frame, 
                                      text="Select Images", 
                                      font=("Arial", 20), 
                                      command=lambda: ImageConverter.select_images(frame))
        select_button.grid(row=1, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        
        format_label = ctk.CTkLabel(frame, 
                                    text="Select Output Format:", 
                                    font=("Arial", 20))
        format_label.grid(row=2, column=0, columnspan=1, sticky="e", padx=(10, 5), pady=(10, 10))
        
        
        format_var = ctk.StringVar(value="JPEG")
        format_dropdown = ctk.CTkComboBox(frame, 
                                          values=["JPEG", "PNG", "BMP", "GIF", "TIFF"], 
                                          variable=format_var, 
                                          font=("Arial", 20))
        format_dropdown.grid(row=2, column=1, columnspan=2, sticky="w", padx=(5, 10), pady=(10, 10))

       
        convert_button = ctk.CTkButton(frame, 
                                       text="Convert", 
                                       font=("Arial", 20), 
                                       command=lambda: ImageConverter.convert_images(format_var.get(), frame))
        convert_button.grid(row=3, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

       
        ImageConverter.result_label = ctk.CTkLabel(frame, 
                                                      text="", 
                                                      font=("Arial", 20), 
                                                      text_color="#dfe6e9")
        ImageConverter.result_label.grid(row=4, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        
        frame.grid_columnconfigure(0, weight=1)
        frame.grid_columnconfigure(1, weight=1)
        frame.grid_columnconfigure(2, weight=1)

        
        ImageConverter.download_button = ctk.CTkButton(frame, 
                                                          text="Download Converted Files", 
                                                          font=("Arial", 20), 
                                                          state="disabled",  
                                                          command=lambda: ImageConverter.download_converted_files())
        ImageConverter.download_button.grid(row=5, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

    selected_files = []
    converted_images = {}

    @staticmethod
    def select_images(frame):
        
        files = filedialog.askopenfilenames(
            filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.bmp;*.gif;*.tiff")]
        )

        
        ImageConverter.selected_files = files

        
        if files:
            ImageConverter.result_label.configure(
                text=f"Selected {len(files)} images."
            )
        else:
            ImageConverter.result_label.configure(
                text="No images selected."
            )

    @staticmethod
    def convert_images(output_format, frame):
       
        if not ImageConverter.selected_files:
            ImageConverter.result_label.configure(
                text="Please select images first."
            )
            return

        ImageConverter.converted_images.clear()  

        
        for file in ImageConverter.selected_files:
            try:
                
                with Image.open(file) as img:
                   
                    base_name = os.path.splitext(os.path.basename(file))[0]
                    new_file_name = f"{base_name}.{output_format.lower()}"
                    
                    
                    img = img.convert("RGB")
                    
                    
                    ImageConverter.converted_images[new_file_name] = img

            except Exception as e:
                
                ImageConverter.result_label.configure(
                    text=f"Error processing: {file}"
                )
                return

        
        ImageConverter.result_label.configure(
            text=f"Conversion successful! Ready to download."
        )

        
        ImageConverter.download_button.configure(state="normal")

    @staticmethod
    def download_converted_files():
        
        if not ImageConverter.converted_images:
            ImageConverter.result_label.configure(
                text="No files to download."
            )
            return

        
        downloads_folder = os.path.join(os.path.expanduser("~"), "Downloads")

        
        if not os.path.exists(downloads_folder):
            os.makedirs(downloads_folder)

       
        for file_name, img in ImageConverter.converted_images.items():
            output_path = os.path.join(downloads_folder, file_name)
            img.save(output_path) 

        
        os.startfile(downloads_folder) 

        
        ImageConverter.result_label.configure(
            text=f"Files have been saved to Downloads."
        )
