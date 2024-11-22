def yaml_num(yaml_lines):
    """Парсит строки YAML и возвращает вложенный словарь."""
    stack = []
    current_dict = {}
    current_key = None
    current_indent = 0

    for line in yaml_lines:
        stripped_line = line.strip()
        if not stripped_line:  # Игнорируем пустые строки
            continue

        # Определяем уровень отступа
        indent = len(line) - len(stripped_line)

        # Если уровень отступа меньше текущего, возвращаемся к предыдущему уровню
        if indent < current_indent:
            while stack and indent < current_indent:
                current_dict = stack.pop()
                current_indent -= 2  # Предполагаем, что отступ 2 пробела

        # Разделяем ключ и значение
        if ':' in stripped_line:
            key, value = stripped_line.split(':', 1)
            if value:  # Если есть значение
                current_dict[key] = value.strip('"')  # Убираем кавычки
            else:
                # Если значение отсутствует, создаем вложенный словарь
                stack.append(current_dict)
                current_dict[key] = {}
                current_key = key
        else:
            # Если это элемент списка
            if isinstance(current_dict[current_key], list):
                current_dict[current_key].append(stripped_line)
            else:
                current_dict[current_key] = [stripped_line]

        current_indent = indent

    return current_dict


def dict_to_xml(data, root_element='root'):
    """Преобразует словарь в XML строку."""
    xml_lines = [f"<{root_element}>"]

    def build_xml(d, indent_level):
        for key, value in d.items():
            indent = ' ' * (indent_level * 2)
            if isinstance(value, dict):
                xml_lines.append(f"{indent}<{key}>")
                build_xml(value, indent_level + 1)
                xml_lines.append(f"{indent}</{key}>")
            elif isinstance(value, list):
                for item in value:
                    xml_lines.append(f"{indent}<{key}>{item}</{key}>")
            else:
                xml_lines.append(f"{indent}<{key}>{value}</{key}>")

    build_xml(data, 1)
    xml_lines.append(f"</{root_element}>")

    return '\n'.join(xml_lines)


# Чтение YAML файла
with open('schedule.yml', 'r', encoding='utf-8') as yaml_file:
    yaml_content = yaml_file.readlines()

# Парсинг YAML
parsed_data = parse_yaml(yaml_content)

# Преобразование в XML
xml_output = dict_to_xml(parsed_data)

# Запись в XML файл
with open('schedule.xml', 'w', encoding='utf-8') as xml_file:
    xml_file.write(xml_output)
