lvl = 0
def add_3():
    def parse_yaml(yaml_lines):
        dict_lines = []
        stack = []
        i = 0
        while i < len(yaml_lines):
            line = yaml_lines[i].rstrip()
            if line.strip() == "":
                i += 1
                continue
            indent = indent_level(line)
            key, value = line_split(line.strip())
            line_dict = {"indent": indent, "key": key, "value": value, "children": []}
            while stack and stack[-1]["indent"] >= indent:  # если уровень отступа последнего элемента в stack >= текущего уровня, то удаляем этот элемент из stack
                stack.pop()
            if stack:  # если stack не пустой, добавляем текущий элемент как вложенный к последнему элементу stack
                stack[-1]["children"].append(line_dict)
            else:
                dict_lines.append(line_dict)
            stack.append(line_dict)
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
            key, value = line.strip().split(":", 1)
            return key.strip(), value.strip()



    def to_xml(dict_elements):
        s = ""
        global lvl
        indent = "  " * lvl
        for element in dict_elements:
            key = element["key"]
            value = element["value"]
            children = element["children"]
            if children:  # если у элемента есть дочерние элементы
                s += f"{indent}<{key}>\n"
                lvl += 1
                s += to_xml(children)
                lvl -= 1
                s += f"{indent}</{key}>\n"
            else:  # если дочерних элементов нет
                s += f"{indent}<{key}>{value}</{key}>\n"
        return s

    with open('yaml_org.yml', 'r', encoding='utf-8') as file_yml:
        file = file_yml.read().replace('\"', '').replace(" -", "")
        lines = file.splitlines()

        parsed_yaml = parse_yaml(lines)
        xml_data = to_xml(parsed_yaml)

    with open('schedule.xml', 'w', encoding='utf-8') as file:
        file.write(xml_data)

add_3()