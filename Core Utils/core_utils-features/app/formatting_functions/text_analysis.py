import customtkinter as ctk
from tkinter import filedialog
import os
import re

class TextAnalysis:
    @staticmethod
    def make_screen(frame):
        for widget in frame.winfo_children():
            widget.destroy()

        label = ctk.CTkLabel(
            frame, text="Text Analysis", font=("Arial", 30), text_color="#dfe6e9"
        )
        label.grid(row=0, column=0, columnspan=3, sticky="nsew", pady=(20, 10))

        select_button = ctk.CTkButton(
            frame,
            text="Upload Text File",
            font=("Arial", 20),
            command=lambda: TextAnalysis.select_file(frame),
        )
        select_button.grid(row=2, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        TextAnalysis.result_label = ctk.CTkLabel(
            frame, text="", font=("Arial", 16), wraplength=500, justify="left"
        )
        TextAnalysis.result_label.grid(row=3, column=0, columnspan=3, sticky="nsew", pady=(10, 10))

        frame.grid_columnconfigure(0, weight=1)
        frame.grid_columnconfigure(1, weight=1)
        frame.grid_columnconfigure(2, weight=1)

    @staticmethod
    def select_file(frame):
        file = filedialog.askopenfilename(
            filetypes=[("Text files", "*.txt")]
        )

        if not file:
            TextAnalysis.result_label.configure(text="No file selected.")
            return

        try:
            with open(file, "r", encoding="utf-8") as f:
                content = f.read()
                result = TextAnalysis.analyze_text(content)
                TextAnalysis.display_result(result)
        except Exception as e:
            TextAnalysis.result_label.configure(
                text=f"Error reading file: {str(e)}"
            )

    @staticmethod
    def analyze_text(text):
        word_count = len(re.findall(r"\b\w+\b", text))
        char_count = len(text)
        sentence_count = len(re.findall(r"[.!?]", text))

        if word_count > 0:
            avg_sentence_length = word_count / sentence_count if sentence_count > 0 else word_count
            syllable_count = sum(TextAnalysis.count_syllables(word) for word in re.findall(r"\b\w+\b", text))
            readability_score = 206.835 - 1.015 * avg_sentence_length - 84.6 * (syllable_count / word_count)
        else:
            avg_sentence_length = 0
            readability_score = 0

        return {
            "word_count": word_count,
            "char_count": char_count,
            "sentence_count": sentence_count,
            "readability_score": readability_score,
        }

    @staticmethod
    def count_syllables(word):
        word = word.lower()
        vowels = "aeiouy"
        syllables = 0
        prev_char_was_vowel = False

        for char in word:
            if char in vowels:
                if not prev_char_was_vowel:
                    syllables += 1
                prev_char_was_vowel = True
            else:
                prev_char_was_vowel = False

        if word.endswith("e"):
            syllables -= 1

        return max(syllables, 1)

    @staticmethod
    def display_result(result):
        result_text = (
            f"Word Count: {result['word_count']}\n"
            f"Character Count: {result['char_count']}\n"
            f"Sentence Count: {result['sentence_count']}\n"
            f"Readability Score: {result['readability_score']:.2f}"
        )
        TextAnalysis.result_label.configure(text=result_text)
