import customtkinter as ctk
from tkinter import filedialog, messagebox
import os
import ffmpeg

class AudioConverterApp:
    selected_files = []
    @staticmethod
    def make_screen(frame):
        for widget in frame.winfo_children():
            widget.destroy()

        ctk.CTkLabel(frame, text="Audio Converter", font=("Arial", 30)).grid(
            row=0, column=0, columnspan=3, pady=(20, 10)
        )

        ctk.CTkButton(
            frame, text="Odaberi Audio Datoteke", font=("Arial", 20),
            command=lambda: AudioConverterApp.select_files(frame)
        ).grid(row=1, column=0, columnspan=3, pady=(10, 10))

        ctk.CTkLabel(frame, text="Izlazni Format:", font=("Arial", 20)).grid(
            row=2, column=0, pady=(10, 10)
        )
        
        format_var = ctk.StringVar(value="MP3")
        format_dropdown = ctk.CTkComboBox(frame, 
                                          values=["MP3", "WAV", "OGG", "FLAC", "AAC"], 
                                          variable=format_var,
                                          font=("Arial", 20))
        
        format_dropdown.grid(row=2, column=1, pady=(10, 10))

        ctk.CTkButton(
            frame, text="Konvertiraj", font=("Arial", 20),
            command=lambda: AudioConverterApp.convert_files(format_var.get(),frame)
        ).grid(row=3, column=0, columnspan=3, pady=(10, 10))

        AudioConverterApp.files_label = ctk.CTkLabel(frame, text="Nije odabrana nijedna datoteka.", font=("Arial", 15), wraplength=400)
        AudioConverterApp.files_label.grid(row=4, column=0, columnspan=3, pady=(10, 10))

        for i in range(3):
            frame.grid_columnconfigure(i, weight=1)


    @staticmethod
    def select_files(frame):
        files = filedialog.askopenfilenames(
            filetypes=[("Audio Datoteke", "*.mp3;*.wav;*.ogg;*.flac;*.aac")]
        )
        if files:
            print(files)
            AudioConverterApp.selected_files = files
            file_list = "\n".join([os.path.basename(f) for f in files])
            AudioConverterApp.files_label.configure(text=f"Odabrane datoteke:\n{file_list}")
        else:
            AudioConverterApp.files_label.configure(text="Nije odabrana nijedna datoteka.")

    @staticmethod
    def get_unique_filename(directory, filename):
        base, ext = os.path.splitext(filename)
        counter = 1
        new_filename = filename
        while os.path.exists(os.path.join(directory, new_filename)):
            new_filename = f"{base}({counter}){ext}"
            counter += 1
        return new_filename

    @staticmethod
    def convert_files(output_format, frame):
        if not AudioConverterApp.selected_files:
            messagebox.showwarning("Nema datoteka", "Molimo odaberite datoteke prvo.")
            return

        output_dir = os.path.join(os.path.expanduser("~"), "Downloads")
        output_format = output_format.lower()

        for file in AudioConverterApp.selected_files:
            try:
                base_name = os.path.splitext(os.path.basename(file))[0]
                output_filename = f"{base_name}.{output_format}"
                output_filename = AudioConverterApp.get_unique_filename(output_dir, output_filename)
                output_path = os.path.join(output_dir, output_filename)

                output_codec = {
                    "mp3": "libmp3lame",
                    "aac": "aac",
                    "wav": "pcm_s16le",
                    "flac": "flac",
                    "ogg": "libvorbis",
                }.get(output_format)

                if output_codec:
                    ffmpeg.input(file).output(output_path, acodec=output_codec, vn=None, ac=2).run(overwrite_output=True)
                else:
                    raise ValueError(f"Nepodržani format: {output_format}")

            except ffmpeg.Error as e:
                error_message = e.stderr.decode()
                messagebox.showerror("FFmpeg Greška", f"Greška pri konverziji {file}:\n{error_message}")
                return
            except ValueError as e:
                messagebox.showerror("Greška", str(e))
                return
            except Exception as e:
                messagebox.showerror("Greška", f"Neočekivana greška pri konverziji {file}: {e}")
                return

        messagebox.showinfo("Uspjeh", f"Datoteke su konvertirane i spremljene u {output_dir}.")
        try:
            os.startfile(output_dir)
        except OSError:
            messagebox.showwarning("Upozorenje", f"Nije moguće automatski otvoriti direktorij {output_dir}.")

