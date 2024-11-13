path = "schedule.yml"
file = open(path, "r", encoding="utf-8").readlines()
yaml_lines = [line.strip('-') for line in file]
i = 0
s = ""


def yaml_list():
    global s
    global i
    while i < len(yaml_lines):
        space = len(yaml_lines[i]) - len(yaml_lines[i].strip())
        line_parts = yaml_lines[i].strip().replace("\"", "").split(":", 1)
        if len(line_parts) == 2 and line_parts[1].strip() != "": # если значения 2, значит это элемент списка
            key, value = line_parts
            s += " " * space + f'<{key}>{value.strip()}</{key}>\n'
        elif len(line_parts) == 2 and line_parts[1].strip() == "": # если значение только 1, значит это начало списка
            s += " " * space + f"<{line_parts[0].strip()}>\n"
            i += 1
            yaml_list()
            s += " " * space + f"</{line_parts[0].strip()}>\n"
        i += 1
    return s


result = yaml_list()
print(result)