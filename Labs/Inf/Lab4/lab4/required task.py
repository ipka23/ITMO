path = "schedule.yml"
yaml_file = open(path, "r", encoding="utf-8")
yaml_lines = [line.strip('-') for line in yaml_file]
i = 0
s = ""
def to_yaml():
    global s
    global i
    while i < len(yaml_lines):
        line_split = yaml_lines[i].strip().replace("\"", "").split(':', 1)
        space = len(yaml_lines[i]) - len(yaml_lines[i].strip())
        if yaml_lines[i] == "":
            pass
        elif len(line_split) == 2 and line_split[1] == '': #начало списка
            key = line_split[0]
            s += ' ' * space + f'<{key}>\n'
            i += 1
            if yaml_lines[i] == yaml_lines[i+1]:
                s += ' ' * space + f'</{key}>\n'
                to_yaml()
            to_yaml()
            s += ' ' * space + f'</{line_split[0]}>\n'
        elif len(line_split) == 2 and line_split[1] != '': #элемент списка
            key, value = line_split
            s += ' ' * space + f'<{key}>{value.strip()}</{key}>\n'
        i += 1
    return s.strip()

with open('schedule.xml', 'w', encoding='utf-8') as f:
    result = to_yaml()
    f.write(result)
yaml_file.close()