import re

# без re.DOTALL мы сломаемся на первом же переносе строки
number_regex = re.compile(r"(-?(?:0|[1-9]\d*)(?:\.\d+)?(?:[eE][+-]?\d+)?)\s*(.*)", re.DOTALL)