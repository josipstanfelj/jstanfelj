import threading
import customtkinter as ctk
import time
import datetime
import json
from tkinter import messagebox

class StopWatchApp:
    
    @staticmethod
    def make_screen(frame):
        for widget in frame.winfo_children():
            widget.destroy()

        label = ctk.CTkLabel(frame,
            text="Stopwatch",
            font=("Arial", 30),
            text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=4, sticky="nsew")
        
        output = ctk.CTkTabview(
            frame,
            text_color="#dfe6e9",
            segmented_button_selected_color="#6c5ce7",
            segmented_button_selected_hover_color="#a29bfe")
        output.grid(row=2, column=0, columnspan=4, sticky="nsew", padx=20, pady=20)
        

        StopWatchApp.make_tab(output)
        
        for i in range(4):
            frame.grid_columnconfigure(i, weight=1)
        
    def make_tab(output):
        time_tab = output.add("Stopwatch")
        
        for i in range(4):
            time_tab.columnconfigure(i, weight=1)
        
        stopwatch = StopWatch()
        
        time_label = ctk.CTkLabel(time_tab,
            text="0:00:00.000000",
            font=("Arial", 30),
            text_color="#dfe6e9")
        time_label.grid(row=0, column=0, columnspan=4, sticky="nsew", padx=20, pady=20)
        
        start_button = ctk.CTkButton(time_tab,
            text="Start",
            font=("Arial", 20),
            command=lambda: StopWatchApp.start_stop(stopwatch, time_label, start_button, lap_button),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        start_button.grid(row=1, column=1, columnspan=1, sticky="ew", padx=20, pady=20)
        
        lap_button = ctk.CTkButton(time_tab,
            text="Reset",
            font=("Arial", 20),
            command=lambda: StopWatchApp.lap_reset(stopwatch, time_label, start_button, lap_frame),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        lap_button.grid(row=1, column=2, columnspan=1, sticky="ew", padx=20, pady=20)
        
        lap_frame = ctk.CTkScrollableFrame(time_tab)
        lap_frame.grid(row=2, column=0, columnspan=4, sticky="ew", padx=20, pady=20)
        
        save_entry = ctk.CTkEntry(time_tab,
            font=("Arial", 20),
            text_color="#dfe6e9",
            bg_color="#6c5ce7",
            border_color="#a29bfe")
        save_entry.grid(row=3, column=1, columnspan=1, sticky="ew", padx=20, pady=20)
        
        save_button = ctk.CTkButton(time_tab,
            text="Save",
            font=("Arial", 20),
            command=lambda: StopWatchApp.save_stopwatch(stopwatch, save_entry),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        save_button.grid(row=3, column=2, columnspan=1, sticky="ew", padx=20, pady=20)
        
        option_menu = ctk.CTkOptionMenu(
            time_tab,
            values=[],
            font=("Arial", 20),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            button_color="#6c5ce7",
            button_hover_color="#a29bfe"
            )
        option_menu.set("Load Stopwatch")
        option_menu.grid(row=4, column=1, columnspan=1, sticky="ew", padx=20, pady=20)

        option_menu.bind("<Enter>", lambda e: StopWatchApp.populate_options(option_menu=option_menu))
        
        load_button = ctk.CTkButton(time_tab,
            text="Load",
            font=("Arial", 20),
            command=lambda: StopWatchApp.load_stopwatch(
                name=option_menu.get(), 
                stopwatch_object=stopwatch, 
                time_label=time_label, 
                start_button=start_button, 
                lap_button=lap_button, 
                lap_frame=lap_frame),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        load_button.grid(row=4, column=2, columnspan=1, sticky="ew", padx=20, pady=20)
        
        
    def start_stop(stopwatch, time_label, start_button, lap_button):
        thread = None
        if stopwatch.is_running():
            stopwatch.stop()
            if time_label.cget("text") == "0:00:00.000000":
                lap_button.configure(text="Reset")
                start_button.configure(text="Start")
            else:
                lap_button.configure(text="Reset")
                start_button.configure(text="Resume")
        else:
            thread = threading.Thread(target=StopWatchApp.start_stopwatch, args=(stopwatch, time_label))
            lap_button.configure(text="Lap")
            start_button.configure(text="Stop")
            thread.start()
            
    def lap_reset(stopwatch, time_label, start_button, lap_frame):
        if stopwatch.is_running():
            time = stopwatch.get_elapsed_time()
            stopwatch.add_lap_time(time)
            lap = ctk.CTkLabel(lap_frame, text="Lap" + str(len(stopwatch.lap_times)) + ": " + str(datetime.timedelta(seconds=time)))
            lap.pack()
        else:
            stopwatch.reset()
            start_button.configure(text="Start")
            time_label.configure(text="0:00:00.000000")
            
    def start_stopwatch(stopwatch, time_label):
        stopwatch.start()
        while stopwatch.is_running():
            time.sleep(0.001)
            time_label.configure(text=datetime.timedelta(seconds=stopwatch.get_elapsed_time()))
            
    def save_stopwatch(stopwatch, save_entry):
        if save_entry.get() != "":
            stopwatch.name = save_entry.get()
            save_entry.delete(0, len(save_entry.get()))
            try:
                with open("assets/saved/stopwatch.jsonl", "a") as file:
                    file.write(json.dumps(stopwatch.to_dict()) + "\n")
                    messagebox.showinfo("Success", "Stopwatch saved successfully.")
            except Exception as e:
                messagebox.showerror("Error", "An error occurred while saving the stopwatch.")
        else:
            messagebox.showerror("Error", "Please enter a name for the stopwatch save.")
            
    def populate_options(option_menu):
        names = []
        with open("assets/saved/stopwatch.jsonl", "r") as file:
            for line in file:
                stopwatch = StopWatch()
                stopwatch = json.loads(line)
                names.append(stopwatch["name"])
        option_menu.configure(values=names)
            
    def load_stopwatch(name, stopwatch_object, time_label, start_button, lap_button, lap_frame):
        with open("assets/saved/stopwatch.jsonl", "r") as file:
            for line in file:
                stopwatch = StopWatch()
                stopwatch = json.loads(line)
                if stopwatch["name"] == name:
                    for key, value in stopwatch.items():
                        setattr(stopwatch_object, key, value)
                    time_label.configure(text=datetime.timedelta(seconds=stopwatch_object.get_elapsed_time()))
                    start_button.configure(text="Resume")
                    lap_button.configure(text="Reset")
                    for widget in lap_frame.winfo_children():
                        widget.destroy()
                    for index, value in enumerate(stopwatch_object.lap_times):
                        lap = ctk.CTkLabel(lap_frame, text="Lap" + str(index+1) + ": " + str(datetime.timedelta(seconds=value)))
                        lap.pack()
                    break
    
    def on_select(selected_item):
        print(f"Selected: {selected_item}")
        
class StopWatch:
    def __init__(self):
        self.name = "My Stopwatch"
        self.start_time = None
        self.running = False
        self.elapsed_time = 0
        self.lap_times = []
        
    def to_dict(self):
        return {
            "name": self.name,
            "start_time": self.start_time,
            "running": self.running,
            "elapsed_time": self.elapsed_time,
            "lap_times": self.lap_times
        }

    def start(self):
        if not self.running:
            self.running = True
            self.start_time = time.time() - self.elapsed_time
            print("Stopwatch started.")

    def stop(self):
        if self.running:
            self.running = False
            self.elapsed_time = time.time() - self.start_time
            print("Stopwatch stopped. Elapsed time:", self.elapsed_time)

    def reset(self):
        self.running = False
        self.elapsed_time = 0
        self.start_time = None
        print("Stopwatch reset.")

    def is_running(self):
        return self.running

    def get_elapsed_time(self):
        if self.running:
            return time.time() - self.start_time
        return self.elapsed_time
    
    def add_lap_time(self, lap_time):
        self.lap_times.append(lap_time)