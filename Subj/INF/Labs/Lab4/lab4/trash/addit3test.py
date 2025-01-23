def parse_yaml(yaml_lines):
    dict_lines = []
    l = []
    i = 0
    while i < len(yaml_lines):
        line = yaml_lines[i].rstrip()
        if line.strip() == "":
            i += 1
            continue
        indent = indent_level(line)
        key, value = line_split(line)
        line_dict = {"indent": indent, "key": key, "value": value, "children": []}
        while l and l[-1]["indent"] >= indent: # если текущий уровень <= уровня последнего элемента в l, то удаляем этот элемент из l
            l.pop()
        if l: # если l не пустой, добавляем текущий элемент как вложенный к последнему элементу l
            l[-1]["children"].append(line_dict)
        else:
            dict_lines.append(line_dict)
        l.append(line_dict)
        i += 1
    return dict_lines


def indent_level(line):
    flag = 0
    indent = 0
    for i in range(len(line)):
        if line[i] == " " and flag == 0:
                indent += 1
        if line[i] != " ":
            flag = 1
    return indent


def line_split(line):
    if ":" in line:
        key, value = line.split(":", 1)
        return key.strip(), value.strip()


lvl = 0
def to_xml(dict_elements):
    s = ""
    global lvl
    indent = "  " * lvl
    for key, value in dict_elements.items():
        if isinstance(value, dict):
            children = value["children"]
        if isinstance(value, list):
            for item in value:
                if isinstance(item, dict):
                    children = value["children"]
        if children: # если у элемента есть дочерние элементы
            s += f"{indent}<{key}>\n"
            lvl += 1
            s += to_xml(children)
            lvl -= 1
            s += f"{indent}</{key}>\n"
        else: # если дочерних элементов нет
            s += f"{indent}<{key}>{value}</{key}>\n"
    return s


with open('../schedule.yml', 'r', encoding='utf-8') as file_yml:
    file = file_yml.read().replace('\"', '').replace(" -", "")
    lines = file.splitlines()

    YAML_parsed = parse_yaml(lines)
    xml_data = to_xml(YAML_parsed)


with open('../schedule.xml', 'w', encoding='utf-8') as file:
    file.write(xml_data)