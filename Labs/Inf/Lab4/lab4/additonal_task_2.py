import re
path = "schedule.yml"
yaml_file = open(path, "r", encoding="utf-8")
yaml_lines = [line.strip('-') for line in yaml_file]
i = 0
s = ""
def to_yaml():
    global s
    global i
    while i < len(yaml_lines):
        line_split = re.sub(r'\"', '', yaml_lines[i]).strip().split(':', 1)
        space = len(yaml_lines[i]) - len(yaml_lines[i].strip())
        if yaml_lines[i] == "":
            pass
        elif len(line_split) == 2 and line_split[1] == '': #начало списка
            key = line_split[0]
            s += ' ' * space + f'<{key}>\n'
            i += 1
            to_yaml()
            s += ' ' * space + f'</{line_split[0]}>\n'
        elif len(line_split) == 2 and line_split[1] != '': #элемент списка
            key, value = line_split
            s += ' ' * space + f'<{key}>{value.strip()}</{key}>\n'
        i += 1
    return s.strip()

with open('schedule.xml', 'w', encoding='utf-8') as xml_file:
    result = to_yaml()
    xml_file.write(result)
yaml_file.close()