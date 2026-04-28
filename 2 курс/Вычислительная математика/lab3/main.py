import math

eps = 0.01

def f1(x):
    return 2*x**3 - 3*x**2 + 5*x - 9

def f2(x):
    return x**3 - 3*x**2 + 7*x - 10

def f3(x):
    return 2*x**3 - 9*x**2 - 7*x + 11

def f4(x):
    if x == 0:
        return math.inf
    return 1 / math.sqrt(x)

def f5(x):
    return 1 / math.sqrt(1 - x**2)

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
    print("1: 1/sqrt(x)")
    print("2: 1/sqrt(1-x^2)")
    print("3: 1/(x-1)")
    choice = input("Введите номер функции (1-3): ")
    while choice not in ["1", "2", "3"]:
        choice = input("Неверный ввод. Введите 1-3: ")
    return [f4, f5, f6][int(choice) - 1]

def checkConvergence(f):
    if f == f6:
        return False
    return True

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

def rungeRule(f, a, b, method, k, eps, n0=4):
    n = n0
    while True:
        I_n = method(f, a, b, n)
        I_2n = method(f, a, b, 2 * n)
        error = abs(I_2n - I_n) / (2 ** k - 1)
        if error < eps:
            return I_2n, 2 * n
        n *= 2
        if n > 1000000:
            return None, None

def improperIntegralLeft(f, a, b, eps, method, p):
    n = 4
    delta = eps
    while True:
        I_n = method(f, a + delta, b, n)
        I_2n = method(f, a + delta/2, b, 2 * n)
        error = abs(I_2n - I_n) / (2 ** p - 1)
        if error < eps:
            return I_2n, 2 * n
        n *= 2
        delta /= 2
        if n > 1000000:
            return None, None

def improperIntegralRight(f, a, b, eps, method, p):
    n = 4
    delta = eps
    while True:
        I_n = method(f, a, b - delta, n)
        I_2n = method(f, a, b - delta/2, 2 * n)
        error = abs(I_2n - I_n) / (2 ** p - 1)
        if error < eps:
            return I_2n, 2 * n
        n *= 2
        delta /= 2
        if n > 1000000:
            return None, None

def improperIntegralMiddle(f, a, b, c, eps, method, p):
    n = 4
    delta = eps
    while True:
        I_n_left = method(f, a, c - delta, n)
        I_n_right = method(f, c + delta, b, n)
        I_2n_left = method(f, a, c - delta/2, 2 * n)
        I_2n_right = method(f, c + delta/2, b, 2 * n)
        I_n = I_n_left + I_n_right
        I_2n = I_2n_left + I_2n_right
        error = abs(I_2n - I_n) / (2 ** p - 1)
        if error < eps:
            return I_2n, 2 * n
        n *= 2
        delta /= 2
        if n > 1000000:
            return None, None

def main():
    print("=" * 60)
    print("ЧИСЛЕННОЕ ИНТЕГРИРОВАНИЕ")
    print("=" * 60)

    f = selectFunction()

    a = float(input("Введите нижний предел a: "))
    b = float(input("Введите верхний предел b: "))
    while a >= b:
        print("Ошибка: a должно быть меньше b")
        a = float(input("Введите a: "))
        b = float(input("Введите b: "))

    eps = float(input("Введите точность вычислений: "))

    print("\nДоступные методы:")
    print("1. Левые прямоугольники")
    print("2. Правые прямоугольники")
    print("3. Средние прямоугольники")
    print("4. Трапеции")
    print("5. Симпсона")

    methodChoice = input("Выберите метод (1-5): ")
    while methodChoice not in ["1", "2", "3", "4", "5"]:
        methodChoice = input("Неверный ввод. Выберите 1-5: ")

    methods = {
        "1": ("левых прямоугольников", leftRectangles, 1),
        "2": ("правых прямоугольников", rightRectangles, 1),
        "3": ("средних прямоугольников", middleRectangles, 2),
        "4": ("трапеций", trapezoid, 2),
        "5": ("Симпсона", simpson, 4)
    }

    methodName, methodFunc, k = methods[methodChoice]

    result, nFinal = rungeRule(f, a, b, methodFunc, k, eps)

    if result is not None:
        print("\n" + "=" * 60)
        print("РЕЗУЛЬТАТ:")
        print(f"  Интеграл ≈ {result:.3f}")
        print(f"  Число разбиений n = {nFinal}")
        print(f"  Требуемая точность {eps} достигнута")
        print("=" * 60)
    else:
        print("Не удалось достичь требуемой точности")

    print("\n\n")

    print("=" * 60)
    print("НЕСОБСТВЕННЫЕ ИНТЕГРАЛЫ 2 РОДА")
    print("=" * 60)

    f_imp = selectImproperFunction()

    a = float(input("Введите нижний предел a: "))
    b = float(input("Введите верхний предел b: "))
    while a >= b:
        print("Ошибка: a должно быть меньше b")
        a = float(input("Введите a: "))
        b = float(input("Введите b: "))

    eps = float(input("Введите точность вычислений: "))

    print("\nВыберите тип разрыва:")
    print("1. Разрыв в точке a")
    print("2. Разрыв в точке b")
    print("3. Разрыв внутри отрезка [a, b]")

    breakType = input("Введите номер (1-3): ")
    while breakType not in ["1", "2", "3"]:
        breakType = input("Неверный ввод. Введите 1-3: ")

    if breakType == "3":
        c = float(input("Введите точку разрыва внутри отрезка: "))
        while c <= a or c >= b:
            c = float(input("Точка разрыва должна быть внутри (a, b). Введите снова: "))

    if not checkConvergence(f_imp):
        print("Интеграл не существует")
        return

    print("\nДоступные методы:")
    print("1. Левые прямоугольники")
    print("2. Правые прямоугольники")
    print("3. Средние прямоугольники")
    print("4. Трапеции")
    print("5. Симпсона")

    methodChoice = input("Выберите метод (1-5): ")
    while methodChoice not in ["1", "2", "3", "4", "5"]:
        methodChoice = input("Неверный ввод. Выберите 1-5: ")

    methodName, methodFunc, k = methods[methodChoice]

    if breakType == "1":
        result, nFinal = improperIntegralLeft(f_imp, a, b, eps, methodFunc, k)
    elif breakType == "2":
        result, nFinal = improperIntegralRight(f_imp, a, b, eps, methodFunc, k)
    else:
        result, nFinal = improperIntegralMiddle(f_imp, a, b, c, eps, methodFunc, k)

    if result is not None:
        print("\n" + "=" * 60)
        print("РЕЗУЛЬТАТ:")
        print(f"  Интеграл ≈ {result:.3f}")
        print(f"  Число разбиений n = {nFinal}")
        print(f"  Требуемая точность {eps} достигнута")
        print("=" * 60)
    else:
        print("Не удалось достичь требуемой точности")

if __name__ == "__main__":
    while True:
        main()