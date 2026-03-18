# import numpy as np
eps = 10 ** -2
funct1 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]
funct2 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]  # todo
funct3 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]  # todo
n = 1


def roundPrint(val, n):
    print(round(val, n))


# l - коэффициент умножения
# add - слагаемое для прибавления к функции
def f(x, l=1, add=0):
    res = 0
    # x = round(x, 2)
    if n == 1:
        for tup in funct1:
            print(f"l4 = {l}")
            # print(f"===\nl={l}, c0={c[0]}, x={x}, c1={c[1]}\n")
            res += l * tup[0] * x ** tup[1]
            # print(f"r = {l * c[0] * x ** c[1]}\n===")
    elif n == 2:
        for c in funct2:
            res += l * c[0] * x ** c[1]
    elif n == 3:
        for c in funct3:
            res += l * c[0] * x ** c[1]
    # if sub == True:
    #     return 1 - res

    # else:
    return res + add


def calcDiff(x):
    return (f(x + eps) - f(x)) / eps


def calcMaxOfDiffFunction(a, b):
    m = -1
    floats = [round(a + i * eps, 2) for i in range(int((b - a) / eps) + 1)]
    for x_0 in floats:
        m = max(m, calcDiff(x_0))
    return m


def halfDivision(a, b):
    x = (a + b) / 2
    # print(f"abs f(x) = {abs(f(x))}")
    # print(f"x = {x}")
    if abs(f(x)) <= eps:
        # print(f"return x = {x}")
        return x
    if f(a) * f(x) > 0:
        return halfDivision(x, b)
    else:
        return halfDivision(a, x)


def fi(x, l):
    print(f"l3 = {l}")
    return f(x, l, x)


def simpleIteration(a, b, x):
    l = -1 / calcMaxOfDiffFunction(a, b)
    print(f"l1 = {l}")
    if abs(f(x)) <= eps:
        return x
    else:
        print(f"l2 = {l}")
        return simpleIteration(a, b, fi(x, l=l))


def secants(a, b):
    pass


def solveEquation():
    # −1,8𝑥^3 − 2,94𝑥^2 + 10,37𝑥 + 5,38 = [(-1.8, 3), (−2.94, 2), (10.37, 1), (5.38, 0)]
    a1 = -4
    b1 = -2

    a2 = -1
    b2 = 0

    a3 = 1
    b3 = 3
    # x3_0 = (a3 + b3) / 2
    # x_right = simpleIteration(a3, b3, x3_0)
    x_right = halfDivision(a3, b3)  # Крайний правый корень
    print(x_right)
    x1_0 = (a1 + b1) / 2
    x_left = simpleIteration(a1, b1, x1_0)  # Крайний левый корень
    print(x_left)
    x2_0 = (a2 + b2) / 2
    # x_mid = simpleIteration(a2, b2, x2_0)  # Центральный корень
    # print(x_mid)


if __name__ == "__main__":
    solveEquation()
