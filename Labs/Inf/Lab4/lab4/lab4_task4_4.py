def Task_4():
    def parse(yaml_lines):
        line_mass = []
        stack = []
        line_numb = 0 
        while line_numb< len(yaml_lines):
            line = yaml_lines[line_numb].rstrip()
            if not line:
                line_numb +=1
                continue
            
            tab =level_tab(line)
            key, value = parse_key_value_mass(line)
            line_dict ={"tab": tab, "key":key, "value": value, "children":[]}
            while stack and stack[-1]["tab"] >= tab:
                stack.pop()
            
            if stack:
                stack[-1]["children"].append(line_dict)
            else:
                line_mass.append(line_dict)

            stack.append(line_dict)
            line_numb +=1

        return line_mass

    #Функция для подсчёта уровня вложенности
    def level_tab(line):
        # подсчёт пробелов
        flag = 0
        tab = 0
        for i in range(len(line)):
            if line[i] == " " and flag == 0:
                    tab +=1
            if line[i] != " ":
                flag = 1
        return(tab)        

    #функция для разбиения строки на ключ и значение
    def parse_key_value_mass(line):

        if (":" in line) and ("[" not in line) and ("]"  not in line) and ("&" not in line) and ("<" not in line) :
            key, value = line.split(":", 1)
            key = key.strip()
            value = value.strip()
            return key, value
        #обработка массивов    
        elif "-" in line:
            line = line.replace("-", "", 1)
            line = line.lstrip()
            key = line
            value = line
            return key, value 
        elif (":" in line) and ("[" in line) and ("]" in line):
            key, value = line.split(":", 1)
            key = key.strip()
            value = value.strip()
            array_elements = [v.strip() for v in value.strip("[]").split(",")]
            return key, array_elements
        #обработка комментариев
        elif "#" in line:
            line = line.replace("#","")
            line = line.lstrip()    
            key = "#"
            value = line
            return key, value
        #обработка ссылок
        elif "&" in line:
            key, value = line.split(":", 1)
            key = key.strip()
            value = value.strip()
            return f"{key} id=\"{key}\"", value
        elif "<<:" in line and "*" in line:
            reference = line.split("*", 1)[1].strip()  # Извлечь ссылку
            key = "ref"
            value = f"#{reference}"  # Формат ссылки
            return key, value

        return line, ""    
        
    #функция для записи в XML
    def to_xml(elements, level=0):
        xml_str = ""
        indent = "  " * level
        for element in elements:
            key = element["key"]
            value = element["value"]
            children = element["children"]
            if isinstance(value, list):
                # Если значение - массив, создаём вложенные элементы
                xml_str += f"{indent}<{key}>\n"
                for item in value:
                    xml_str += f"{indent}  <item>{item}</item>\n"
                xml_str += f"{indent}</{key}>\n"
            elif children:
                # Рекурсивный вызов для вложенных элементов
                if "id=" in key:  # Проверка на наличие атрибута id
                    tag_name, attr = key.split(" ", 1)
                    xml_str += f"{indent}<{tag_name} {attr}>\n"
                    xml_str += to_xml(children, level + 1)
                    xml_str += f"{indent}</{tag_name}>\n"
                else:
                    xml_str += f"{indent}<{key}>\n"
                    xml_str += to_xml(children, level + 1)
                    xml_str += f"{indent}</{key}>\n"
            elif key == "ref":
                xml_str += f'{indent}<reference ref="{value}"/>\n'
            elif key == "#":
                xml_str += f"<!--{value}-->\n"
            else:
                # Элементы без дочерних узлов
                xml_str += f"{indent}<{key}>{value}</{key}>\n"


        return xml_str
        
        xml_str = f"<{root_tag}>\n"
        xml_str += build_xml(data)
        xml_str += f"</{root_tag}>\n"
            
        
        
        return xml_str


    with open('schedule.yml', 'r', encoding='utf-8') as file_yml:
        file = file_yml.read().replace('\"', '').replace(" -", "") 
        lines = file.splitlines()


        YAML_parsed = parse(lines)
        xml_data = to_xml(YAML_parsed)
        #print(xml_data)

    with open('schedule.xml', 'w', encoding='utf-8') as file:
        file.write(xml_data)   


Task_4()
