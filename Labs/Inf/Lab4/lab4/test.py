s = 'lessons:'
key_and_value = s.split(': ')
key = key_and_value[0]
value = key_and_value[1] if len(key_and_value) > 1 else ""  # Проверяем длину списка

print(key, value, len(s.split(': ')))
