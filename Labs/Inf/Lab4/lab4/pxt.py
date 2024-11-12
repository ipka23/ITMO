path = "schedule.yml"
with open(path, 'r', encoding='utf-8') as file:
    yaml_lines = [line.strip() for line in file.readlines()]  # Убираем пробелы и символы


def yaml_list():
    l = []
    i = 0  # Индекс для прохода по yaml_lines
    while i < len(yaml_lines):
        line = yaml_lines[i].strip()
        if "-" in line:  # Проверяем, является ли строка элементом списка
            item = line.replace("-", "", 1)# Убираем символ '-'
            l.append(item)  # Добавляем элемент в список
        elif ':' in line:  # Если строка содержит ':', это ключ-значение
            key_value = line.replace("\"", '').split(': ', maxsplit=1)  # Разделяем по первому ':'
            key = key_value[0]
            value = key_value[1].strip() if len(key_value) > 1 else ""
            l.append({key: value})  # Добавляем словарь с ключом и значением
        else:
            break  # Выходим из цикла, если строка не соответствует формату
        i += 1
    return l
xml_file = open('schedule.xml', 'w', encoding='utf-8')
xml_space = 1
def xml_list(name, array):
    global xml_space
    for item in array:
        xml_file.write('\t' * xml_space + f'<{name}>{item}</{name}>' + '\n')

xml_file.write('<?xml version="1.0" encoding="UTF-8"?>' + '\n')

xml_list(yaml_list())

