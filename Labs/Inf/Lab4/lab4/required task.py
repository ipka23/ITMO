path = "schedule.yml"
file = open(path, 'r', encoding='utf-8')
yaml_lines = [line.rstrip().strip("-\"") for line in file.readlines()]
for l in yaml_lines:
    print(l)
i = 0

#
# def yaml_dict():
#     global i
#     d = {}
#     while i < len(yaml_lines) - 2:
#         i += 1 # идём до конца файла(до последней строки) xml_index <= 81 number_of_xml_lines = 82
#         if yaml_lines[i+2].strip()[0] == "-" or i == len(yaml_lines) - 2:
#             return d
#         key, value = yaml_lines[i].split(': ', maxsplit=1)
#         if yaml_lines[i].strip()[0] == "-":  # проверяем является ли строка началом списка
#             d[key] = yaml_dict()
#         else:
#             d[key] = value
# yaml_dict()


# def yaml_list():
#     global i
#     l = []
#     while i < len(yaml_lines) - 2:
#         i += 1
#         if yaml_lines[i] == '-':
#             l.append(yaml_list())
#         elif yaml_lines[i+2].strip()[0] == "-" or i == len(yaml_lines) - 2:
#             return l
#         else:
#             l.append(yaml_lines[i])
#
# yaml_list()