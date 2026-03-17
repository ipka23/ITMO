eps = 10**-2
def f(x):
    return -1.8 * x ** 3 - 2.94 * x ** 2 + 10.37 * x + 5.38

def calcDiff(x):
    return (f(x + eps) - f(x)) / eps

def halfDivision(a, b):

    x = (a + b) / 2
    if (f(x) <= eps):
        return x
    if (f(a) * f(x) < 0):
        halfDivision(a, x)
    if (f(x) * f(b) < 0):
        halfDivision(a, x)


def simpleIteration(a, b):
    x = (a + b) / 2
    lambd = -1 / max(calcDiff(a), calcDiff(b))
    calcDiff(lambd * f(x)) + 1


def secants(a, b):
    pass


def solveEquation():
    # −1,8𝑥^3 − 2,94𝑥^2 + 10,37𝑥 + 5,38
    a1 = -4
    b1 = -2

    a2 = -1
    b2 = 0

    a3 = 1
    b3 = 3
    x_right = halfDivision(a3, b3)  # Крайний правый корень
    print(x_right)
    x_left = simpleIteration(a1, b1)  # Крайний левый корень
    print(x_left)
    x_mid = halfDivision(a2, b2)  # Центральный корень
    print(x_mid)


if __name__ == "__main__":
    solveEquation()
