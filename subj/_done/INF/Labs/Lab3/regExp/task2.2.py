
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
test5 = "ВТ - ?    ИТМО ВТ рав аовов ИТМО"


def vt_best(text):
    if re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', text):
        match = re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', text)
        for m in match:
            regexp = re.sub(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', r'\2 \3 \4 \5 \6 \7',m.group(0))
            print(regexp)

    if re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', text):
        match = re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', text)
        for m in match:
            regexp = re.sub(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', r'\2 \3 \4 \5 \6',m.group(0))
            print(regexp)

    if re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', text):
        match = re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', text)
        for m in match:
            regexp = re.sub(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)\W+(\bИТМО\b(?!ИТМО))', r'\2 \3 \4 \5',m.group(0))
            print(regexp)

    if re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)(\bИТМО\b(?!ИТМО))', text):
        match = re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)(\bИТМО\b(?!ИТМО))', text)
        for m in match:
            regexp = re.sub(r'((?<!ВТ)(\bВТ\b))\W+(\w+)\W+(\w+)(\bИТМО\b(?!ИТМО))', r'\2 \3 \4',m.group(0))
            print(regexp)
    if re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\bИТМО\b(?!ИТМО))', text):
        match = re.finditer(r'((?<!ВТ)(\bВТ\b))\W+(\bИТМО\b(?!ИТМО))', text)
        for m in match:
            regexp = re.sub(r'((?<!ВТ)(\bВТ\b))\W+(\bИТМО\b(?!ИТМО))', r'\2 \3',m.group(0))
            print(regexp)
    else:
        print("The text does not match!")


def test():
    vt_best(test1)
    vt_best(test2)
    vt_best(test3)
    vt_best(test4)
    vt_best(test5)


test()