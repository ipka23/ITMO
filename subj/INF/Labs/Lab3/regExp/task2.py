r'''
Задание:
Дан текст. Необходимо найти в нём каждый фрагмент, где сначала идёт слово «ВТ»,
затем не более 4 слов, и после этого идёт слово «ИТМО».
Для простоты будем считать словом любую последовательность букв, цифр и знаков
«_» (то есть символов \w).
Пример:
Ввод:
А ты знал, что ВТ – лучшая кафедра в
ИТМО?
Вывод:
ВТ лучшая кафедра в ИТМО
'''
import re
test1 = "А ты знал, что ВТ - лучшая кафедра в ИТМО?"
test2 = "Кафедра ! ВТ --- ВТ & ВТ ? АЛО ИТМО"
test3 = "ИВТ ВТ кафедра - НЕ ИТМОС      A      ИТМО"
test4 = "ВТ раз два три четыре пять шесть семь ИТМО"
test5 = "ВТ - - - - ИТМО"


def vt_best(text):
    if re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b)', text):
        match = re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b)', text)
        for m in match:
            print(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6))

    if re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b)', text):
        match = re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b)', text)
        for m in match:
            print(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5))

    if re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)\W+(\bИТМО\b)', text):
        match = re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)\W+(\bИТМО\b)', text)
        for m in match:
            print(m.group(1), m.group(2), m.group(3), m.group(4))

    if re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)\W+(\bИТМО\b)', text):
        match = re.finditer(r'(\bВТ\b)\W+(\w+)\W+(\w+)(\bИТМО\b)', text)
        for m in match:
            print(m.group(1), m.group(2), m.group(3))
    if re.finditer(r'(\bВТ\b)\W+(\bИТМО\b)', text):
        match = re.finditer(r'(\bВТ\b)\W+(\bИТМО\b)', text)
        for m in match:
            print(m.group(1), m.group(2))
    else:
        print("The text does not match!")


def test():
    # vt_best(test1)
    # vt_best(test2)
    # vt_best(test3)
    # vt_best(test4)
    vt_best(test5)


test()