def Task_4():
    def parse(yaml_lines):
        line_mass = []
        stack = []
        line_numb = 0
        while line_numb < len(yaml_lines):
            line = yaml_lines[line_numb].rstrip()
            if not line:
                line_numb += 1
                continue

            tab = level_tab(line)
            key, value = parse_key_value_mass(line)
            line_dict = {"tab": tab, "key": key, "value": value, "children": []}
            while stack and stack[-1]["tab"] >= tab:
                stack.pop()

            if stack:
                stack[-1]["children"].append(line_dict)
            else:
                line_mass.append(line_dict)

            stack.append(line_dict)
            line_numb += 1

        return line_mass

    # Функция для подсчёта уровня вложенности
    def level_tab(line):
        # подсчёт пробелов
        flag = 0
        tab = 0
        for i in range(len(line)):
            if line[i] == " " and flag == 0:
                tab += 1
            if line[i] != " ":
                flag = 1
        return (tab)

        # функция для разбиения строки на ключ и значение

    def parse_key_value_mass(line):
        if (":" in line) and "-" not in line:
            key, value = line.split(":", 1)
            key = key.strip() #############
            value = value.strip()
            return key, value
        # обработка массивов
        elif (":" in line) and "-" in line:
            key, value = line.split(":", 1)
            key = key.replace("-", "", 1).strip() #############
            value = value.strip()
            return key, value
        elif ":" not in line:
            line = line.replace("-", "", 1)
            line = line.lstrip()
            key = line
            value = line
            return key, value


        # функция для записи в XML

    def to_xml(data, root_tag="root"):
        # print(data)

        def build_xml(elements, level=0):
            xml_str = ""
            indent = "  " * level
            for element in elements:
                key = element["key"]
                value = element["value"]
                children = element["children"]
                if children:
                    # Рекурсивный вызов для вложенных элементов
                    xml_str += f"{indent}<{key}>\n"
                    xml_str += build_xml(children, level + 1)
                    xml_str += f"{indent}</{key}>\n"
                # Элементы без дочерних узлов
                else:
                    xml_str += f"{indent}<{key}>{value}</{key}>\n"

            return xml_str

        xml_str = f"<{root_tag}>\n"
        xml_str += build_xml(data)
        xml_str += f"</{root_tag}>\n"

        return xml_str

    with open('simple_schedule.yml', 'r', encoding='utf-8') as file_yml:
        content = file_yml.read().replace("\"", "")
        lines = content.splitlines()

        YAML_parsed = parse(lines)
        xml_data = to_xml(YAML_parsed)
        # print(xml_data)

    with open('schedule.xml', 'w', encoding='utf-8') as file:
        file.write(xml_data)


Task_4()