import math

eps = 10 ** -2
funct1 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]
funct2 = [(1, 3), (-3.125, 2), (-3.5, 1), (2.458, 0)]
funct3 = [(4.45, 3), (7.81, 2), (-9.62, 1), (-8.17, 0)]
equationNum = -1
mode = 1
input_file = "input"
output_file = "output"
input_dict = {}
inFromFile = False
outToFile = False


def getRes(x, n):
    s = "\n\n"
    s += "=" * 100 + "\n"
    s += "Результат работы программы\n"
    s += "=" * 100 + "\n"
    s += f"x_0 = {x:.3f}\nf(x_0) = {f(x):.3f}\nКоличество итераций: {n}\n"
    s += "=" * 100 + "\n"
    s += "\n\n"
    return s


# l - коэффициент умножения
# add - слагаемое для прибавления к функции
def f(x, l=1, add=0):
    res = 0
    # x = round(x, 2)
    if equationNum == 1:
        for tup in funct1:
            # print(f"===\nl={l}, c0={c[0]}, x={x}, c1={c[1]}\n")
            res += l * tup[0] * x ** tup[1]
            # print(f"r = {l * c[0] * x ** c[1]}\n===")
    elif equationNum == 2:
        for tup in funct2:
            res += l * tup[0] * x ** tup[1]
    elif equationNum == 3:
        for tup in funct3:
            res += l * tup[0] * x ** tup[1]
    return res + add


def calcDiff(func, x):
    print("=====calcDiff======")
    print(func(x + eps))
    print( - func(x))
    print("=====calcDiff======")

    return (func(x + eps) - func(x)) / eps


def fi(x, l):
    return f(x, l, x)


def simpleIteration(a, b):
    print(a)
    print(b)
    print(calcDiff(f, a))
    print(calcDiff(f, b))
    # print(max(calcDiff(f, a), calcDiff(f, b)))
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
    s = getRes(x_curr, n)
    if outToFile:
        with open(outToFile) as file:
            file.write(s)
    else:
        print(s)


def newton(a, b):
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
    s = getRes(x_curr, n)
    if outToFile:
        with open(outToFile) as file:
            file.write(s)
    else:
        print(s)


def chords(a, b):
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
    s = getRes(x_curr, n)
    if outToFile:
        with open(outToFile, 'w') as file:
            file.write(s)
    else:
        print(s)


def solveEquation(methodNum):
    if inFromFile:
        a = float(input_dict["a"])
        b = float(input_dict["b"])
        print(calcDiff(f, a))
        print(calcDiff(f, b))
        print("+++")
    else:
        while True:
            try:
                print("Введите начальное приближение - (a, b):")
                a = float(input("a = ").strip())
                b = float(input("b = ").strip())
                break
            except ValueError:
                print("Введите корректное значение!")
    match str(methodNum):
        case "1":
            chords(a, b)
        case "2":
            newton(a, b)
        case "3":
            simpleIteration(a, b)


def calcDiffX(func, x, y):
    return (func(x + eps, y) - func(x, y)) / eps


def calcDiffY(func, x, y):
    return (func(x, y + eps) - func(x, y)) / eps


def f1(x, y):
    return math.sin(x + y) - 1.5 * x + 0.1


def f2(x, y):
    return x ** 2 + 2 * y ** 2 - 1


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


def solveSystemOfNonLinearEquation(x, y):
    x0, y0 = 0, 0
    while True:
        if abs(x - x0) <= eps and abs(y - y0) <= eps:
            return x, y
        j = calcJacobian(f1, f2, x, y)
        j_inv = invertMatrix(j)
        dx = -1 * (f1(x, y) * j_inv[0][0] + f2(x, y) * j_inv[0][1])
        dy = -1 * (f1(x, y) * j_inv[1][0] + f2(x, y) * j_inv[1][1])
        x0 = x
        y0 = y
        x = x + dx
        y = y + dy


def inputSNEq():
    pass


def inputNEq():
    global equationNum
    if not inFromFile:
        while True:
            print(
                "Выберите уравнение (1/2/3):\n1: -1,8x^3 - 2,94x^2 + 10,37x + 5,38\n2: x^3 - 3,125x^2 - 3,5x + 2,458\n3: 4,45x^3 + 7,81x^2 - 9,62x - 8,17")
            n = input(">>> ").strip()

            match n:
                case "1":
                    equationNum = 1
                    break
                case "2":
                    equationNum = 2
                    break
                case "3":
                    equationNum = 3
                    break
                case _:
                    print("Введите корректное значение!\n\n")
                    continue
        while True:
            print("Каким методом вы хотите решить:\n1 – Метод хорд\n2 – Метод Ньютона\n3 – Метод простой итерации")
            n = input(">>> ").strip()
            if n not in ["1", "2", "3"]:
                print("Введите корректное значение!\n\n")
                continue
            solveEquation(n)
            break

    else:
        m = input_dict["method"]
        if m not in ["1", "2", "3"]:
            print("Введите корректное значение method в файле!\n\n")
        else:
            solveEquation(m)


def userInput():
    global mode, input_file, output_file, equationNum, input_dict, inFromFile, outToFile
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
    # if inFromFile and outToFile:
    #     while True:
    #         try:
    #             input_file = input("Введите имя файла с входными данными: ").strip()
    #             output_file = input("Введите имя файла для записи результата работы программы: ").strip()
    #             break
    #         except FileNotFoundError as e:
    #             print(f"Не существует такого файла: {e.filename}")
    # elif inFromFile and not outToFile:
    #     while True:
    #         try:
    #             input_file = input("Введите имя файла с входными данными: ").strip()
    #             break
    #         except FileNotFoundError as e:
    #             print(f"Не существует такого файла: {e.filename}")
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
            with open(input_file) as file:
                for line in file:
                    key, val = line.split(" = ")[0].strip(), line.split(" = ")[1].strip()
                    input_dict[key] = val
                print(input_dict)
            typeOfProgram = input_dict["typeOfProgram"]
            equationNum = input_dict["equationNum"]
            match typeOfProgram:
                case "1":
                    inputNEq()
                case "2":
                    inputSNEq()
                case _:
                    print("Введите корректное значение!\n\n")


if __name__ == "__main__":
    userInput()
    # x1 = 0.7
    # y1 = 0.5
    # x, y = solveSystemOfNonLinearEquation(x1, y1)
    # print(f"x = {x} | y = {y}")
