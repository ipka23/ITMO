path = "C:/Users/ilyai/OneDrive/Рабочий стол/ITMO/Labs/Inf/Lab4/lab4/test2.yml"
file = open(path, 'r', encoding='utf-8')
yaml_lines = [
    "name: John Doe",
    "age: 30",
    "hobbies:",
    "- reading",
    "- traveling",
    "education:",
    "- degree: Bachelor",
    "  major: Computer Science",
    "- degree: Master",
    "  major: Data Science"
]
def yaml_dict():
    d = {}
    for i in range(len(yaml_lines) - 2): # идём до конца файла(до последней строки) xml_index <= 81 number_of_xml_lines = 82
        key, value = yaml_lines[i].split(': ')
        if yaml_lines[i].strip()[0] == "-":  # проверяем является ли строка началом списка
            d[key] = yaml_dict()
        else:
            d[key] = value
        if yaml_lines[i+2].strip()[0] == "-" or i == len(yaml_lines) - 2:
            return d
yaml_dict()