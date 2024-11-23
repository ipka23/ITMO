import re
path = "schedule.yml"
with open(path, "r", encoding="utf-8") as yaml_file:
    yaml_lines = [line for line in yaml_file]
i = 0
s = ""


def to_xml():
    global i
    global s
    while i < len(yaml_lines):
        line = re.sub('\"', '', yaml_lines[i])
        space = len(line) - len(line.strip())
        match_list = re.fullmatch(r'(\w+):', line.strip())  # начало списка
        match_element = re.fullmatch(r'(\w+):( \w+.+)', line.strip())

        if match_list:
            list_name = match_list.group(1)
            s += space * ' ' + f'<{list_name}>\n'
            i += 1
            to_xml()
            s += space * ' ' + f'</{list_name}>\n'

        elif match_element:  # элемент списка
            element_name = match_element.group(1)
            element_value = match_element.group(2).strip()
            s += space * ' ' + f'<{element_name}>{element_value}</{element_name}>\n'
        i += 1
    return s.strip()

with open('schedule.xml', 'w', encoding='utf-8') as xml_file:
    result = to_xml()
    xml_file.write(result)