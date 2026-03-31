import math

eps = 10 ** -2
funct1 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]
funct2 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]  # todo
funct3 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]  # todo
n = 1


# l - коэффициент умножения
# add - слагаемое для прибавления к функции
def f(x, l=1, add=0):
    res = 0
    # x = round(x, 2)
    if n == 1:
        for tup in funct1:
            # print(f"===\nl={l}, c0={c[0]}, x={x}, c1={c[1]}\n")
            res += l * tup[0] * x ** tup[1]
            # print(f"r = {l * c[0] * x ** c[1]}\n===")
    elif n == 2:
        for c in funct2:
            res += l * c[0] * x ** c[1]
    elif n == 3:
        for c in funct3:
            res += l * c[0] * x ** c[1]
    return res + add


def calcDiff(func, x):
    return (func(x + eps) - func(x)) / eps


def fi(x, l):
    return f(x, l, x)


def simpleIteration(a, b):
    x_curr = a
    l = -1 / max(calcDiff(f, a), calcDiff(f, b))
    n = 1
    while True:
        x_next = fi(x_curr, l)
        if abs(x_next - x_curr) <= eps:
            break
        if (abs(x_next - x_curr)) > 10 or n > 20:
            print("Введите более точные границы, с текущими последовательность решений не сходится к истинному")
            break

        # print(f"n = {n} | x_{n} = {x_curr:.3f} | x_{n+1} = {x_next:.3f} | f(x_{n+1}) = {f(x_next):.3f} | |x_next - x_curr| = {abs(x_next - x_curr):.3f}")
        x_curr = x_next
        n += 1
    print(round(x_next, 3))


def tangents(a, b):
    x_mid = (a + b) / 2
    x_prev = x_mid
    if calcDiff(f, x_mid) > 0:
        x_prev = a
    elif calcDiff(f, x_mid) < 0:
        x_prev = b
    while True:
        h_prev = f(f, x_prev) / calcDiff(f, x_prev)
        x_curr = x_prev - h_prev
        if abs(x_curr - x_prev) <= eps:
            break
        x_prev = x_curr
    print(x_curr)


def chords(a, b):
    while True:
        x_curr = a - f(a) * (a - b) / (f(a) - f(b))
        if abs(f(x_curr)) <= eps:
            break
        # f_i = f(x_i)
        if f(a) * f(x_curr) < 0:
            b = x_curr
        else:
            a = x_curr
    print(x_curr)


def solveEquation():
    # −1,8𝑥^3 − 2,94𝑥^2 + 10,37𝑥 + 5,38 = [(-1.8, 3), (−2.94, 2), (10.37, 1), (5.38, 0)]
    a1 = -4
    b1 = -3

    a2 = -1
    b2 = 0

    a3 = 1.5
    b3 = 2

    # simpleIteration(a1, b1)
    # simpleIteration(a2, b2)
    # print("-" * 100)
    # simpleIteration(a3, b3)

    # chords(a1, b1)
    # print("-" * 100)
    # chords(a2, b2)
    # print("-" * 100)
    # chords(a3, b3)

    tangents(a1, b1)
    print("-" * 100)
    tangents(a2, b2)
    print("-" * 100)
    tangents(a3, b3)


def calcDiffX(func, x, y):
    return (func(x + eps, y) - func(x, y)) / eps


def calcDiffY(func, x, y):
    return (func(x, y + eps) - func(x, y)) / eps


def f1(x, y):
    return math.sin(x + y) - 1.5 * x + 0.1


def f2(x, y):
    return x ** 2 + 2 * y ** 2 - 1


def solveSystemOfLinearEquation(a11, a12, a21, a22, b1, b2):
    pass


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


if __name__ == "__main__":
    # solveEquation()
    x1 = 0.7
    y1 = 0.5
    x, y = solveSystemOfNonLinearEquation(x1, y1)
    print(f"x = {x} | y = {y}")
