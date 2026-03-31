eps = 0.01


def f(x):
    return 9 * x ** 3 - 3 * x ** 2 - 8 * x - 2


def halfDiv(a, b):
    x = (a + b) / 2
    k = 1
    while True:
        k += 1
        print(f"k = {k} | x = {x}")
        if abs(b - a) < eps:
            return x
        if f(a) * f(b) > 0:
            a = x
        else:
            b = x


def trapezoid(a, b):
    s = (f(a) + f(b)) / 2
    h = (b - a) / 10
    for n in range(1, 10):
        s += f(a + h * n)
    s *= h
    return s


def fi(x, l):
    return x + l * f(x)


def diff(x):
    return (f(x + eps) - f(x)) / eps


def simpleIterNonLinearEq(a, b):
    x = (a + b) / 2
    l = -1 / max(diff(a), diff(b))
    while True:
        if abs(f(x)) <= eps:
            return x
        x = fi(x, l)

halfDiv(0, 2)