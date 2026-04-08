import math
import os

import matplotlib.pyplot as plt
import numpy as np
from setuptools.command.rotate import rotate

# NEq
eps = 10 ** -2
funct1 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]
funct2 = [(1, 3), (-3.125, 2), (-3.5, 1), (2.458, 0)]
funct3 = [(4.45, 3), (7.81, 2), (-9.62, 1), (-8.17, 0)]
equationNum = "1"
inputFile = "in"
outputFile = "out"
inputDict = {}
inFromFile = False
outToFile = False
leftBound = None
rightBound = None
x_0 = None

# SNEq
u1 = lambda x, y: np.sin(x + y) - 1.5 * x + 0.1
u2 = lambda x, y: x ** 2 + 2 * y ** 2 - 1

v1 = lambda x, y: np.tan(x * y) - x ** 2
v2 = lambda x, y: 0.8 * x ** 2 + 2 * y ** 2 - 1

w1 = lambda x, y: np.tan(x * y + 0.2) - x ** 2
w2 = lambda x, y: x ** 2 + 2 * y ** 2 - 1

systemDict = {
    "1.1": u1,
    "1.2": u2,
    "2.1": v1,
    "2.2": v2,
    "3.1": w1,
    "3.2": w2,
}

systemNum = "1"


def getResNEq(x, n):
    s = "\n\n"
    s += "=" * 100 + "\n"
    s += "Результат работы программы\n"
    s += "=" * 100 + "\n"
    s += f"Корень уравнения x_0 = {x:.3f}\nf(x_0) = {f(x):.3f}\nКоличество итераций: {n}\n"
    s += "=" * 100 + "\n"
    s += "\n\n"
    return s


def getResSNEq(x, y, n, delta):
    s = "\n\n"
    s += "=" * 100 + "\n"
    s += "Результат работы программы\n"
    s += "=" * 100 + "\n"
    s += f"Вектор неизвестных (x_0, y_0) = ({x:.3f}, {y:.3f})\nКоличество итераций: {n}\n"
    s += f"Вектор погрешностей |x_{n}^({n}) - x_{n}^({n - 1})| = {delta:.4f}\n"
    s += "=" * 100 + "\n"
    s += "\n\n"
    return s


# l - коэффициент умножения
# add - слагаемое для прибавления к функции
def f(x, l=1, add=0):
    global equationNum
    res = 0
    # x = round(x, 2)
    if equationNum == "1":
        for tup in funct1:
            # print(f"===\nl={l}, c0={c[0]}, x={x}, c1={c[1]}\n")
            res += l * tup[0] * x ** tup[1]
            # print(f"r = {l * c[0] * x ** c[1]}\n===")
    elif equationNum == "2":
        for tup in funct2:
            res += l * tup[0] * x ** tup[1]
    elif equationNum == "3":
        for tup in funct3:
            res += l * tup[0] * x ** tup[1]
    return res + add


def calcDiff(func, x):
    return (func(x + eps) - func(x)) / eps


def fi(x, l):
    return f(x, l, x)


def simpleIteration(a, b):
    global x_0
    x_curr = a
    l = -1 / max(calcDiff(f, a), calcDiff(f, b))
    n = 0
    while True:
        n += 1
        x_next = fi(x_curr, l)
        if abs(x_next - x_curr) <= eps:
            break
        if (abs(x_next - x_curr)) > 10 or n > 20:
            print("Введите более точные границы, с текущими последовательность решений не сходится к истинному")
            break
        x_curr = x_next
    x_0 = x_curr
    s = getResNEq(x_curr, n)
    if outToFile:
        with open(outputFile, "w") as file:
            file.write(s)
            print("=" * 100)
            print(f"Решение было записано в файл '{outputFile}'")
            print("=" * 100)
            print("\n")
    else:
        print(s)


def newton(a, b):
    global x_0
    x_mid = (a + b) / 2
    x_prev = x_mid
    n = 0
    if calcDiff(f, x_mid) > 0:
        x_prev = a
    elif calcDiff(f, x_mid) < 0:
        x_prev = b
    while True:
        n += 1
        h_prev = f(x_prev) / calcDiff(f, x_prev)
        x_curr = x_prev - h_prev
        if abs(x_curr - x_prev) <= eps:
            break
        x_prev = x_curr
    x_0 = x_curr
    s = getResNEq(x_curr, n)
    if outToFile:
        with open(outputFile, "w") as file:
            file.write(s)
            print("=" * 100)
            print(f"Решение было записано в файл '{outputFile}'")
            print("=" * 100)
            print("\n")
    else:
        print(s)


def chords(a, b):
    global x_0
    n = 0
    while True:
        n += 1
        x_curr = a - f(a) * (a - b) / (f(a) - f(b))
        if abs(f(x_curr)) <= eps:
            break
        if f(a) * f(x_curr) < 0:
            b = x_curr
        else:
            a = x_curr
    x_0 = x_curr
    s = getResNEq(x_curr, n)
    if outToFile:
        # open(output_file, 'w').close()
        with open(outputFile, "w") as file:
            file.write(s)
            print("=" * 100)
            print(f"Решение было записано в файл '{outputFile}'")
            print("=" * 100)
            print("\n")
    else:
        print(s)


def solveEquation(methodNum):
    global eps, leftBound, rightBound
    if inFromFile:
        a = float(inputDict["a"])
        b = float(inputDict["b"])
        leftBound = a
        rightBound = b
        if f(a) * f(b) > 0:
            print("На заданном в файле интервале (a, b) нет решений")
            return
    else:
        while True:
            try:
                print("Введите начальное приближение - (a, b):")
                a = float(input("a = ").strip())
                b = float(input("b = ").strip())
                leftBound = a
                rightBound = b
                if f(a) * f(b) > 0:
                    print("На заданном в файле интервале (a, b) нет решений")
                    continue
                else:
                    break
            except ValueError:
                print("Введите корректное значение!")
        while True:
            try:
                print("Введите погрешность вычислений (epsilon):")
                eps = float(input("epsilon = ").strip())
                break
            except ValueError:
                print("Введите корректное значение!")
    match str(methodNum):
        case "1":
            chords(a, b)
        case "2":
            newton(a, b)
        case "3":
            x = (a + b) / 2
            l = -1 / max(calcDiff(f, a), calcDiff(f, b))
            q = abs(calcDiff(lambda x: fi(x, l), x))
            if not q < 1:
                print(
                    f"На выбранном интервале (a, b) = ({a}, {b}) последовательность решений уравнения не сходится к истинному;")
                while True:
                    try:
                        print("Введите начальное приближение - (a, b):")
                        a = float(input("a = ").strip())
                        b = float(input("b = ").strip())
                        leftBound = a
                        rightBound = b
                        if f(a) * f(b) > 0:
                            print("На заданном в файле интервале (a, b) нет решений")
                            continue
                        else:
                            simpleIteration(a, b)
                            break
                    except ValueError:
                        print("Введите корректное значение!")
            else:
                simpleIteration(a, b)


def calcDiffX(func, x, y):
    return (func(x + eps, y) - func(x, y)) / eps


def calcDiffY(func, x, y):
    return (func(x, y + eps) - func(x, y)) / eps


def calcDet(A):
    return A[0][0] * A[1][1] - A[1][0] * A[0][1]


def calcJacobian(func1, func2, x0, y0):
    j11 = calcDiffX(func1, x0, y0)
    j12 = calcDiffY(func1, x0, y0)
    j21 = calcDiffX(func2, x0, y0)
    j22 = calcDiffY(func2, x0, y0)
    return [[j11, j12], [j21, j22]]


def invertMatrix(M):
    c = 1 / calcDet(M)
    return [[c * M[1][1], -1 * c * M[0][1]], [-1 * c * M[1][0], c * M[0][0]]]


def checkSystemSolution(f1, f2, x, y):
    global eps
    return abs(f1(x, y)) <= eps and abs(f2(x, y)) <= eps


def solveSystemOfNonLinearEquationNewton(xVect, yVect):
    x1, x2 = xVect
    y1, y2 = yVect
    x0, y0 = 0, 0
    n = 0
    while True:
        n += 1
        f1 = systemDict[systemNum + ".1"]
        f2 = systemDict[systemNum + ".2"]
        if abs(x2 - x0) <= eps and abs(y2 - y0) <= eps:
            res = getResSNEq(x2, y2, n, abs(x2 - x0))
            if not outToFile:
                print(res)
                if not checkSystemSolution(f1, f2, x2, y2):
                    print("Решение неверное!")
                    print("=" * 100)
            else:
                with open(outputFile, "w") as file:
                    file.write(res)
                    print("=" * 100)
                    print(f"Решение было записано в файл '{outputFile}'")
                    print("=" * 100)
                    print("\n")
            break
        j = calcJacobian(f1, f2, x2, y2)
        j_inv = invertMatrix(j)
        dx = -1 * (f1(x2, y2) * j_inv[0][0] + f2(x2, y2) * j_inv[0][1])
        dy = -1 * (f1(x2, y2) * j_inv[1][0] + f2(x2, y2) * j_inv[1][1])
        x0 = x2
        y0 = y2
        x2 = x2 + dx
        y2 = y2 + dy


def drawGraphSNEq(systemNumber):
    xValues = np.linspace(-1.5, 1.5, 1000)
    yValues = np.linspace(-1.5, 1.5, 1000)
    func1 = systemDict[systemNumber + ".1"]
    func2 = systemDict[systemNumber + ".2"]
    x, y = np.meshgrid(xValues, yValues)
    axes = plt.gca()
    axes.set_xticks(np.arange(-1.5, 1.5, 0.1), minor=True)
    axes.set_yticks(np.arange(-1.5, 1.5, 0.1), minor=True)
    axes.set_xticks(np.arange(-1.5, 1.5, 0.5))
    axes.set_yticks(np.arange(-1.5, 1.5, 0.5))
    axes.spines['bottom'].set_position('zero')
    axes.spines['left'].set_position('zero')
    axes.text(1.6, 0, 'x')
    axes.text(0, 1.6, 'y')
    zValues1 = func1(x, y)
    zValues2 = func2(x, y)

    plt.contour(x, y, zValues1, levels=[0], colors="red")
    plt.contour(x, y, zValues2, levels=[0], colors="blue")

    plt.axvline(x=0, color='black', linewidth=1)
    plt.axhline(y=0, color='black', linewidth=1)
    plt.grid(True, which="both")
    plt.tight_layout()
    plt.show()


def inputSNEq():
    global systemNum
    if not inFromFile:
        while True:
            print("Выберите систему (1/2/3):")

            print("1: / sin(x + y) = 1,5x - 0,1")
            print("  <")
            print("   \\ x^2 + 2y^2 = 1")
            print("-" * 100)
            print("2: / tg(xy) = x^2")
            print("  <")
            print("   \\ 0,8x^2 + 2y^2 = 1")
            print("-" * 100)
            print("3: / tg(xy + 0,2) = x^2")
            print("  <")
            print("   \\ x^2 + 2y^2 = 1")
            n = input(">>> ").strip()
            if n not in ["1", "2", "3"]:
                print("Введите корректное значение!\n\n")
                continue
            else:
                systemNum = n
                break
        drawGraphSNEq(systemNum)
        x1, x2 = 0, 0
        y1, y2 = 0, 0
        while True:
            try:
                print("Введите начальное приближение по оси x \nx1 <= x0 <= x2")
                x1 = float(input("x1 = ").strip())
                x2 = float(input("x2 = ").strip())
                print("Введите начальное приближение по оси y \ny1 <= y0 <= y2")
                y1 = float(input("y1 = ").strip())
                y2 = float(input("y2 = ").strip())
                break
            except ValueError:
                print("Введите корректное значение!\n\n")
                continue
    else:
        x1, x2 = int(inputDict["x1"]), int(inputDict["x2"])
        y1, y2 = int(inputDict["y1"]), int(inputDict["y2"])
    x = (x1, x2)
    y = (y1, y2)
    solveSystemOfNonLinearEquationNewton(x, y)


def inputNEq():
    global equationNum, x_0
    if not inFromFile:
        while True:
            print(
                "Выберите уравнение (1/2/3):\n1: -1,8x^3 - 2,94x^2 + 10,37x + 5,38\n2: x^3 - 3,125x^2 - 3,5x + 2,458\n3: 4,45x^3 + 7,81x^2 - 9,62x - 8,17")
            n = input(">>> ").strip()
            if n not in ["1", "2", "3"]:
                print("Введите корректное значение!\n\n")
                continue
            else:
                equationNum = n
                break

        while True:
            print("Каким методом вы хотите решить:\n1 – Метод хорд\n2 – Метод Ньютона\n3 – Метод простой итерации")
            n = input(">>> ").strip()
            if n not in ["1", "2", "3"]:
                print("Введите корректное значение!\n\n")
                continue
            solveEquation(n)
            break

    else:
        m = inputDict["method"]
        if m not in ["1", "2", "3"]:
            print("Введите корректное значение method в файле!\n\n")
        else:
            solveEquation(m)
    drawGraphNEq()


def userInput():
    global inputFile, outputFile, equationNum, inputDict, inFromFile, outToFile, systemNum
    while True:
        while True:
            print(
                "Выберите режим работы (1/2/3/4):\n1 - ввод и вывод в консоли\n2 - ввод из файла, вывод в файл\n3 - ввод в консоли, вывод в файл\n4 - ввод из файла, вывод в консоль")
            n = input(">>> ").strip()
            match n:
                case "1":
                    inFromFile = False
                    outToFile = False
                case "2":
                    inFromFile = True
                    outToFile = True
                case "3":
                    inFromFile = False
                    outToFile = True
                case "4":
                    inFromFile = True
                    outToFile = False
                case _:
                    print("Введите корректное значение!\n\n")
                    continue
            break
        if inFromFile and outToFile:
            while True:
                inputFile = input("Введите имя файла с входными данными: ").strip()
                outputFile = input("Введите имя файла для записи результата работы программы: ").strip()
                if not os.path.exists(inputFile):
                    print(f"Не существует такого файла: {inputFile}")
                else:
                    break
        elif inFromFile and not outToFile:
            while True:
                inputFile = input("Введите имя файла с входными данными: ").strip()
                if not os.path.exists(inputFile):
                    print(f"Не существует такого файла: {inputFile}")
                else:
                    break
        while True:
            if not inFromFile:
                while True:
                    print(
                        "Выберите что вы хотите решить (1/2):\n1 - нелинейное уравнение\n2 - систему нелинейных уравнений")
                    typeOfProgram = input(">>> ").strip()
                    match typeOfProgram:
                        case "1":
                            inputNEq()
                        case "2":
                            inputSNEq()
                        case _:
                            print("Введите корректное значение!\n\n")
            else:
                with open(inputFile) as file:
                    for line in file:
                        key, val = line.split(" = ")[0].strip(), line.split(" = ")[1].strip()
                        inputDict[key] = val
                typeOfProgram = inputDict["typeOfProgram"]
                match typeOfProgram:
                    case "1":
                        equationNum = inputDict["equationNum"]
                        inputNEq()
                        break
                    case "2":
                        systemNum = inputDict["systemNum"]
                        inputSNEq()
                        break
                    case _:
                        print("Введите корректное значение!\n\n")


def drawGraphNEq():
    xValues = np.linspace(leftBound, rightBound, 1000)
    yValues = []
    func = funct1 if equationNum == "1" else (funct2 if equationNum == "2" else funct3)
    for x in xValues:
        res = 0
        for tup in func:
            res += tup[0] * x ** tup[1]
        yValues.append(res)
    plt.plot(xValues, yValues, color="red", linewidth=1)
    plt.plot(x_0, 0, markersize=4, color="black", marker='o')
    plt.axvline(x=0, color='black', linewidth=1)
    plt.axhline(y=0, color='black', linewidth=1)
    plt.grid(True)
    plt.tight_layout()
    plt.show()


if __name__ == "__main__":
    userInput()