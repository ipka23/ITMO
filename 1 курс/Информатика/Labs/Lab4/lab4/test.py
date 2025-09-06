d = {
    "indent": 'вложеность',
    "key": 'ключ',
    "value": 'значение',
    "children": [
        {
            "indent": 'вложеность',
            "key": 'ключ',
            "value": 'значение',
            "children": ['1', '2']
        }
    ]
}

# Итерируем по словарю d
for key, value in d.items():
    if isinstance(value, dict):  # Если значение - это вложенный словарь
        print(value["indent"])
    elif isinstance(value, list):  # Если значение - это список
        for item in value:
            if isinstance(item, dict):  # Если элемент списка - это словарь
                print(item["indent"])
