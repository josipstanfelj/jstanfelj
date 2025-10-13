import customtkinter as ctk
import requests
import threading
from tkinter import messagebox

class ExchangeRate:
    @staticmethod
    def make_screen(frame):
        for widget in frame.winfo_children():
            widget.destroy()

        label = ctk.CTkLabel(
            frame,
            text="Tečajna Lista",
            font=("Arial", 30),
            text_color="#dfe6e9",
        )
        label.grid(row=0, column=0, columnspan=3, sticky="nsew", pady=(20, 10))

        fetch_button = ctk.CTkButton(
            frame,
            text="Dohvati Tečajnu Listu",
            font=("Arial", 20),
            command=lambda: ExchangeRate.start_fetching(frame),
        )
        fetch_button.grid(row=1, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        ExchangeRate.result_textbox = ctk.CTkTextbox(
            frame, width=400, height=200, font=("Arial", 14)
        )
        ExchangeRate.result_textbox.grid(
            row=2, column=0, columnspan=3, sticky="nsew", pady=(10, 10), padx=(10, 10)
        )

        frame.grid_columnconfigure(0, weight=1)
        frame.grid_columnconfigure(1, weight=1)
        frame.grid_columnconfigure(2, weight=1)

    @staticmethod
    def start_fetching(frame):
        threading.Thread(
            target=ExchangeRate.fetch_exchange_rates, args=(frame,), daemon=True
        ).start()

    @staticmethod
    def fetch_exchange_rates(frame):
        ExchangeRate.result_textbox.delete("1.0", "end")
        ExchangeRate.result_textbox.insert("end", "Dohvaćanje tečajne liste...\n")
        try:
            url = "https://api.hnb.hr/tecajn/v1"
            response = requests.get(url, timeout=10)

            if response.status_code == 200:
                rates = response.json()

                result = "Tečajna lista:\n"
                result += f"{'Valuta':<10}{'Kupovni':<15}{'Srednji':<15}{'Prodajni':<15}\n"
                result += "-" * 50 + "\n"

                for rate in rates:
                    result += (
                        f"{rate['valuta']:<10}{rate['kupovni_tecaj']:<15}{rate['srednji_tecaj']:<15}{rate['prodajni_tecaj']:<15}\n"
                    )

                ExchangeRate.result_textbox.delete("1.0", "end")
                ExchangeRate.result_textbox.insert("end", result)
            else:
                raise Exception(f"HTTP Pogreška: {response.status_code}")

        except requests.exceptions.RequestException as e:
            ExchangeRate.result_textbox.delete("1.0", "end")
            ExchangeRate.result_textbox.insert(
                "end", f"Greška pri dohvaćanju: {str(e)}\n"
            )
        except Exception as e:
            ExchangeRate.result_textbox.delete("1.0", "end")
            ExchangeRate.result_textbox.insert(
                "end", f"Neočekivana pogreška: {str(e)}\n"
            )
