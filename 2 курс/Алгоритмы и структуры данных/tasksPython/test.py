def all_latin(s: str) -> bool:
    return all(('A' <= c <= 'Z') or ('a' <= c <= 'z') for c in s)

def zooIsValid(s: str) -> bool:
    if len(s) % 2 != 0:
        return False
    lower = [0] * 26
    upper = [0] * 26
    for c in s:
        if 'a' <= c <= 'z':
            lower[ord(c) - 97] += 1
        else:
            upper[ord(c) - 65] += 1
    return lower == upper

def sortZoo(lettersInput):
    if lettersInput == "" or not all_latin(lettersInput) or not zooIsValid(lettersInput):
        print("Impossible")
        return

    n = len(lettersInput)

    # номер животного (порядок среди строчных)
    animal_id = {}
    cnt = 0
    for i, c in enumerate(lettersInput):
        if 'a' <= c <= 'z':
            cnt += 1
            animal_id[i] = cnt

    # стек для каждой буквы
    stacks = [[] for _ in range(26)]

    # результат: для позиции ловушки -> animal_id
    match = {}

    for i, c in enumerate(lettersInput):
        idx = ord(c.lower()) - 97
        if not stacks[idx]:
            stacks[idx].append(i)
        else:
            j = stacks[idx].pop()
            # проверка регистров
            if lettersInput[i].isupper() == lettersInput[j].isupper():
                print("Impossible")
                return
            # определяем, где ловушка
            if lettersInput[i].isupper():
                match[i] = animal_id[j]
            else:
                match[j] = animal_id[i]

    # если остались незакрытые
    for st in stacks:
        if st:
            print("Impossible")
            return

    # вывод в порядке обхода ловушек
    res = []
    for i, c in enumerate(lettersInput):
        if 'A' <= c <= 'Z':
            res.append(str(match[i]))

    print("Possible")
    print(" ".join(res))


if __name__ == "__main__":
    sortZoo(input().strip())