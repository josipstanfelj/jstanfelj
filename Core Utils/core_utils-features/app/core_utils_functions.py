from .math_functions.dice_roller import DiceRoller
from .fun_functions.fortune_teller import FortuneTeller
from .image_functions.image_metadata import ImageMetadata
from .time_functions.stopwatch import StopWatchApp
from .calculator_functions.calculator import Calculator

from .image_functions.picture_converter import ImageConverter
from .image_functions.picture_to_text_converter import ImageToTextConverterApp
from .document_conversion.document_conversion_functions import DocumentConverterApp
from .media_functions.audio_converter import AudioConverterApp
from .media_functions.video_converter import VideoConverterApp
from .account_manager_functions.account_manager import PasswordManagerApp
from .calculator_functions.science_calculator import ScientificCalculator
from .calculator_functions.unit_converter import UnitConverter
from .formatting_functions.formatting import Formatter
from .formatting_functions.cryptography import Cryptography
from .formatting_functions.text_analysis import TextAnalysis
from .fun_functions.exchange_rate import ExchangeRate

class OptionGroup:
    def __init__(self, group, options, active):
        self.group = group
        self.options = options
        self.active = active
        
    @staticmethod
    def getOptions():
        option_list = []
        option_list.append(OptionGroup("Images",
                                       {"Image Conversion": lambda frame: ImageConverter.make_screen(frame), 
                                        "Image to Text": lambda frame: ImageToTextConverterApp.make_screen(frame),
                                        "Image Metadata": lambda frame: ImageMetadata.make_screen(frame)},
                                       None))
        
        option_list.append(OptionGroup("Audio",
                                       {"Audio Conversion": lambda frame: AudioConverterApp.make_screen(frame)},
                                       None))
        
        option_list.append(OptionGroup("Video",
                                       {"Video Conversion": lambda frame: VideoConverterApp.make_screen(frame)},
                                       None))
        
        option_list.append(OptionGroup("Math",
                                       {"Calculator": lambda frame: Calculator.make_screen(frame),
                                        "Scientific Calculator": lambda frame: ScientificCalculator.make_screen(frame),
                                        "Dice Roller": lambda frame: DiceRoller.make_screen(frame),
                                        "Unit Converter": lambda frame: UnitConverter.make_screen(frame)},
                                       None))
        
        option_list.append(OptionGroup("Fun",
                                       {"Fortune Teller": lambda frame: FortuneTeller.make_screen(frame),
                                        "Exchange Rate List": lambda frame: ExchangeRate.make_screen(frame)},
                                       None))
        
        option_list.append(OptionGroup("Time",
                                       {"Stopwatch": lambda frame: StopWatchApp.make_screen(frame)},
                                       None))
        
        option_list.append(OptionGroup("Other", 
                                       {"Document conversion": lambda frame: DocumentConverterApp.make_screen(frame),
                                        "Password manager": lambda frame: PasswordManagerApp.make_screen(frame),
                                        "Text Formatting": lambda frame: Formatter.make_screen(frame),
                                        "Cryptography": lambda frame: Cryptography.make_screen(frame),
                                        "Text Analysis": lambda frame: TextAnalysis.make_screen(frame)},
                                       None))
        return option_list