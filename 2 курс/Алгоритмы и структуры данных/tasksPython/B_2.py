def countLettersFrequency(zooList):
    lettersFrequency = [0] * 26
    for i in range(len(zooList)):
        ind = ord(zooList[i].lower()) - ord("a")
        lettersFrequency[ind] += 1
    return lettersFrequency


def zooIsValid(lettersInput, lettersList):
    if len(lettersInput) % 2 != 0:
        return False

    lettersFrequency = countLettersFrequency(lettersList)
    for i in range(len(lettersFrequency)):
        if lettersFrequency[i] != 2 and lettersFrequency[i] != 0:
            return False
    return True


def sortZoo(lettersInput):
    lettersList = []
    for letter in lettersInput:
        lettersList.append(letter)
    if (lettersInput == "" or not zooIsValid(lettersInput, lettersList)):
        print("Impossible")
        return
    else:
        i = 0
        answerStr = ""
        while lettersList:
            i += 1
            mid = len(lettersList) // 2 - 1
            cage = lettersList[mid]
            animal = lettersList[mid + 1]
            for _ in range(len(lettersList)):
                if (cage != animal and cage.lower() != animal.lower()):
                    lettersList = lettersList[1:] + lettersList[:1]
                    cage = lettersList[mid]
                    animal = lettersList[mid + 1]
                else:
                    break

            if (cage != animal and cage.lower() == animal.lower()):
                answerStr += str(ord(lettersList[mid].lower()) - ord('a') + 1) + " "
                lettersList.remove(cage)
                lettersList.remove(animal)
            else:
                print("Impossible")
                return
        print("Possible\n" + answerStr)


if __name__ == "__main__":
    lettersInput = input()
    sortZoo(lettersInput)
