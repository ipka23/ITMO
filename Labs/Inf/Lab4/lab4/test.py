# def parse_yaml(yaml_str):
#     result = {}
#     lines = yaml_str.strip().splitlines()
#
#     current_key = None
#     for line in lines:
#         line = line.strip()
#
#         if line.startswith('- '):  # Обработка списков
#             item = line[2:]  # Убираем '- '
#             if current_key not in result:
#                 result[current_key] = []  # Инициализация списка
#             result[current_key].append(item)  # Добавляем элемент в список
#         elif ':' in line:  # Обработка ключ-значение
#             key, value = line.split(':', 1)
#             current_key = key.strip()
#             result[current_key] = value.strip() if value.strip() else None  # Устанавливаем значение
#
#     return result
#
#
# # Пример использования
# yaml_data = """
# UserName: Alicia
# Password: pinga123*
# phone: (495) 555-32-56
# room: 10
# TablesList:
#  - EmployeeTable
#  - SoftwaresList
#  - HardwareList
# """
#
# parsed_data = parse_yaml(yaml_data)
# print(parsed_data)


# l = []
# s = "word exel libre writer"
# l.append(s.split())
# print(l)

# s = "- lesson: Лекция "
# for i in range(len(s)):
#     while s[i] != " ":
#         print(s[i])
#         i += 1












# yml = '''
#                 - lesson: Лекция
#                   time: "11:4013:10"
#                 - lesson: Практика
#                   time: "13:3015:00"
# '''
# yaml_len = len(yml)
# i = 0
# def yaml_list():
#     global i
#     r = []
#
#     while i < yaml_len:
#         # Пропускаем пробелы
#         while i < yaml_len and yml[i] == ' ':
#             i += 1
#
#         # Проверяем на наличие символа '-'
#         if i < yaml_len and yml[i] == '-':
#             i += 1  # Пропускаем '-'
#
#             # Пропускаем пробелы после '-'
#             while i < yaml_len and yml[i] == ' ':
#                 i += 1
#
#             s = ""
#             # Считываем до конца строки или следующего '-'
#             while i < yaml_len and yml[i] != '\n':
#                 s += yml[i]
#                 i += 1
#             key, val = s.split(': ')
#             r.append({key: val})  # Добавляем строку без лишних пробелов
#
#         # Переходим к следующей строке
#         i += 1
#
#     return r
#
#
# # Вызов функции и вывод результата
# result = yaml_list()
# print(result)


yml = '''
      days:
        -
          day: Понедельник
          disciplines:
            -
              discipline: Основы профессиональной деятельности
              teacher: Остапенко Ольга Денисовна
              building: Кронверкский пр., д.49, лит.А
              classroom: "2308"
              lessons:
                -
                  lesson: Лабораторная
                  time: "8:20-9:50"
                -
                  lesson: Лабораторная
                  time: "9:50-11:20"
            -
              discipline: Дискретная математика
              teacher: Поляков Владимир Иванович
              building: Кронверкский пр., д.49, лит.А
              classroom: "2403"
              lessons:
                -
                  lesson: Лекция
                  time: "11:40-13:10"
                -
                  lesson: Практика
                  time: "13:30-15:00"
        -
          day: Четверг
'''
yaml_len = len(yml)
i = 0


def yaml_list():
    global i
    r = {}

    # Пропускаем пробелы перед основным ключом
    while i < yaml_len and yml[i].isspace():
        i += 1

    # Считываем основной ключ (lessons)
    key = ""
    while i < yaml_len and yml[i] != '\n':
        key += yml[i]
        i += 1
    key = key.strip().rstrip(':')  # Убираем пробелы и двоеточие

    r[key] = []  # Инициализируем список для значений

    # Переходим к следующей строке
    i += 1

    while i < yaml_len:
        # Пропускаем пробелы
        while i < yaml_len and yml[i].isspace():
            i += 1

        # Проверяем на наличие символа '-'
        if i < yaml_len and yml[i] == '-':
            i += 1  # Пропускаем '-'

            # Пропускаем пробелы после '-'
            while i < yaml_len and yml[i].isspace():
                i += 1

            current_dict = {}

            # Считываем ключ-значение пар до следующего '-'
            while i < yaml_len and yml[i] != '-':
                # Пропускаем пробелы перед ключом
                while i < yaml_len and yml[i].isspace():
                    i += 1

                s = ""
                while i < yaml_len and yml[i] != '\n':
                    s += yml[i]
                    i += 1

                # Пропускаем пустые строки
                if not s.strip():
                    i += 1
                    continue

                if ':' in s:
                    k, v = s.split(':', 1)  # Разделяем по первому вхождению ':'
                    current_dict[k.strip()] = v.strip().strip('"')  # Убираем лишние пробелы и кавычки

                # Переходим на следующую строку
                i += 1

            r[key].append(current_dict)

        else:
            i += 1  # Переходим к следующей строке

    return r


# Вызов функции и вывод результата
result = yaml_list()
print(result)







