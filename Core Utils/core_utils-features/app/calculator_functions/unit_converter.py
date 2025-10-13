import customtkinter as ctk


class UnitConverter:
    @staticmethod
    def make_screen(frame):
        for widget in frame.winfo_children():
            widget.destroy()

        # Naslov
        label = ctk.CTkLabel(frame,
                             text="Unit Converter",
                             font=("Arial", 30),
                             text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=4, sticky="nsew", pady=(10, 20))

        # Ulaz za vrijednost
        entry = ctk.CTkEntry(frame, font=("Arial", 20), height=50)
        entry.grid(row=1, column=0, columnspan=4, sticky="nsew", padx=10, pady=10)

        # Kategorija pretvorbe
        category_var = ctk.StringVar(value="Length")
        category_menu = ctk.CTkOptionMenu(frame, values=["Length", "Mass", "Temperature"], variable=category_var)
        category_menu.grid(row=2, column=0, columnspan=4, sticky="nsew", padx=10, pady=5)

        # Izbor jedinica
        from_unit_var = ctk.StringVar(value="meters")
        to_unit_var = ctk.StringVar(value="kilometers")

        from_menu = ctk.CTkOptionMenu(frame, values=["meters", "kilometers", "centimeters", "millimeters", "miles"],
                                      variable=from_unit_var)
        to_menu = ctk.CTkOptionMenu(frame, values=["meters", "kilometers", "centimeters", "millimeters", "miles"],
                                    variable=to_unit_var)

        from_menu.grid(row=3, column=0, columnspan=2, sticky="nsew", padx=10, pady=5)
        to_menu.grid(row=3, column=2, columnspan=2, sticky="nsew", padx=10, pady=5)

        # Rezultat
        result_label = ctk.CTkLabel(frame, text="Result: ", font=("Arial", 20), text_color="#dfe6e9")
        result_label.grid(row=4, column=0, columnspan=4, sticky="nsew", pady=10)

        # Funkcija za promjenu jedinica kad se promijeni kategorija
        def update_units(*args):
            category = category_var.get()
            if category == "Length":
                units = ["meters", "kilometers", "centimeters", "millimeters", "miles", "yards"]
            elif category == "Mass":
                units = ["grams", "kilograms", "pounds", "ounces"]
            elif category == "Temperature":
                units = ["Celsius", "Fahrenheit", "Kelvin"]
            else:
                units = []

            from_menu.configure(values=units)
            to_menu.configure(values=units)

            from_unit_var.set(units[0])
            to_unit_var.set(units[1])

        # Povezivanje promjene kategorije s funkcijom
        category_var.trace("w", update_units)

        # Funkcija za pretvorbu
        def convert():
            try:
                value = float(entry.get())
                category = category_var.get()
                from_unit = from_unit_var.get()
                to_unit = to_unit_var.get()

                if category == "Length":
                    conversions = {
                        ("meters", "kilometers"): value / 1000,
                        ("meters", "centimeters"): value * 100,
                        ("meters", "millimeters"): value * 1000,
                        ("meters", "miles"): value / 1609.34,
                        ("meters", "yards"): value * 1.09361,
                    }
                    result = conversions.get((from_unit, to_unit), "Invalid Conversion")
                elif category == "Mass":
                    conversions = {
                        ("grams", "kilograms"): value / 1000,
                        ("grams", "pounds"): value / 453.592,
                        ("grams", "ounces"): value / 28.3495,
                    }
                    result = conversions.get((from_unit, to_unit), "Invalid Conversion")
                elif category == "Temperature":
                    if from_unit == "Celsius" and to_unit == "Fahrenheit":
                        result = (value * 9/5) + 32
                    elif from_unit == "Celsius" and to_unit == "Kelvin":
                        result = value + 273.15
                    elif from_unit == "Fahrenheit" and to_unit == "Celsius":
                        result = (value - 32) * 5/9
                    elif from_unit == "Fahrenheit" and to_unit == "Kelvin":
                        result = (value - 32) * 5/9 + 273.15
                    elif from_unit == "Kelvin" and to_unit == "Celsius":
                        result = value - 273.15
                    elif from_unit == "Kelvin" and to_unit == "Fahrenheit":
                        result = (value - 273.15) * 9/5 + 32
                    else:
                        result = "Invalid Conversion"
                else:
                    result = "Invalid Category"

                result_label.configure(text="Result: " + str(result))
            except Exception:
                result_label.configure(text="Error: Invalid input")

        # Gumb za pretvorbu
        convert_button = ctk.CTkButton(frame, text="Convert", font=("Arial", 20), command=convert)
        convert_button.grid(row=5, column=0, columnspan=4, sticky="nsew", padx=10, pady=10)

        # Postavljanje proporcija
        for i in range(4):
            frame.grid_columnconfigure(i, weight=1)
        for i in range(6):
            frame.grid_rowconfigure(i, weight=1)
