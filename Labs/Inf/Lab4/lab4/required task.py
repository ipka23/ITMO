path = "C:/Users/ilyai/OneDrive/Рабочий стол/ITMO/Labs/Inf/Lab4/lab4/schedule.yml"
file = open(path, 'r', encoding='utf-8')
yaml_lines = [line.rstrip() for line in file.readlines()]
# for l in xml_lines:
#     print(l)



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


def xml_list():
    global xml_index

    res = []

    while xml_index < len(yaml_lines):
        xml_index += 1

        if yaml_lines[xml_index] == '-':
            res.append(xml_list())
        elif yaml_lines[xml_index] == ']':
            return res
        else:
            res.append(yaml_lines[xml_index])