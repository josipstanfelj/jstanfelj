import datetime
import customtkinter as ctk
import random
import sys
import threading
from gpt4all import GPT4All

class FortuneTeller:
    
    @staticmethod
    def make_screen(frame):
        
        for widget in frame.winfo_children():
            widget.destroy()

        label = ctk.CTkLabel(frame,
            text="Fortune Teller",
            font=("Arial", 30),
            text_color="#dfe6e9")
        label.grid(row=0, column=0, columnspan=4, sticky="nsew")
        
        labelText = ctk.CTkLabel(frame, font=("Arial", 20), text="Your name:")
        labelText.grid(row=1, column=0, pady=(20, 10), sticky="ew", columnspan=2)
        textBox = ctk.CTkEntry(frame, font=("Arial", 20), height=1)
        textBox.grid(row=2, column=1, columnspan=2, sticky="ew")
        
        button = ctk.CTkButton(frame,
            text="Read my fortune",
            font=("Arial", 20),
            command=lambda: FortuneTeller.read_fortune(sum(ord(char) for char in textBox.get()) if textBox.get() 
                                                    else random.randint(-sys.maxsize - 1, sys.maxsize), output),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        button.grid(row=3, column=0, columnspan=4, sticky="ew", padx=100, pady=20)
        
        output = ctk.CTkTabview(
            frame,
            text_color="#dfe6e9",
            segmented_button_selected_color="#6c5ce7",
            segmented_button_selected_hover_color="#a29bfe")
        output.grid(row=4, column=0, columnspan=4, sticky="nsew", padx=20, pady=20)
        
        
        frame.grid_columnconfigure(0, weight=1)
        frame.grid_columnconfigure(1, weight=1)
        frame.grid_columnconfigure(2, weight=1)
        frame.grid_columnconfigure(3, weight=1)
        
    def read_fortune(seed, output):
        print(seed)
        compound_words_path= "assets/files/compound_words.txt"
        nouns_path= "assets/files/nouns.txt"
        prepositions_path= "assets/files/prepositions.txt"
        verbs_path= "assets/files/verbs.txt"
        compound_words = []
        nouns = []
        prepositions = []
        verbs = []
        
        with open(compound_words_path) as f:
            compound_words = [line.strip() for line in f.readlines()]
        with open(nouns_path) as f:
            nouns = [line.strip() for line in f.readlines()]
        with open(prepositions_path) as f:
            prepositions = [line.strip() for line in f.readlines()]
        with open(verbs_path) as f:
            verbs = [line.strip() for line in f.readlines()]
        
        seed = seed + sum(ord(char) for char in str(datetime.datetime.now().date()))
        random.seed(seed)
        sentence = FortuneTeller.get_random_sentence(compound_words, nouns, prepositions, verbs)
        
        print(sentence, )
        try:
            output.delete("Fortune")
        except:
            pass
        threading.Thread(target=FortuneTeller.get_model_response, args=(sentence, output)).start()
        
    def get_model_response(sentence, output):
        response = FortuneTeller.get_explanation(sentence)
        result_tab = output.add("Fortune")
        textbox = ctk.CTkTextbox(result_tab, font=("Arial", 20))
        textbox.pack(fill="both", expand=True)
        
        textbox.insert("end", sentence + "\n\n")
        tokens = iter(response[0])
        for token in tokens:
            if token == "<|assistant|>":
                token = "Fortune Teller: "
            if token == "<|endoftext|>":
                for token in tokens:
                    pass
                response[1].close()
                break
            textbox.configure(state="normal")
            textbox.insert("end", token)
            textbox.configure(state="disabled")
            textbox.update_idletasks()
            
        button = ctk.CTkButton(result_tab,
            text="Delete",
            font=("Arial", 20),
            command=lambda: output.delete("Fortune"),
            text_color="#dfe6e9",
            fg_color="#6c5ce7",
            border_color="#a29bfe",
            hover_color="#a29bfe")
        button.pack(side="bottom", pady=10)
    
    def get_random_word(word_list):
        return word_list[random.randint(0, len(word_list) - 1)]
    
    def get_random_sentence(compound_words, nouns, prepositions, verbs):
        fortune = ""
        
        if  random.randint(0, 1):
            fortune += FortuneTeller.get_random_word(compound_words) + " "
        else:
            fortune += FortuneTeller.get_random_word(nouns) + " "
        
        fortune += FortuneTeller.get_random_word(verbs) + " "
        
        if random.randint(0, 1):
            fortune += FortuneTeller.get_random_word(prepositions) + " "
        
        if random.randint(0, 1):
            fortune += FortuneTeller.get_random_word(compound_words) + " "
        else:
            fortune += FortuneTeller.get_random_word(nouns) + " "
            
        return fortune
    
    def get_explanation(sentence):
        progress_window = ctk.CTkToplevel()
        progress_window.title("Fortune reader")
        progress_window.geometry("300x100")
        progress_window.attributes("-topmost", True)

        label = ctk.CTkLabel(progress_window, text="Reading your fortune, please wait...")
        label.pack(pady=10)

        progress_bar = ctk.CTkProgressBar(progress_window, width=250, fg_color="#dfe6e9", progress_color="#6c5ce7", mode="indeterminate")
        progress_bar.pack(pady=10)
        progress_bar.start()

        model = GPT4All(model_name="Phi-3-mini-4k-instruct.Q4_0.gguf", model_path="assets/models", device="gpu")
        instructions = ("You are a fortune teller. These are the tarot cards you drew today: " + sentence + ". In 30 words or less, explain the fortune. Do not write anything else.")
        response = model.generate(prompt=instructions, streaming=True, top_k=1, max_tokens=100)

        progress_window.destroy()
        return response, model

