# import numpy as np
eq = [(1, 2), (3, 4)]
eps = 10 ** -2
# −1,8𝑥^3 − 2,94𝑥^2 + 10,37𝑥 + 5,38 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]


def f(x, num=1):
    funct1 = [(-1.8, 3), (-2.94, 2), (10.37, 1), (5.38, 0)]
    s = 0
    for c in funct1:
        s += c[0]*x**c[1]
    return s

def calcDiff(x):
    return (f(x + eps) - f(x)) / eps


def calcMaxOfDiffFunction(a, b):
    m = -1
    floats = [round(a + i * eps, 2) for i in range(int((b - a) / eps) + 1)]
    for x_0 in floats:
        m = max(m, calcDiff(f(x_0)))
    return m

print(calcMaxOfDiffFunction(-2, 1))
