import re
from idlelib.replace import replace

path = "schedule.yml"
yaml_file = open(path, "r", encoding="utf-8").read()
i = 0
symbol = yaml_file[i]
skip = ['\n', '\t', '\v', ' ', ':', ',', '-'] ################### возможно "-" не нужно
digits = '0123456789' ############### минус добавить мб "-"??? да и вообще '1234567890-eE.' ????
yaml_len = len(yaml_file)
def yaml_parce(): ########### добавить xml_literal()
    global i
    global symbol
    r = None
    while i < yaml_len:
        if symbol in skip:
            i += 1
        elif symbol in digits:
            r = yaml_number()
        elif symbol == "-":
            r = yaml_dict_list()
        elif symbol == '\"':
            r = yaml_string()
    return r


def yaml_number(): ############ добавить обработку float мб??????
    global i
    global symbol
    r = ''

    while (i < yaml_len) and (symbol in digits):
        r += symbol
        i += 1
    return r


def yaml_string():
    global i
    global symbol
    r = ''

    while (i < yaml_len) and (not re.search(r'".*?[^\\]"', r)):
        r += symbol
        i += 1
    return xml_escape(r[1:-1])

def yaml_dict():





xml_replace = [
    ('&', '&amp;'),
    ('<', '&lt;'),
    ('>', '&gt;'),
    ('\'', '&apos;'),
    ('\"', '&quot;')
]
def xml_escape(line):
    for yaml_not_escape_symbol, xml_escape_symbol in xml_replace:
        replace(yaml_not_escape_symbol, xml_escape_symbol)
    return line


