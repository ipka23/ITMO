path = "test2.yml"
file = open(path, 'r', encoding='utf-8')
yaml_lines = [line.strip("-") for line in file.readlines()]

# def yaml_dict():
#     d = {}
#     for i in range(len(yaml_lines) - 1):
#         if len(yaml_lines[i+1].split(': ')) == 1:  # проверяем является ли строка концом списка
#             return d
#         key_and_value = yaml_lines[i].split(': ')
#         key = key_and_value[0]
#         value = key_and_value[1] if len(key_and_value) > 1 else ""
#         if len(yaml_lines[i].split(': ')) == 1:  # проверяем является ли строка началом списка
#             d[key] = yaml_dict()
#         else:
#             d[key] = value
#         return d
# yaml_dict()
def yaml_list():
    l = []
    for i in range(len(yaml_lines) - 1):
        if len(yaml_lines[i].split(': ')) == 1:
            l.append(yaml_list())
        elif len(yaml_lines[i+1].split(': ')) == 1 or i == len(yaml_lines) - 1:  # проверяем является ли строка концом списка
            return l
        else:
            l.append(yaml_lines[i])

yaml_list()
#
# path = "test2.yml"
# with open(path, 'r', encoding='utf-8') as file:
#     yaml_lines = [line.strip() for line in file.readlines()]  # Убираем пробелы и символы
#
#
# def yaml_list():
#     l = []
#     i = 0  # Индекс для прохода по yaml_lines
#     while i < len(yaml_lines):
#         line = yaml_lines[i].strip()
#
#         if line.startswith("-"):  # Проверяем, является ли строка элементом списка
#             item = line[1:].strip()  # Убираем символ '-'
#             l.append(item)  # Добавляем элемент в список
#         elif ':' in line:  # Если строка содержит ':', это ключ-значение
#             key_value = line.split(': ', 1)  # Разделяем по первому ':'
#             key = key_value[0]
#             value = key_value[1].strip() if len(key_value) > 1 else ""
#             l.append({key: value})  # Добавляем словарь с ключом и значением
#         else:
#             break  # Выходим из цикла, если строка не соответствует формату
#
#         i += 1  # Увеличиваем индекс
#
#     return l
#
#
# result = yaml_list()
# print(result)
