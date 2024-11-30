import re
i = 0
s = ""
def add_2():
    with open("schedule.yml", "r", encoding="utf-8") as yaml_file:
        yaml_lines = [line for line in yaml_file]
    def strip(string):
        return string.strip()
    def to_xml():
        global i
        global s
        while i < len(yaml_lines):
            line = re.sub('\"', '', yaml_lines[i])
            space = len(line) - len(strip(line))

            match_list = re.fullmatch(r'(\w+):', strip(line))  # начало списка
            match_element = re.fullmatch(r'(\w+):( \w+.+)', strip(line)) # элемент списка

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
        return strip(s)

    with open('schedule.xml', 'w', encoding='utf-8') as xml_file:
        result = to_xml()
        xml_file.write(result)

add_2()