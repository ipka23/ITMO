path = "schedule.yml"
with open(path, "r", encoding="utf-8") as yaml_file:
    yaml_lines = [line.rstrip().replace("\"", "") for line in yaml_file]
i = 0
s = ""

def parse_yaml(indent_level):
    global i
    global s

    while i < len(yaml_lines):
        line = yaml_lines[i]
        current_indent = len(line) - len(line.lstrip())

        if current_indent < indent_level:
            return s

        if ':' in line:
            key, value = line.split(':', 1)
            key = key.strip()
            value = value.strip()

            if value == "":
                s += ' ' * current_indent + f'<{key}>\n'
                i += 1
                parse_yaml(current_indent + 2)
                s += ' ' * current_indent + f'</{key}>\n'
            else:
                s += ' ' * current_indent + f'<{key}>{value}</{key}>\n'
        i += 1

def to_xml():
    global s
    parse_yaml(0)
    return s

result = to_xml()
with open('schedule.xml', 'w', encoding='utf-8') as xml_file:
    xml_file.write(result)