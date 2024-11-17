def parse_yaml(yaml_str):
    result = {}
    lines = yaml_str.strip().splitlines()

    current_key = None
    for line in lines:
        line = line.strip()

        if line.startswith('- '):  # Обработка списков
            item = line[2:]  # Убираем '- '
            if current_key not in result:
                result[current_key] = []  # Инициализация списка
            result[current_key].append(item)  # Добавляем элемент в список
        elif ':' in line:  # Обработка ключ-значение
            key, value = line.split(':', 1)
            current_key = key.strip()
            result[current_key] = value.strip() if value.strip() else None  # Устанавливаем значение

    return result


# Пример использования
yaml_data = """
UserName: Alicia
Password: pinga123*
phone: (495) 555-32-56
room: 10
TablesList:
 - EmployeeTable
 - SoftwaresList
 - HardwareList
"""

parsed_data = parse_yaml(yaml_data)
print(parsed_data)