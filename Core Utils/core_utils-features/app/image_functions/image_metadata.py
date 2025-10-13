import customtkinter as ctk
from tkinter import filedialog, messagebox
from PIL import Image, ExifTags
import webview

class ImageMetadata:
    browser = None
    
    @staticmethod
    def make_screen(frame):
        #Clear the frame
        for widget in frame.winfo_children():
            widget.destroy()
            
        label = ctk.CTkLabel(frame,
            text="Image Metadata",
            font=("Arial", 30),
            text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=4, sticky="nsew")
        
        output = ctk.CTkTabview(
            frame,
            text_color="#dfe6e9",
            segmented_button_selected_color="#6c5ce7",
            segmented_button_selected_hover_color="#a29bfe")
        output.grid(row=2, column=0, columnspan=4, sticky="nsew", padx=20, pady=20)
        metadata_tab = output.add("Metadata")
        gpsinfo_tab = output.add("GpsInfo")
        makernote_tab = output.add("MakerNote")
        
        button = ctk.CTkButton(frame,
            text="Select Image",
            command=lambda: ImageMetadata.select_image(metadata_tab, gpsinfo_tab, makernote_tab),
            font=("Arial", 20),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        button.grid(row=1, column=0, pady=20)
        
    def select_image(metadata_tab, gpsinfo_tab, makernote_tab):
        file_path = filedialog.askopenfilename(filetypes=[("Image files", "*.jpg *.jpeg *.png *.tiff *.jp2 *.pgf *.miff *.hdp *.psp *xcf *.raw")])
        if file_path:
            print(f"Selected image: {file_path}")
            metadata = ImageMetadata.read_metadata(file_path)
            if metadata:
                ImageMetadata.show_metadata(metadata, metadata_tab, gpsinfo_tab, makernote_tab, file_path)
            else:
                print("No metadata found")
    
    def read_metadata(file_path):
        metadata = {}
        with Image.open(file_path) as img:
            info = img._getexif()
            if info:
                for tag, value in info.items():
                    tag_name = ExifTags.TAGS.get(tag, tag)
                    metadata[tag_name] = value
                return metadata
            
            else:
                return None
            
    def show_metadata(metadata, metadata_tab, gpsinfo_tab, makernote_tab, file_path):
        for widget in metadata_tab.winfo_children():
            widget.destroy()
        for widget in gpsinfo_tab.winfo_children():
            widget.destroy()
        for widget in makernote_tab.winfo_children():
            widget.destroy()
        
        i=0
        for key, value in metadata.items():
            if key not in ["GPSInfo", "MakerNote"]:
                keyLabel = ctk.CTkLabel(metadata_tab, text=key, font=("Arial", 20))
                keyLabel.grid(row=i, column=0, columnspan=1,sticky="nsw", pady=5)
                keyLabel.bind("<Button-1>", ImageMetadata.copy_to_clipboard)
                valueLabel = ctk.CTkLabel(metadata_tab, text=repr(value).replace("'",""), font=("Arial", 20), wraplength=400)
                valueLabel.grid(row=i, column=1, columnspan=3, sticky="nse", pady=5)
                valueLabel.bind("<Button-1>", ImageMetadata.copy_to_clipboard)
            i+=1
        
        metadata_tab.grid_columnconfigure(0, weight=1)
        metadata_tab.grid_columnconfigure(1, weight=1)
        metadata_tab.grid_columnconfigure(2, weight=1)
        metadata_tab.grid_columnconfigure(3, weight=1)
        
        i=0
        if "GPSInfo" in metadata:
            for key, value in metadata["GPSInfo"].items():
                keyText = ExifTags.GPSTAGS.get(key, key)
                keyLabel = ctk.CTkLabel(gpsinfo_tab, text=keyText, font=("Arial", 20))
                keyLabel.grid(row=i, column=0, columnspan=1,sticky="nsw", pady=5)
                keyLabel.bind("<Button-1>", ImageMetadata.copy_to_clipboard)
                valueLabel = ctk.CTkLabel(gpsinfo_tab, text=repr(value).replace("'",""), font=("Arial", 20), wraplength=400)
                valueLabel.grid(row=i, column=1, columnspan=3, sticky="nse", pady=5)
                valueLabel.bind("<Button-1>", ImageMetadata.copy_to_clipboard)
                i+=1
            show_map_button = ctk.CTkButton(gpsinfo_tab, 
                text="Show Map",
                command=lambda: ImageMetadata.open_map(metadata["GPSInfo"]),
                font=("Arial", 20),
                text_color="#dfe6e9",
                fg_color="#6c5ce7",
                border_color="#a29bfe",
                hover_color="#a29bfe")
            show_map_button.grid(row=i, column=0, columnspan=4, pady=10)
            i+=1
            delete_gpsinfo_button = ctk.CTkButton(gpsinfo_tab, 
                text="Delete GPS Info",
                command=lambda: ImageMetadata.remove_gps_metadata(file_path),
                font=("Arial", 20),
                text_color="#dfe6e9",
                fg_color="#6c5ce7",
                border_color="#a29bfe",
                hover_color="#a29bfe")
            delete_gpsinfo_button.grid(row=i, column=0, columnspan=4, pady=10)
        else:
            keyLabel = ctk.CTkLabel(gpsinfo_tab, text="No GPS Info Found", font=("Arial", 20))
            keyLabel.grid(row=i, column=0, columnspan=1,sticky="nsw", pady=5)
        gpsinfo_tab.grid_columnconfigure(0, weight=1)
        gpsinfo_tab.grid_columnconfigure(1, weight=1)
        gpsinfo_tab.grid_columnconfigure(2, weight=1)
        gpsinfo_tab.grid_columnconfigure(3, weight=1)
        
        if "MakerNote" in metadata:
            keyLabel = ctk.CTkLabel(makernote_tab, text="MakerNote", font=("Arial", 20))
            keyLabel.grid(row=1, column=0, columnspan=1,sticky="nsw", pady=5)
            keyLabel.bind("<Button-1>", ImageMetadata.copy_to_clipboard)
            valueLabel = ctk.CTkLabel(makernote_tab, text=repr(metadata["MakerNote"]).replace("'",""), font=("Arial", 20), wraplength=400)
            valueLabel.grid(row=i, column=0, columnspan=4, sticky="nse", pady=5)
            valueLabel.bind("<Button-1>", ImageMetadata.copy_to_clipboard)
        else:
            keyLabel = ctk.CTkLabel(makernote_tab, text="No MakerNote Found", font=("Arial", 20))
            keyLabel.grid(row=i, column=0, columnspan=1,sticky="nsw", pady=5)
        gpsinfo_tab.grid_columnconfigure(0, weight=1)
        gpsinfo_tab.grid_columnconfigure(1, weight=1)
        gpsinfo_tab.grid_columnconfigure(2, weight=1)
        gpsinfo_tab.grid_columnconfigure(3, weight=1)
        
    def open_map(gpsinfo):
        for key, value in gpsinfo.items():
            keyText = ExifTags.GPSTAGS.get(key, key)
            if keyText == "GPSLatitudeRef":
                latRef = value
            elif keyText == "GPSLatitude":
                lat = value
            elif keyText == "GPSLongitudeRef":
                lonRef = value
            elif keyText == "GPSLongitude":
                lon = value
        if not lat or not lon or not latRef or not lonRef:
            return None
        latitude = float(lat[0]) + float(lat[1])/60 + float(lat[2])/3600 if latRef == "N" else -(float(lat[0]) + float(lat[1])/60 + float(lat[2])/3600)
        longitude = float(lon[0]) + float(lon[1])/60 + float(lon[2])/3600 if lonRef == "E" else -(float(lon[0]) + float(lon[1])/60 + float(lon[2])/3600)
        print(f"Latitude: {latitude}, Longitude: {longitude}")
        map_url = f"https://www.google.com/maps?q={latitude},{longitude}&z=15"
        webview.create_window("Google Maps", map_url)
        webview.start()
        
    def copy_to_clipboard(event):
        label = event.widget
        text = label.cget("text")
        label.clipboard_clear()
        label.clipboard_append(text)
        print(f"Copied to clipboard: {text}")
        
    def remove_gps_metadata(image_path):
        image = Image.open(image_path)
        exif_data = image.getexif()

        if exif_data:
            gps_tag = None
            for tag, value in exif_data.items():
                tag_name = ExifTags.TAGS.get(tag, tag)
                if tag_name == "GPSInfo":
                    gps_tag = tag
                    break
                
            if gps_tag is not None:
                del exif_data[gps_tag]

            new_image_path = image_path.rsplit(".", 1)[0] + "_no_gpsinfo." + image.format.lower()
            image.save(new_image_path, image.format, exif=exif_data.tobytes())
            messagebox.showinfo("Image Created", f"New image created without GPSInfo metadata: {new_image_path}")
            return 0

        return None