import customtkinter as ctk
from .core_utils_functions import OptionGroup

class CoreUtilsApp(ctk.CTk):
    def __init__(self):
        super().__init__()

        ctk.set_appearance_mode("dark")
        self.geometry("900x900")
        self.minsize(500, 500)
        self.title("Core Utils")
        self.iconbitmap("assets/icon.ico")

        sidebar = ctk.CTkScrollableFrame(self, corner_radius=(0))
        sidebar.grid(row=0, column=0, sticky="nsew")
        
        
        search_frame = ctk.CTkFrame(sidebar, corner_radius=(0))
        search_frame.pack(fill="x", padx=10, pady=(10, 0))
        found_frame = ctk.CTkFrame(sidebar, fg_color="#242424", corner_radius=(0), height=0)
        found_frame.pack(fill="x", padx=10, pady=0, ipady=5)
        search_entry = ctk.CTkEntry(search_frame, font=("Arial", 15), fg_color="#242424", corner_radius=(0))
        search_entry.pack(side="left", fill="x", expand=True)
        search_entry.bind("<Return>", lambda event: self.search(event, found_frame, search_entry.get()))
        search_button = ctk.CTkButton(
                    search_frame, 
                    text="Search", 
                    font=("Arial", 15),
                    command=lambda: self.search(None, found_frame, search_entry.get()),
                    fg_color="#242424",
                    border_color="#242424",
                    hover_color="#2b2b2b",
                    border_width=3,
                    corner_radius=(0))
        search_button.pack(side="right", fill="x", expand=False)

        options = OptionGroup.getOptions()
        for option in options:
            def create_button(option):
                button = ctk.CTkButton(
                    sidebar,
                    text=option.group,
                    font=("Arial", 20),
                    command=lambda: self.add_options(option, sidebar, button),
                    fg_color="#242424",
                    border_color="#242424",
                    hover_color="#2b2b2b",
                    border_width=3,
                    height=50,
                    corner_radius=(0))
                button.pack(fill="x", padx=10, pady=(10, 0))
            create_button(option)

        
        self.main_area_frame = ctk.CTkScrollableFrame(self, fg_color="#242424", corner_radius=0)
        self.main_area_frame.grid(row=0, column=1, sticky="nsew")

        self.main_area_label = ctk.CTkLabel(self.main_area_frame,
            text="Welcome to Core Utils!",
            font=("Arial", 30),
            text_color="#dfe6e9")
        self.main_area_label.grid(row=0, column=0, columnspan=2)

        
        self.grid_rowconfigure(0, weight=1)
        self.grid_columnconfigure(0, weight=1)
        self.grid_columnconfigure(1, weight=4)
        self.main_area_frame.grid_columnconfigure(0, weight=1)

    
    def add_options(self, option, sidebar, btn):
        if option.active:
            option.active.destroy()
            option.active = None
            return
        frame = ctk.CTkFrame(sidebar, fg_color="#242424", corner_radius=(0))
        frame.pack(fill="x", padx=10, pady=0, after=btn, ipady=5)
        for opt in option.options:
            button = ctk.CTkButton(
                frame,
                text=opt,
                command= lambda opt=opt: option.options[opt](self.main_area_frame),
                font=("Arial", 15),
                text_color="#dfe6e9",
                fg_color="#6c5ce7",
                border_color="#a29bfe",
                hover_color="#a29bfe",
                border_width=1,
                height=35,
                corner_radius=0)
            button.pack(fill="x", padx=10, pady=0)
        option.active = frame
    
    
    def search(self, event, found_frame, search_query):
        for widget in found_frame.winfo_children():
            widget.destroy()
        if not search_query:
            found_frame.configure(height=0)
            return
        options = OptionGroup.getOptions()
        for option in options:
            for opt in option.options:
                if search_query.lower() in opt.lower():
                    self.create_button(option, opt, found_frame)
    
    def create_button(self, option, opt, found_frame):         
        button = ctk.CTkButton(
            found_frame,
            text=opt,
            font=("Arial", 15),
            command= lambda: option.options[opt](self.main_area_frame),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe",
            border_width=1,
            height=35,
            corner_radius=0)
        button.pack(fill="x", padx=10, pady=0)