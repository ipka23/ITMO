import re
text = ("""Кафедра ! ВТ --- ВТ & ВТ ? АЛО ИТМО
       ВТ - кафедра итмо не ИТМО """)
match = re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b)', text)
for m in match:
    print(m, " | ", m.group(1), m.group(2), m.group(3), m.group(4), m.group(5))