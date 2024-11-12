path = "test2.yml"
file = open(path, 'r', encoding='utf-8')
yaml_lines = [line.strip("- ").replace('"', "").strip() for line in file.readlines()]
i = 0
# for l in yaml_lines:
#     print(l)
# def yaml_dict():
#     d = {}
#     open_tags = []
#     global i
#     while i < len(yaml_lines) - 2:
#         i += 1
#         if len(yaml_lines[i+1].split(': ')) == 1 or i == len(yaml_lines):  # проверяем является ли строка концом списка
#             return d
#         key_and_value = yaml_lines[i].split(': ')
#         key = key_and_value[0]
#         value = key_and_value[1] if len(key_and_value) > 1 else ""
#         if len(yaml_lines[i].split(': ')) == 1: # проверяем является ли строка началом списка
#             open_tags.append(yaml_lines[i].split(': ')[0])
#             d[key] = yaml_dict()
#         else:
#             d[key] = value
        # print(f'<{key}>{value}</{key}>')
#         print(d, open_tags)
# yaml_dict()

# def yaml_list():
#     l = []
#     for i in range(len(yaml_lines) - 1):
#         if len(yaml_lines[i].split(': ')) == 1:
#             l.append(yaml_list())
#         elif len(yaml_lines[i+1].split(': ')) == 1 or i == len(yaml_lines) - 1:  # проверяем является ли строка концом списка
#             return l
#         elif len(yaml_lines[i].split(': ')) > 1:
#             l.append(yaml_lines[i])
#
#
# yaml_list()


def json_dict():
    global i

    res = {}

    while i < len(yaml_lines) - 2:
        i += 1
        if len(yaml_lines[i+1].split(':', )) == 1 or i + 1 == len(yaml_lines):
            return res
        if ":" not in yaml_lines[i]:
            yaml_lines[i] =   ,]
        key, value = yaml_lines[i].split(':', 1)
        if value == '':
            res[key] = json_dict()
        else:
            res[key] = value
        print(res)
json_dict()