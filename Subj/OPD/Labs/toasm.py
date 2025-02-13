
hex_data = """
3F8      040E
3F9      A000
3FA      4000
3FB      E000
3FC      AF80
3FD      0740
3FE      0680
3FF      EEFB
400      AF03
401      EEF8
402      4EF5
403      EEF5
404      ABF4
405      0480
406      F404
407      0400
408      7EF2
409      F901
40A      EEF0
40B      83FA
40C      CEF7
40D      0100
40E      8409
40F      0000
410      53FB
"""

# Разбираем данные в словарь
lines = hex_data.strip().split("\n")
data_dict = {int(line.split()[0], 16): line.split()[1] for line in lines}

# Определяем минимальный и максимальный адрес
min_addr = min(data_dict.keys())
max_addr = max(data_dict.keys())

# Генерируем ассемблерный код с нулями
asm_code = ""
for addr in range(min_addr, max_addr + 1):
    value = data_dict.get(addr, "0000")  # Заполняем нулями отсутствующие адреса
    asm_code += f"ORG 0x{addr:X} \nWORD 0x{value}\n"
data_dict = {line.split()[0]: line.split()[1] for line in lines}

# Заполняем нулями отсутствующие адреса
for addr in range(0x000, 0x800):
    addr_hex = f"{addr:03X}"  # Преобразуем в 3-значный HEX
    if addr_hex not in data_dict:
        data_dict[addr_hex] = "0000"

# Генерируем ассемблерный код
asm_code = "\n".join([f"ORG 0x{addr} \nWORD 0x{value}" for addr, value in sorted(data_dict.items())])

# Сохраняем результат в файл
with open("data.txt", "w") as file:
    file.write(asm_code)
with open("data.txt", "w") as f:
    f.write(asm_code)

# Выводим часть результата для проверки
print("Generated assembly saved to data.txt")