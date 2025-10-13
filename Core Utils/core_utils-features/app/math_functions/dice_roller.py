import customtkinter as ctk
import random

class DiceRoller:
    count = 0
    
    @staticmethod
    def make_screen(frame):
        #Clear the frame
        for widget in frame.winfo_children():
            widget.destroy()

        label = ctk.CTkLabel(frame,
            text="Dice Roller",
            font=("Arial", 30),
            text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=4, sticky="nsew")
        
        labelText = ctk.CTkLabel(frame, font=("Arial", 20), text="Dice value:")
        labelText.grid(row=1, column=0, pady=(20, 10), sticky="ew")
        textBox = ctk.CTkTextbox(frame, font=("Arial", 20), height=1)
        textBox.grid(row=2, column=0, columnspan=2)
        
        labelText = ctk.CTkLabel(frame, font=("Arial", 20), text="Number of dice:")
        labelText.grid(row=1, column=2, pady=(20, 10), sticky="ew")
        textBox2 = ctk.CTkTextbox(frame, font=("Arial", 20), height=1)
        textBox2.grid(row=2, column=2, columnspan=2)


        output = ctk.CTkTabview(
            frame,
            text_color="#dfe6e9",
            segmented_button_selected_color="#6c5ce7",
            segmented_button_selected_hover_color="#a29bfe")
        output.grid(row=4, column=0, columnspan=4, sticky="nsew", padx=20, pady=20)
        
        button = ctk.CTkButton(frame,
            text="Roll",
            font=("Arial", 20),
            command=lambda: DiceRoller.roll(int(textBox.get("1.0", "end-1c")), int(textBox2.get("1.0", "end-1c")), output),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        button.grid(row=3, column=0, columnspan=4, sticky="ew", padx=100, pady=20)
        
        
        frame.grid_columnconfigure(0, weight=1)
        frame.grid_columnconfigure(1, weight=1)
        frame.grid_columnconfigure(2, weight=1)
        frame.grid_columnconfigure(3, weight=1)
    
    
    def roll(dice, times, output):
        DiceRoller.count += 1
        count = DiceRoller.count
        rolledDice = dict()
        listDice = []
        sum = 0
        
        #rolling dice
        for i in range(times):
            value = random.randint(1, dice)
            sum += value
            listDice.append(value)
            if value in rolledDice:
                rolledDice[value] += 1
            else:
                rolledDice[value] = 1

        #calculating average, min, max and median
        avg = sum/times
        min = (None, float('inf'))
        max = (None, float('-inf'))
        for key, value in rolledDice.items():
            if value < min[1]:
                min = (key, value)
            if value > max[1]:
                max = (key, value)
        if(len(listDice) % 2 == 0):
            median = (sorted(listDice)[times//2] + sorted(listDice)[times//2 - 1])/2
        else:
            median = sorted(listDice)[times//2]
        
        results_tab = output.add("Results " + str(count))
        
        #displaying results
        labelValue = ctk.CTkLabel(results_tab, text="Value", font=("Arial", 20))
        labelNumber = ctk.CTkLabel(results_tab, text="Rolled", font=("Arial", 20))
        labelPercentage = ctk.CTkLabel(results_tab, text="Percentage", font=("Arial", 20))
        labelValue.grid(row=0, column=0, sticky="nsew", padx=20)
        labelNumber.grid(row=0, column=1, sticky="nsew", padx=20)
        labelPercentage.grid(row=0, column=2, sticky="nsew", padx=20)
        b=1
        for key in sorted(rolledDice):
            val = ctk.CTkLabel(results_tab, text=f"{key}", font=("Arial", 20))
            number = ctk.CTkLabel(results_tab, text=f"{rolledDice[key]}", font=("Arial", 20))
            precentage = ctk.CTkLabel(results_tab, text=f"{(rolledDice[key]/times)*100:.2f}%", font=("Arial", 20))
            val.grid(row=b, column=0, sticky="nsew", padx=20)
            number.grid(row=b, column=1, sticky="nsew", padx=20)
            precentage.grid(row=b, column=2, sticky="nsew", padx=20)
            b+=1
        minLabel = ctk.CTkLabel(results_tab, text=f"Min: Value - {min[0]} (Times - {min[1]})", font=("Arial", 20))
        minLabel.grid(row=b, column=0, columnspan=3, sticky="nsew", pady=20)
        b+=1
        maxLabel = ctk.CTkLabel(results_tab, text=f"Max: Value - {max[0]} (Times - {max[1]})", font=("Arial", 20))
        maxLabel.grid(row=b, column=0, columnspan=3, sticky="nsew", pady=20)
        b+=1
        sumLabel = ctk.CTkLabel(results_tab, text=f"Sum: {sum}", font=("Arial", 20))
        sumLabel.grid(row=b, column=0, columnspan=3, sticky="nsew", pady=20)
        b+=1
        avgLabel = ctk.CTkLabel(results_tab, text=f"Average: {avg:.2f}", font=("Arial", 20))
        avgLabel.grid(row=b, column=0, columnspan=3, sticky="nsew", pady=20)
        b+=1
        medianLabel = ctk.CTkLabel(results_tab, text=f"Median: {median}", font=("Arial", 20))
        medianLabel.grid(row=b, column=0, columnspan=3, sticky="nsew", pady=20)
        b+=1

        button = ctk.CTkButton(results_tab,
            text="Delete",
            font=("Arial", 20),
            command=lambda: output.delete(f"Results {count}"),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        button.grid(row=b, column=0, columnspan=3, sticky="ew", padx=100, pady=20)
        
        results_tab.grid_columnconfigure(0, weight=1)
        results_tab.grid_columnconfigure(1, weight=1)
        results_tab.grid_columnconfigure(2, weight=1)
        return 0