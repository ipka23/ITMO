import math

epsilon = 0.01


def f1(x):
    return 2 * x ** 3 - 3 * x ** 2 + 5 * x - 9


def f2(x):
    return x ** 3 - 3 * x ** 2 + 7 * x - 10


def f3(x):
    return 2 * x ** 3 - 9 * x ** 2 - 7 * x + 11


def f4(x):
    return 1 / math.sqrt(x)


def f5(x):
    return 1 / math.sqrt(-1 * x)


def f6(x):
    return 1 / (x - 1)


def selectFunction():
    print("Выберите функцию:")
    print("1: 2x^3 - 3x^2 + 5x - 9")
    print("2: x^3 - 3x^2 + 7x - 10")
    print("3: 2x^3 - 9x^2 - 7x + 11")
    choice = input("Введите номер функции (1-3): ")
    while choice not in ["1", "2", "3"]:
        choice = input("Неверный ввод. Введите 1-3: ")
    return [f1, f2, f3][int(choice) - 1]


def selectImproperFunction():
    print("Выберите функцию для несобственного интеграла:")
    print("1: 1/√x")
    print("2: 1/√-x")
    print("3: 1/(x-1)")
    choice = input("Введите номер функции (1-3): ")
    while choice not in ["1", "2", "3"]:
        choice = input("Неверный ввод. Введите 1-3: ")
    return [f4, f5, f6][int(choice) - 1]


def getBreakpoints(f, a, b, n=4):
    breakpoints = []
    h = (b - a) / n
    c = (a + b) / 2
    val = None
    try:
        val = a
        f(a)
        val = b
        f(b)
        val = c
        f(c)
        for i in range(1, n + 1):
            c = a + i * h
            val = c
            f(c)
    except ZeroDivisionError:
        breakpoints.append(val)
    return breakpoints


def leftRectangles(f, a, b, n):
    h = (b - a) / n
    result = 0
    for i in range(n):
        result += f(a + i * h)
    return result * h


def rightRectangles(f, a, b, n):
    h = (b - a) / n
    result = 0
    for i in range(1, n + 1):
        result += f(a + i * h)
    return result * h


def middleRectangles(f, a, b, n):
    h = (b - a) / n
    result = 0
    for i in range(n):
        result += f(a + (i + 0.5) * h)
    return result * h


def trapezoid(f, a, b, n):
    h = (b - a) / n
    result = (f(a) + f(b)) / 2
    for i in range(1, n):
        result += f(a + i * h)
    return result * h


def simpson(f, a, b, n):
    if n % 2 != 0:
        n += 1
    h = (b - a) / n
    result = f(a) + f(b)
    for i in range(1, n):
        if i % 2 == 0:
            result += 2 * f(a + i * h)
        else:
            result += 4 * f(a + i * h)
    return result * h / 3


def solveRunge(f, a, b, method, k, eps, alpha=0.0, delta=0.0):
    n = 4
    while True:
        I_h = method(f, a + alpha, b - delta, n)
        I_h2 = method(f, a + alpha / 2, b - delta / 2, 2 * n)
        error = abs(I_h2 - I_h) / (2 ** k - 1)
        if error < eps:
            return I_h2, 2 * n
        n *= 2
        alpha /= 2
        delta /= 2
        if n > 1000000:
            return None, None


methods = {
    "1": ("левых прямоугольников", leftRectangles, 1),
    "2": ("правых прямоугольников", rightRectangles, 1),
    "3": ("средних прямоугольников", middleRectangles, 2),
    "4": ("трапеций", trapezoid, 2),
    "5": ("Симпсона", simpson, 4)
}


def finalPrompt(result, n, eps):
    if result is not None:
        print("\n" + "=" * 60)
        print("РЕЗУЛЬТАТ:")
        print(f"  Интеграл ≈ {result:.3f}")
        print(f"  Число разбиений n = {n}")
        print(f"  Требуемая точность {eps} достигнута")
        print("=" * 60)
    else:
        print("Не удалось достичь требуемой точности")

    print("\n\n")


def firstTask():
    print("=" * 60)
    print("ЧИСЛЕННОЕ ИНТЕГРИРОВАНИЕ")
    print("=" * 60)

    f = selectFunction()

    a, b, eps = input_A_B_EPS()

    methodChoice = getMethod()

    methodName, methodFunc, k = methods[methodChoice]
    result, nFinal = solveRunge(f, a, b, methodFunc, k, eps)

    finalPrompt(result, nFinal, eps)


def isNumber(s):
    try:
        float(s)
        return True
    except ValueError:
        return False


def input_A_B_EPS():
    a = input("Введите нижний предел a: ")
    while not isNumber(a):
        a = input("Введите нижний предел a: ")
    b = input("Введите верхний предел b: ")
    while not isNumber(b):
        b = input("Введите нижний предел b: ")
    a, b = float(a), float(b)
    while a >= b:
        print("Ошибка: a должно быть меньше b")
        a = float(input("Введите a: "))
        b = float(input("Введите b: "))
    eps = float(input("Введите точность вычислений: "))
    return a, b, eps


def getMethod():
    print("\nДоступные методы:")
    print("1. Левые прямоугольники")
    print("2. Правые прямоугольники")
    print("3. Средние прямоугольники")
    print("4. Трапеции")
    print("5. Симпсона")
    methodChoice = input("Выберите метод (1-5): ")
    while methodChoice not in ["1", "2", "3", "4", "5"]:
        methodChoice = input("Неверный ввод. Выберите 1-5: ")
    return methodChoice


def secondTask():
    print("=" * 60)
    print("НЕСОБСТВЕННЫЕ ИНТЕГРАЛЫ 2 РОДА")
    print("=" * 60)
    breakpoints = []
    f_imp = selectImproperFunction()

    while True:
        a, b, eps = input_A_B_EPS()

        try:
            breakpoints = getBreakpoints(f_imp, a, b)
            break
        except ValueError:
            print("=" * 60)
            print(
                "Область допустимых значений функции √x = [0, +inf)\nОбласть допустимых значений функции √-x = (-inf, 0]\nВведите корректные значения пределов интегрирования!")
            continue

    breakType = None
    c = None
    if breakpoints:
        print(f"Обнаружены точки разрыва: {breakpoints[0]}")
        for i in range(len(breakpoints)):
            if a == breakpoints[i]:
                breakType = "1"
                print(f"Разрыв в точке a = {a}")
            elif b == breakpoints[i]:
                breakType = "2"
                print(f"Разрыв в точке b = {b}")
            else:
                breakType = "3"
                c = breakpoints[i]
                print(f"Разрыв внутри отрезка [a, b] в точке {c}")
    methodChoice = getMethod()
    methodName, methodFunc, k = methods[methodChoice]

    if breakType == "1":
        result, nFinal = solveRunge(f_imp, a, b, methodFunc, k, eps, alpha=eps)
    elif breakType == "2":
        result, nFinal = solveRunge(f_imp, a, b, methodFunc, k, eps, delta=eps)
    else:
        I_2n_left, n_left = solveRunge(f_imp, a, c, methodFunc, k, eps, delta=eps)
        I_2n_right, n_right = solveRunge(f_imp, c, b, methodFunc, k, eps, alpha=eps)
        if n_left is None or n_right is None:
            print("=" * 60)
            print("Интеграл не существует")
            print("=" * 60)
            print("\n\n")
            return
        result, nFinal = I_2n_left + I_2n_right, n_left + n_right

    finalPrompt(result, nFinal, eps)


def main():
    try:
        while True:
            firstTask()
            secondTask()
    except Exception as e:
        print(f"Ошибка выполнения: {e}")


if __name__ == "__main__":
    main()
