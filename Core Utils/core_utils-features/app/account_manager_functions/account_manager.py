import customtkinter as ctk
from tkinter import filedialog, messagebox
import random
import string
import os
import json

class PasswordManagerApp:
    accounts = {}

    current_dir = os.path.dirname(os.path.abspath(__file__))
    data_file = os.path.join(current_dir, "passwords.json")

    @staticmethod
    def make_screen(frame):
       
        for widget in frame.winfo_children():
            widget.destroy()

       
        ctk.CTkLabel(frame, text="Password Manager", font=("Arial", 30)).grid(
            row=0, column=0, columnspan=3, pady=(20, 10)
        )

        ctk.CTkLabel(frame, text="Stranica:", font=("Arial", 20)).grid(row=1, column=0, pady=(10, 10))
        site_entry = ctk.CTkEntry(frame, font=("Arial", 20))
        site_entry.grid(row=1, column=1, pady=(10, 10))

        ctk.CTkLabel(frame, text="Račun (Ime):", font=("Arial", 20)).grid(row=2, column=0, pady=(10, 10))
        account_entry = ctk.CTkEntry(frame, font=("Arial", 20))
        account_entry.grid(row=2, column=1, pady=(10, 10))

        ctk.CTkLabel(frame, text="E-mail:", font=("Arial", 20)).grid(row=3, column=0, pady=(10, 10))
        email_entry = ctk.CTkEntry(frame, font=("Arial", 20))
        email_entry.grid(row=3, column=1, pady=(10, 10))

        ctk.CTkLabel(frame, text="Lozinka:", font=("Arial", 20)).grid(row=4, column=0, pady=(10, 10))
        password_entry = ctk.CTkEntry(frame, font=("Arial", 20), show="*")
        password_entry.grid(row=4, column=1, pady=(10, 10))

       
        ctk.CTkButton(
            frame, text="Generiraj Lozinku", font=("Arial", 20),
            command=lambda: PasswordManagerApp.generate_password(password_entry)
        ).grid(row=5, column=0, columnspan=3, pady=(10, 10))

       
        ctk.CTkButton(
            frame, text="Spremi Račun", font=("Arial", 20),
            command=lambda: PasswordManagerApp.save_account(site_entry, account_entry, email_entry, password_entry, frame)
        ).grid(row=6, column=0, columnspan=3, pady=(10, 10))

        
        ctk.CTkButton(
            frame, text="Prikaži Račune", font=("Arial", 20),
            command=lambda: PasswordManagerApp.display_accounts(frame)
        ).grid(row=7, column=0, columnspan=3, pady=(10, 10))

        
        for i in range(3):
            frame.grid_columnconfigure(i, weight=1)

        
        PasswordManagerApp.load_accounts()

    @staticmethod
    def generate_password(entry):
        length = 12
        characters = string.ascii_letters + string.digits + string.punctuation
        password = ''.join(random.choice(characters) for _ in range(length))
        entry.delete(0, ctk.END)
        entry.insert(0, password)

    @staticmethod
    def save_account(site_entry, account_entry, email_entry, password_entry, frame):
        site = site_entry.get().strip()
        account = account_entry.get().strip()
        email = email_entry.get().strip()
        password = password_entry.get().strip()

        if not site or not account or not email or not password:
            messagebox.showwarning("Upozorenje", "Molimo unesite sve podatke.")
            return

        PasswordManagerApp.accounts[site] = {
            "account": account,
            "email": email,
            "password": password
        }
        PasswordManagerApp.save_to_file()

        messagebox.showinfo("Uspjeh", "Račun je uspješno spremljen.")
        site_entry.delete(0, ctk.END)
        account_entry.delete(0, ctk.END)
        email_entry.delete(0, ctk.END)
        password_entry.delete(0, ctk.END)

       
        PasswordManagerApp.display_accounts(frame)

    @staticmethod
    def save_to_file():
        try:
            with open(PasswordManagerApp.data_file, "w") as file:
                json.dump(PasswordManagerApp.accounts, file, indent=4)
        except Exception as e:
            messagebox.showerror("Greška", f"Nije moguće spremiti podatke: {e}")

    @staticmethod
    def load_accounts():
        if os.path.exists(PasswordManagerApp.data_file):
            try:
                with open(PasswordManagerApp.data_file, "r") as file:
                    PasswordManagerApp.accounts = json.load(file)
            except Exception as e:
                messagebox.showerror("Greška", f"Nije moguće učitati podatke: {e}")

    @staticmethod
    def display_accounts(frame):
        
        for widget in frame.winfo_children():
            if isinstance(widget, ctk.CTkFrame):
                widget.destroy()

        if not PasswordManagerApp.accounts:
            messagebox.showinfo("Informacija", "Nema spremljenih računa.")
            return

        accounts_frame = ctk.CTkFrame(frame)
        accounts_frame.grid(row=8, column=0, columnspan=3, pady=(10, 10), sticky="nsew")

        ctk.CTkLabel(accounts_frame, text="Stranica", font=("Arial", 15, "bold")).grid(row=0, column=0, padx=5, pady=5)
        ctk.CTkLabel(accounts_frame, text="Račun (Ime)", font=("Arial", 15, "bold")).grid(row=0, column=1, padx=5, pady=5)
        ctk.CTkLabel(accounts_frame, text="E-mail", font=("Arial", 15, "bold")).grid(row=0, column=2, padx=5, pady=5)
        ctk.CTkLabel(accounts_frame, text="Lozinka", font=("Arial", 15, "bold")).grid(row=0, column=3, padx=5, pady=5)

        for i, (site, details) in enumerate(PasswordManagerApp.accounts.items(), start=1):
            ctk.CTkLabel(accounts_frame, text=site, font=("Arial", 15)).grid(row=i, column=0, padx=5, pady=5, sticky="w")
            ctk.CTkLabel(accounts_frame, text=details["account"], font=("Arial", 15)).grid(row=i, column=1, padx=5, pady=5, sticky="w")
            ctk.CTkLabel(accounts_frame, text=details["email"], font=("Arial", 15)).grid(row=i, column=2, padx=5, pady=5, sticky="w")
            ctk.CTkLabel(accounts_frame, text=details["password"], font=("Arial", 15)).grid(row=i, column=3, padx=5, pady=5, sticky="w")

        accounts_frame.grid_columnconfigure(0, weight=1)
        accounts_frame.grid_columnconfigure(1, weight=1)
        accounts_frame.grid_columnconfigure(2, weight=1)
        accounts_frame.grid_columnconfigure(3, weight=1)
